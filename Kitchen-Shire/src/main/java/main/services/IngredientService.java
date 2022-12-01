package main.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.IsolationLevel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import main.entities.IngredientType;
import main.repositories.IngredientRepository;

@Service
public class IngredientService {
	
	@Autowired
	private IngredientRepository repo;
	
	public boolean isAvailable(Map<IngredientType, Integer> total) {		
		try {
			Map<IngredientType, Integer> availableFromDb=findAvailableIngredients(total);
			for(IngredientType type:total.keySet()) {
				if(total.get(type)>availableFromDb.get(type)) {
					return false;
				}			
			}		
			return true;	
		} catch (Exception e) {
			return false;
		}	
			
	}
	
	@Transactional(isolation=Isolation.READ_COMMITTED)
	public Map<IngredientType, Integer> findAvailableIngredients(Map<IngredientType, Integer> total) {
		Map<IngredientType, Integer> availableFromDb=new HashMap<>();
		repo.findAll().stream().forEach(e->availableFromDb.put(e.getType(), e.getAmount()));
		return availableFromDb;	
	}

}

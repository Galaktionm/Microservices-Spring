package main.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.IsolationLevel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import lombok.Data;
import main.objects.Dish;
import main.repositories.IngredientRepository;
import main.services.IngredientService;
import main.entities.IngredientType;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	
	@Autowired
	private IngredientService service;
	
	@PostMapping
	public ResponseEntity<OrderAcceptResult> acceptOrder(@RequestBody List<Dish> dishes) {
		
		Map<IngredientType, Integer> totalRequiredResources=new HashMap<>();
		for(Dish dish:dishes) {
			Map<IngredientType, Integer> requiredResources=dish.getRequiredIngredients();
			for(IngredientType type:requiredResources.keySet()) {
				totalRequiredResources.merge(type, requiredResources.get(type), (c, n)->c+n);
			}
		}
		
		if(service.isAvailable(totalRequiredResources)) {
			return new ResponseEntity<OrderAcceptResult>(
					new OrderAcceptResult(true, "Order accepted"),
					HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<OrderAcceptResult>(new OrderAcceptResult(false, "Not available"),
					HttpStatus.NOT_ACCEPTABLE);
		}	
	}
	
	
	
	@Data
	public static class OrderAcceptResult{
		
		private boolean result;
		
		private String message;
		
		public OrderAcceptResult(boolean result, String message) {
			this.result=result;
			this.message=message;
		}
		
	}
	
	

}

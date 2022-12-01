package main.entities;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import main.entities.IngredientType;

@Data
@Entity
public class Dish {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@JsonIgnore
	private Long id;
	
	private String name;
	
	private Map<IngredientType, Integer> requiredIngredients=new HashMap<>();
	
	public Dish(String name) {
		this.name=name;
	}

}

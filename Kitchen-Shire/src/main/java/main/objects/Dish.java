package main.objects;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import main.entities.IngredientType;

@Data
public class Dish {
	
	private String name;
	
	private Map<IngredientType, Integer> requiredIngredients=new HashMap<>();
	
	public Dish(String name) {
		this.name=name;
	}
	
	public Dish(String name, Map<IngredientType, Integer> ingredients) {
		this.name=name;
		this.requiredIngredients=ingredients;
	}

}

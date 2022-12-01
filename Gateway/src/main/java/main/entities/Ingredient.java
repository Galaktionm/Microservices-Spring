package main.entities;


import lombok.Data;

@Data
public class Ingredient {
	
	private Long id;
	
	private IngredientType type;
	
	private Integer amount;
	
	public Ingredient() {}
	
	public Ingredient(IngredientType type, Integer amount) {
		this.type=type;
		this.amount=amount;
	}
	
	

}

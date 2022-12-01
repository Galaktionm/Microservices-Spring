package main.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Ingredient {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private IngredientType type;
	
	private Integer amount;
	
	public Ingredient() {}
	
	public Ingredient(IngredientType type, Integer amount) {
		this.type=type;
		this.amount=amount;
	}
	
	

}

package main.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import main.entities.Ingredient;
import main.entities.IngredientType;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
	
	Ingredient findByType(IngredientType type);

}

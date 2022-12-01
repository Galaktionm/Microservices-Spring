package main.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.entities.Ingredient;
import main.objects.Chef;
import main.repositories.IngredientRepository;
import main.services.LoadPointService;

@RestController
@RequestMapping("/api/management")
public class ManagementController {
	
	@Autowired
	private LoadPointService service;
	@Autowired
	private IngredientRepository repo;
	
	@GetMapping("/loadPoint")
	public ResponseEntity<Map<Integer, String>> getLoadPoint(){
		System.out.println("Ras shvreba to");
		return new ResponseEntity<Map<Integer, String>>(
				Collections.singletonMap(LoadPointService.getLoadScore(), "KitchenService-KingsLanding"),HttpStatus.OK);
	}
	
	@PostMapping("/chef")
	public void checkInChef(@RequestBody Chef chef) {
		service.addChef(chef);
	}
	
	@PostMapping("/setIngredients")
	public ResponseEntity<String> setIngredients(@RequestBody List<Ingredient> ingredients) {
		for(Ingredient ingredient:ingredients) {
			Ingredient ingredientFromDb=repo.findByType(ingredient.getType());
			if(ingredientFromDb!=null) {
			ingredientFromDb.setAmount(ingredientFromDb.getAmount()+ingredient.getAmount());
			} else {
				repo.save(ingredient);
			}
	}
		return new ResponseEntity<String>("Ingredients added", HttpStatus.OK);
}

}

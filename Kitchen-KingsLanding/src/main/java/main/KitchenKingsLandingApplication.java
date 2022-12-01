package main;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import main.entities.Ingredient;
import main.entities.IngredientType;
import main.objects.Chef;
import main.objects.Dish;
import main.repositories.IngredientRepository;
import main.services.LoadPointService;

@SpringBootApplication
public class KitchenKingsLandingApplication {
	
	@Autowired
	private LoadPointService service;
	@Autowired
	private IngredientRepository repo;

	public static void main(String[] args) {
		SpringApplication.run(KitchenKingsLandingApplication.class, args);
	}
	
	@Bean
	CommandLineRunner loadData() {
		return args->{
			Ingredient potato=new Ingredient(IngredientType.Potato, 10);
			Map<IngredientType, Integer> ingredients=new HashMap<>();
			ingredients.put(IngredientType.Potato, 10);
			Chef chef1=new Chef(1L, "Chef1");
			Chef chef2=new Chef(2L, "Chef2");
			Chef chef3=new Chef(3L, "Chef3");
			Chef chef4=new Chef(4L, "Chef4");
			Chef chef5=new Chef(5L, "Chef5");
			Dish dish=new Dish("Potato salad", ingredients);
			repo.save(potato);
			service.addChef(chef1);
			service.addChef(chef2);
			service.addChef(chef3);
			service.addChef(chef4);
			service.addChef(chef5);
			service.addDish(dish);
			
			
		};
	}

}

package main.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.entities.Dish;
import main.repositories.DishRepository;

@RestController
@RequestMapping("/api/dishes")
public class DishController {
	
	@Autowired
	private DishRepository repo;
	
	@GetMapping
	public ResponseEntity<List<Dish>> getDishes(@RequestBody List<String> names){
		List<Dish> result=new ArrayList<>();
		for(String name:names) {
			if(repo.findByName(name).isPresent()) {
				result.add(repo.findByName(name).get());
			}
		}
		if(result.size()>0) {
			return new ResponseEntity<List<Dish>>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
	}

}

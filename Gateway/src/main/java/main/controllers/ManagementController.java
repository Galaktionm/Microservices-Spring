package main.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import main.entities.Ingredient;
import main.helpers.LoginRequest;
import main.services.JWTService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/api/management")
@RestController
public class ManagementController {
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@PostMapping("/admin/setIngredients")
	public Mono<ResponseEntity<String>> setIngredients(@RequestBody Flux<Ingredient> list){
		Mono<ResponseEntity<String>> result=webClientBuilder.baseUrl("http://KitchenService/api/management/setIngredients").build()
				.post().body(list, LoginRequest.class)
				.exchangeToMono(response->{
					
					if(response.statusCode().equals(HttpStatus.OK)) {
						
						return Mono.just(new ResponseEntity<String>("Ingredients added", HttpStatus.OK));
				
				     } 
					return Mono.just(new ResponseEntity<String>("There was an errror processing your request", HttpStatus.BAD_REQUEST));
					});
		
		return result;
		
	}
	
	

}

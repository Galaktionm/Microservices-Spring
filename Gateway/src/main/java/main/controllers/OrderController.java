package main.controllers;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import main.entities.Dish;
import main.entities.Ingredient;
import main.helpers.LoginRequest;
import main.services.OrderService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	@Autowired
	private OrderService service;
	
	@PostMapping
	public Mono<Object> order(@RequestBody Flux<Dish> list) {
		
	Mono<Object> result=webClientBuilder.baseUrl("http://KitchenService/api/order").build()
			.post().body(list, LoginRequest.class)
			.exchangeToMono(response->{					
				if(response.statusCode().equals(HttpStatus.OK)) {						
					return Mono.just(new ResponseEntity<String>("Order accepted", HttpStatus.OK));				
			     } 
				return Mono.just(new ResponseEntity<String>("There was an errror processing your request", HttpStatus.BAD_REQUEST));
				});	
	    return result;
	}

	
	
}

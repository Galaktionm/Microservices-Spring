package main.controllers;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.Data;
import main.entities.User;
import main.helpers.LoginRequest;
import main.services.JWTService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/authorization")
public class UserAuthorizationController {
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	@Autowired
	private JWTService service;
	@Autowired
	private ReactiveCircuitBreakerFactory factory;
	
	@GetMapping("/account/{id}")
	@CircuitBreaker(name="accountBreaker")
	public Mono<Object> getAccount(@PathVariable("id") Long userId) {

		Mono<Object> result=webClientBuilder.baseUrl("http://UserService/api/authorization/account/"+userId).build()
				.get().exchangeToMono(response->{
					
					if(response.statusCode().equals(HttpStatus.OK)) {
						return response.bodyToMono(User.class);
					}					
					return Mono.just(new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));					
				})
				.timeout(Duration.ofMillis(100000))
				.transform(e->{
					ReactiveCircuitBreaker cb=factory.create("Gateway");
					return cb.run(e);
				});
		return result;	

	}
	
	
	
	@PostMapping("/request/user/login")
	public Mono<ResponseEntity<String>> authorizeUser(@RequestBody Mono<LoginRequest> request) throws SignatureException, ExpiredJwtException, MalformedJwtException, IllegalArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
		Mono<ResponseEntity<String>> result=webClientBuilder.baseUrl("http://UserService/api/authorization/login").build()
		.post().body(request, LoginRequest.class)
		.exchangeToMono(response->{
			
			if(response.statusCode().equals(HttpStatus.OK)) {
				List<String> header=response.headers().header("Authorization");
				
				if(header!=null && service.verifyUser(header.get(0))) {
					return Mono.just(new ResponseEntity<String>(header.get(0), HttpStatus.OK));
				}							
				
		     } 
			return Mono.just(new ResponseEntity<String>("Email or username incorrect", HttpStatus.BAD_REQUEST));
			});
		
		return result;
	}	
	
}

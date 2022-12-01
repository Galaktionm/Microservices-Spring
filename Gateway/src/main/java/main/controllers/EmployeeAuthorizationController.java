package main.controllers;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import main.helpers.LoginRequest;
import main.services.JWTService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/authorization")
public class EmployeeAuthorizationController {
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	@Autowired
	private JWTService service;
	@Autowired
	private ReactiveCircuitBreakerFactory factory;
	
	@PostMapping("/request/admin/login")
	public Mono<ResponseEntity<String>> authorizeAdmin(@RequestBody Mono<LoginRequest> request) throws SignatureException, ExpiredJwtException, MalformedJwtException, IllegalArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
		Mono<ResponseEntity<String>> result=webClientBuilder.baseUrl("http://EmployeeService/api/authorization/login/admin").build()
		.post().body(request, LoginRequest.class)
		.exchangeToMono(response->{
			
			if(response.statusCode().equals(HttpStatus.OK)) {
				List<String> header=response.headers().header("Authorization");
				
				if(header!=null && service.verifyAdmin(header.get(0))) {
					return Mono.just(new ResponseEntity<String>(header.get(0), HttpStatus.OK));
				}							
				
		     } 
			return Mono.just(new ResponseEntity<String>("Email or username incorrect", HttpStatus.BAD_REQUEST));
			});
		
		return result;
	}
	
	@PostMapping("/request/chef/login")
	public Mono<ResponseEntity<String>> authorizeChef(@RequestBody Mono<LoginRequest> request) throws SignatureException, ExpiredJwtException, MalformedJwtException, IllegalArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
		Mono<ResponseEntity<String>> result=webClientBuilder.baseUrl("http://EmployeeService/api/authorization/login/chef").build()
		.post().body(request, LoginRequest.class)
		.exchangeToMono(response->{
			
			if(response.statusCode().equals(HttpStatus.OK)) {
				List<String> header=response.headers().header("Authorization");
				
				if(header!=null && service.verifyChef(header.get(0))) {
					return Mono.just(new ResponseEntity<String>(header.get(0), HttpStatus.OK));
				}							
				
		     } 
			return Mono.just(new ResponseEntity<String>("Email or username incorrect", HttpStatus.BAD_REQUEST));
			});
		
		return result;
	}

}

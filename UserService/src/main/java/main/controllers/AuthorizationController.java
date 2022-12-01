package main.controllers;


import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.jsonwebtoken.security.InvalidKeyException;
import lombok.Data;
import main.entities.User;
import main.repositories.UserRepository;
import main.services.JWTService;

@RestController
@RequestMapping("/api/authorization")
public class AuthorizationController {
	
	@Autowired
	private UserRepository repo;
	@Autowired
	private JWTService service;
	@Autowired
	private PasswordEncoder encoder;
	
	@GetMapping("/account/{id}")
	//@CircuitBreaker(name="UserServiceGetAccount")
	public ResponseEntity<User> getAccount(@PathVariable Long id) throws InterruptedException {
	
		//Thread.sleep(100000);
		
		Optional<User> user=repo.findById(id);
		
		if(user.isPresent()) {
			return new ResponseEntity<User>(user.get(), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

		
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest request) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException{
		User user=repo.findByEmail(request.getEmail());
		if(user!=null && encoder.matches(request.getPassword(), user.getPassword())) {
			
			String token=service.getToken(request.getEmail());
			System.out.println("WTF IS GOING ON");
			
			return ResponseEntity.ok()
					.header(HttpHeaders.AUTHORIZATION, "Bearer "+token)
					.header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization").build();				
		}
		
		return ResponseEntity.badRequest().build();
		
	}	
	
	@Data
	public static class LoginRequest{
		
		private String email;
		
		private String password;
		
		public LoginRequest(String email, String password) {
			this.email=email;
			this.password=password;
		}		
	}
	

}

package main.controllers;


import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.security.InvalidKeyException;
import lombok.Data;
import main.entities.Administrator;
import main.repositories.AdminRepository;
import main.repositories.ChefRepository;
import main.repositories.EmployeeRepository;
import main.services.JWTService;

@RestController
@RequestMapping("/api/authorization/login")
public class AuthorizationController {
	
	@Autowired
	private AdminRepository adminRepo;
	@Autowired
	private ChefRepository chefRepo;
	@Autowired
	private JWTService service;
	
	@PostMapping("/admin")
	public ResponseEntity<?> authenticateAdmin(@RequestBody LoginRequest request) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException{
		
		Administrator admin=adminRepo.findByEmail(request.getEmail());
		if(admin!=null) {
			
			String token=service.getTokenForAdmin(request.getEmail());
			
			return ResponseEntity.ok()
					.header(HttpHeaders.AUTHORIZATION, "Bearer "+token)
					.header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization").build();				
		}
		
		return ResponseEntity.badRequest().build();
		
	}	
	
	
	@PostMapping("/chef")
	public ResponseEntity<?> authenticateChef(@RequestBody LoginRequest request) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException{
		if(chefRepo.findByEmail(request.getEmail())!=null) {
			
			String token=service.getTokenForChef(request.getEmail());
			
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

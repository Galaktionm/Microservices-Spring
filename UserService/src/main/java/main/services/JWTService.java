package main.services;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import main.repositories.UserRepository;

@Service
public class JWTService {
	
	@Autowired
	private UserRepository repo;
	
	private static final Long expiresIn=1800000L;
	
	@Value("${secretKey}")
	private String secretKeyString;
	
	
	public String getToken(String email) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
		Date date=new Date();
		Claims claim=Jwts.claims(Collections.singletonMap("Authority", 
				                             "User"));
		String token = Jwts.builder()
			  .setSubject(email)
			  .setClaims(claim)
			  .signWith(getSecretKey())
			  .compact();
		return token;
  }
	
	@Bean
	public SecretKey getSecretKey() {
	    byte[] decodedKey = Base64.getDecoder().decode(secretKeyString);
	    SecretKey key=Keys.hmacShaKeyFor(decodedKey);
	    return key;
	}
	
}

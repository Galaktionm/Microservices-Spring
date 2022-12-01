package main.services;

import java.util.Base64;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
	
	
	private static final Long expiresIn=1800000L;
	
	@Value("${secretKey}")
	private String secretKeyString;
	
		
	public boolean verifyUser(String jws)  {
		
			Jws<Claims> result = Jwts.parserBuilder()
					.setSigningKey(getSecretKey())
					.require("Authority", "User")
					.build()
					.parseClaimsJws(jws.replace("Bearer", ""));
	    
			if (result != null) {
				return true;
		}
		return false;		
	}
	
	public boolean verifyAdmin(String jws) {
		Jws<Claims> result = Jwts.parserBuilder()
				.setSigningKey(getSecretKey())
				.require("Authority", "Admin")
				.build()
				.parseClaimsJws(jws.replace("Bearer", ""));
    
		if (result != null) {
			return true;
	}
	return false;		
	}
	
	public boolean verifyChef(String jws) {
		Jws<Claims> result = Jwts.parserBuilder()
				.setSigningKey(getSecretKey())
				.require("Authority", "Chef")
				.build()
				.parseClaimsJws(jws.replace("Bearer", ""));
    
		if (result != null) {
			return true;
	}
	return false;		
	}
	
	

/*
	public JwtAuthenticationToken getToken(String jws, String role)  {
		JwtParser parser=Jwts.parserBuilder()
				.setSigningKey(key)
				.build();
		Jws<Claims> result=parser.parseClaimsJws(jws.replace("Authorization", ""));
		
		if(result!=null) {
			Map<String, Object> claims=new HashMap<>();
			result.getBody().forEach((k, v)->claims.put(k, v));
			Map<String, Object> headers=new HashMap<>();
			headers.put("alg", "HSA256"); headers.put("typ", "JWT");
			Instant issuedAt=Instant.now();
			Jwt springJwt=new Jwt(jws, issuedAt, issuedAt.plusSeconds(1800), headers, claims);
			JwtAuthenticationToken token=new JwtAuthenticationToken(springJwt, 
					Arrays.asList(new SimpleGrantedAuthority(role)));
			return token;		
		}
		
		return null;		
	}	
*/
	
	@Bean
	public SecretKey getSecretKey() {
	    byte[] decodedKey = Base64.getDecoder().decode(secretKeyString);
	    SecretKey key=Keys.hmacShaKeyFor(decodedKey);
	    return key;
	}
	
	
}
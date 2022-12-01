package main.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;


@Component
public class AuthenticationFilter implements WebFilter {
	
	@Autowired
	private JWTService service;
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        List<String> header=exchange.getRequest().getHeaders().get("Authorization");	
		if((header==null) && 
				(new AntPathMatcher().match("/api/authorization/request/**", exchange.getRequest().getPath().value())==false
				&& new AntPathMatcher().match("/actuator/**", exchange.getRequest().getPath().value())==false)){
			ServerHttpResponse response = exchange.getResponse();
	        response.setStatusCode(HttpStatus.UNAUTHORIZED);
	        return response.setComplete();
		} 
		
		
		return chain.filter(exchange);
	}

	
}
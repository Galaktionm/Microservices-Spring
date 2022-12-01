package main.entities;

import java.util.ArrayList;
import java.util.List;


import lombok.Data;

@Data
public class User {

	private Long id;
	
	private String username;
	
	private String email;
	
	private String password;
	
	private List<Address> addresses=new ArrayList<>();

	private List<Order> orders=new ArrayList<>();
	
	public User() {}
	
	public User(String username, String email, String password) {
		this.username=username;
		this.email=email;
		this.password=password;
	}
	
	

}

package main.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	private String username;
	
	private String email;
	
	private String password;
	
	@OneToMany(fetch=FetchType.LAZY)
	private List<Address> addresses=new ArrayList<>();
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="user")
	private List<Order> orders=new ArrayList<>();
	
	public User() {}
	
	public User(String username, String email, String password) {
		this.username=username;
		this.email=email;
		this.password=password;
	}
	
	

}

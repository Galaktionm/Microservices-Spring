package main.entities;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Order {
	
	private Long id;
	
	private List<String> dishes=new ArrayList<>();

	private User user;

	private Address address;		
	

}

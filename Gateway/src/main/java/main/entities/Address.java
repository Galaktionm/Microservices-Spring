package main.entities;

import lombok.Data;

@Data
public class Address {	

	private Long id;
	
	private String street;
	
	private Integer number;
	
	private Integer flatNumber;
	
	public Address() {}
	
	public Address(String street, Integer number, Integer flatNumber) {
		this.street=street;
		this.number=number;
		this.flatNumber=flatNumber;
	}
	

}

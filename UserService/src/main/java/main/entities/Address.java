package main.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Address {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
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

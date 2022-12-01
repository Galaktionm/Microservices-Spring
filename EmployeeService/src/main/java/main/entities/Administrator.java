package main.entities;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Administrator extends Employee{
	
	public Administrator() {}
	
	public Administrator(String name, Branch branch) {
		super(name, branch);
		this.role="Admin";
	}
	
	public Administrator(String name, String email, String password, Branch branch) {
		super(name, email, password, branch);
		this.role="Admin";
	}
}

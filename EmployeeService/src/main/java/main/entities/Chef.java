package main.entities;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Chef extends Employee{
	
    public Chef() {}
	
	public Chef(String name, Branch branch) {
		super(name, branch);
		this.role="Chef";
		
	}
	
	public Chef(String name, String email, String password, Branch branch) {
		super(name, email, password, branch);
		this.role="Chef";
	}

}

package main.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Employee {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	protected String name;
	
	protected String email;
	
	protected String password;
	
	@Enumerated
	protected Branch branch;
	
	protected String role;
	
	
	public Employee() {}
	
	public Employee(String name, Branch branch) {
		this.name=name;
		this.branch=branch;
	}
	
	public Employee(String name, String email, String password, Branch branch) {
		this.name=name;
		this.email=email;
		this.password=password;
		this.branch=branch;
	}

}

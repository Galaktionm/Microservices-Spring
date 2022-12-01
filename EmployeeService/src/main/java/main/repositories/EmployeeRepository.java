package main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import main.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	Employee findByEmail(String email);

}

package main;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import main.entities.Administrator;
import main.entities.Branch;
import main.entities.Chef;
import main.repositories.EmployeeRepository;

@SpringBootApplication
public class EmployeeServiceApplication {
	
	@Autowired
	private EmployeeRepository repo;

	public static void main(String[] args) {
		SpringApplication.run(EmployeeServiceApplication.class, args);
	}
	
	@Bean
	CommandLineRunner loadEmployees() {
		return args->{
			Administrator admin1=new Administrator("Gagalo", "gmetr20@freeuni.edu.ge", "password", 
					Branch.Shire);
			Chef chef1=new Chef("Chef1", "chef1@gmail.com", "password", Branch.Shire);
			Chef chef2=new Chef("Chef2", "chef2@gmail.com", "password", Branch.Shire);
			Chef chef3=new Chef("Chef3", "chef3@gmail.com", "password", Branch.Shire);
			Chef chef4=new Chef("Chef4", "chef4@gmail.com", "password", Branch.Shire);
			Chef chef5=new Chef("Chef5", "chef5@gmail.com", "password", Branch.Shire);
			repo.saveAll(Arrays.asList(admin1, chef1, chef2, chef3, chef4, chef5));
			
		};
	}

}

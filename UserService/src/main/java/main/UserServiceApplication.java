package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import main.entities.User;
import main.repositories.UserRepository;

@SpringBootApplication
public class UserServiceApplication {
	
	@Autowired
	private UserRepository repo;
	@Autowired
	private PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
	
	
	@Bean
	CommandLineRunner loadUser() {
		return args->{
			User user=new User("Gagalo", "gaga.metreveli.5@gmail.com", encoder.encode("password"));
			repo.save(user);
		};
	}
	

}

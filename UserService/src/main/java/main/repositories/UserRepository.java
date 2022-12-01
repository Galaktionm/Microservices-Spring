package main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import main.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByEmail(String email);

}

package main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import main.entities.Chef;

public interface ChefRepository extends JpaRepository<Chef, Long> {

	Chef findByEmail(String email);
}

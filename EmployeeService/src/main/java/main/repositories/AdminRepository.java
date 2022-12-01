package main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import main.entities.Administrator;

public interface AdminRepository extends JpaRepository<Administrator, Long> {

	Administrator findByEmail(String email);
}

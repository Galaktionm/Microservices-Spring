package main.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import main.entities.Dish;

public interface DishRepository extends JpaRepository<Dish, Long>{
	
	Optional<Dish> findByName(String name);

}

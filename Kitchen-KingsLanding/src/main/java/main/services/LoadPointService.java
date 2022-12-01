package main.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.Data;
import main.objects.Chef;
import main.objects.Dish;

@Service
@Data
public class LoadPointService {
	
	
	private static final List<Dish> currentDishes=new ArrayList<>();
	private static final List<Chef> currentChefs=new ArrayList<>();
	
	public static Integer getLoadScore() {
		if(currentDishes.size()>=10) {
			return -1;
		}
		return 10-currentChefs.size()*2+currentDishes.size();
	}
	
	public void addChef(Chef chef) {
		currentChefs.add(chef);
	}
	
	public void addDish(Dish dish) {
		currentDishes.add(dish);
	}

}

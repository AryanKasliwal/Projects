package testing;

import restaurantFood.Appetizer;
import restaurantFood.Dessert;
import restaurantFood.Food;
import restaurantFood.MainCourse;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class foodTest {

	// Test no: T17
	@Test
	public void testTotalTime() {
		Food food1 = new Appetizer("Cesar salad", 5, 20);
		Food food2 = new Dessert("Apple pie", 7, 15);
		Food food3 = new MainCourse("Kebab", 15, 40);
		ArrayList <Food> dishes = new ArrayList<Food>();
		dishes.add(food1);
		dishes.add(food2);
		dishes.add(food3);
		int result = Food.getTotalTime(dishes);
		assertEquals(result, 27);
	}
	
	// Test no: T18
	@Test
	public void testTotalTime2() {
		Food food1 = new Appetizer("Cesar salad", 10, 20);
		Food food2 = new Dessert("Apple pie", 7, 15);
		Food food3 = new MainCourse("Kebab", 15, 40);
		ArrayList <Food> dishes = new ArrayList<Food>();
		dishes.add(food1);
		dishes.add(food2);
		dishes.add(food3);
		int result = Food.getTotalTime(dishes);
		assertEquals(result==27, false);
	}
}
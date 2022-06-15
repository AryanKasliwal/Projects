package restaurantFood;

import java.util.ArrayList;

public abstract class Food {

	private String name;  // Name of the dish
	private int eatingTime;
	private int price;
	
	
	public String getName() { return this.name; }  // Getter 
	public int timeToEat() { return this.eatingTime; }  // est. time in mins it takes for each dish to be eaten and cleared
	public int getPrice() { return this.price; }  // Get price of each food item
	public abstract String getType();
	
	public Food(String name, int timeToEat, int price) {  // set name on intialization for each food
		this.name = name;
		this.eatingTime = timeToEat;
		this.price = price;
	}

	// Helper function to calculate total time of multiple food items being eaten
	public static int getTotalTime(ArrayList<Food> items) {
		int sum = 0;
		for(Food f: items)
			sum += f.timeToEat();
		return sum;
	}
}

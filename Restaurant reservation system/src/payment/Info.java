package payment;

import java.util.ArrayList;

import logistics.Clock;
import logistics.Location;
import logistics.TimePeriod;
import restaurantFood.Food;

public class Info {
	private Location location;
	private int numGuests;
	private ArrayList<Food> dishes;
	private String name;
	private TimePeriod tp;
	
	public Info(String name, Location location, int numGuests, ArrayList<Food> dishes) {
		this.location = location;
		this.numGuests = numGuests;
		this.dishes = dishes;
		this.name = name;
	}
	
	public Location getLocation() {
		return location;
	}

	public int getNumGuests() {
		return numGuests;
	}

	public ArrayList<Food> getDishes() {
		return dishes;
	}

	public String getName() {
		return name;
	}
	
	public void setTimePeriod(TimePeriod tp) {
		this.tp = tp;
	}
	
	public Clock getstart() {
		return this.tp.getStart();
	}
	
	public Clock getend() {
		return this.tp.getEnd();
	}

	public TimePeriod getTimePeriod() {
		return this.tp;
	}
}

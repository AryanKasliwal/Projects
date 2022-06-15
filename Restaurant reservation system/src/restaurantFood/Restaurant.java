package restaurantFood;


import java.util.ArrayList;

import logistics.Location;
import logistics.Reservation;

public class Restaurant {


	private static ArrayList<Food> menu;
	private static Restaurant inst = new Restaurant();
	private static ArrayList<Location> loc;
	
	private Restaurant() {
		menu = new ArrayList<Food>();
		loc = new ArrayList<Location>();
	
	}
	
	public static void addMenuItem(Food food) {
		menu.add(food);
	}
	public static Restaurant getinstance() {
		return inst;
	}
	
	public static ArrayList<Food> getMenu() {
		return menu;
	}
	
	public static void addLocation(Location newLoc) {
		loc.add(newLoc);
	}
	
	public static ArrayList<Location> getLocations(){
		return loc;
	}
	

	
	public static Object[] findBooking(int bookingID) {
		ArrayList<Location> alloc = getLocations();
		int len = alloc.size();
	
		for(int c = 0; c < len; c++) {
			for(Reservation r: alloc.get(c).getReservations()) {
				if(r.getBookingNum() == bookingID) {
					return new Object[]{r, alloc.get(c)};
				}
			}
		}
		
		return null;
	}
	
	public static void removeReservations() {
		
		int len = loc.size();
		for(int c = 0; c<len; c++)
		{
			while(loc.get(c).getReservations().size()>0)
				loc.get(c).getReservations().remove(0);
			
		}
		
	}
	
	public static void removeAllLocation() {
		while(loc.size()>0)
		{
			loc.remove(0);
		}
	}
}

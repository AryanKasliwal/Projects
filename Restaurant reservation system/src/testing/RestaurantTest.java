package testing;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Exceptions.ExInvalidClockRange;
import logistics.Clock;
import logistics.Location;
import logistics.Reservation;
import logistics.TimePeriod;
import payment.Bill;
import payment.Info;
import restaurantFood.Appetizer;
import restaurantFood.Food;
import restaurantFood.Restaurant;

public class RestaurantTest {


	@AfterAll
	public static void reset() {
	    Restaurant.removeReservations();
	    Restaurant.removeAllLocation();
	    Bill.resetBookingID();
	
	}

	
	// Test no: T44
	@Test
	public void Testfindbooking() throws ExInvalidClockRange {

		
		Location loc = new Location("MK", 10);
		Info info;
		Food f1;
		Food f2;
		f1 = new Appetizer("Cesar Salad",7,20);
		f2 = new Appetizer("Mushroom Soup",6,40);
		ArrayList<Food> dishes;
		dishes = new ArrayList<Food>();
		dishes.add(f1);
		dishes.add(f2);
		info = new Info("Jeff", loc, 3, dishes);
		TimePeriod tp;
	
		Clock timest = Clock.createClockWithTime(11, 0);
		Clock timeend = Clock.createClockWithTime(12, 0);
		tp = new TimePeriod(timest, timeend);
		info.setTimePeriod(tp);
		 
		Bill bill;
		bill = new Bill(info);
		bill.setBookingID();
	
		
		Reservation res = new Reservation(info);
		res.setBill(bill);
		loc.addReservationLoc(res);
		
		Restaurant.addLocation(loc);
		
		Object[] obj = new Object[] {res, loc};
		
		int bookingID = 1;
		
		Object[] obj2 = Restaurant.findBooking(bookingID);
		
		boolean flag = false;
		if(obj2!= null && obj[0].equals(obj2[0]) && obj[1].equals(obj2[1]))
			flag = true;
		

		assertEquals(true, flag);
		
	}
	
	// Test no: T45
	@Test
	public void Testnotfindbooking() throws ExInvalidClockRange {
		
		Location loc = new Location("MK", 10);
		Info info;
		Food f1;
		Food f2;
		f1 = new Appetizer("Cesar Salad",7,20);
		f2 = new Appetizer("Mushroom Soup",6,40);
		ArrayList<Food> dishes;
		dishes = new ArrayList<Food>();
		dishes.add(f1);
		dishes.add(f2);
		info = new Info("Millie", loc, 2, dishes);
		TimePeriod tp;
		Clock timest = Clock.createClockWithTime(11, 0);
		Clock timeend = Clock.createClockWithTime(12, 0);
		tp = new TimePeriod(timest, timeend);
		info.setTimePeriod(tp);
		
		Bill bill;
		bill = new Bill(info);
		bill.setBookingID();
	
		
		Reservation res = new Reservation(info);
		res.setBill(bill);
		loc.addReservationLoc(res);
		
		Restaurant.addLocation(loc);
		
		Object[] obj = new Object[] {res, loc};
		
		int bookingID = 6;
		
		Object[] obj2 = Restaurant.findBooking(bookingID);
		
		boolean flag = false;
			if(obj2!= null && obj[0].equals(obj2[0]) && obj[1].equals(obj2[1]))
				flag = true;
		
		
		assertEquals(false, flag);
	}
}

package testing;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
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

public class LocationTest {

	@AfterEach
	public void reset() {
	    Restaurant.removeReservations();
	    Restaurant.removeAllLocation();
	    Bill.resetBookingID();
	
	}
	
	// Test no: T41
	@Test
	public void testZero() throws ExInvalidClockRange
	{
		String name = "MK";
		Location loc = new Location(name, 10);
		Clock test = Clock.createClockWithTime(11, 0);
		int num = loc.calcCurrentCapacity(test);
		
		assertEquals(num, 0);
	}
	
	// Test no: T42
	@Test
	public void testMed() throws ExInvalidClockRange
	{
		String name = "MK";
		Location loc = new Location(name, 10);

		Clock.createClockWithTime(11, 0);
		
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
		
		Reservation res = new Reservation(info);
		loc.addReservationLoc(res);
		
		int num = loc.calcCurrentCapacity(timest);
		
		
		
		assertEquals(num, 3);
	}
	
	// Test no: T43
	@Test
	public void testMax() throws ExInvalidClockRange
	{
		String name = "MK";
		Location loc = new Location(name, 10);
		Clock.createClockWithTime(11, 0);
		
		Info info;
		Food f1;
		Food f2;
		f1 = new Appetizer("Cesar Salad",7,20);
		f2 = new Appetizer("Mushroom Soup",6,40);
		ArrayList<Food> dishes;
		dishes = new ArrayList<Food>();
		dishes.add(f1);
		dishes.add(f2);
		info = new Info("Jeff", loc, 10, dishes);
		TimePeriod tp;
		Clock timest = Clock.createClockWithTime(11, 0);
		Clock timeend = Clock.createClockWithTime(12, 0);
		tp = new TimePeriod(timest, timeend);
		info.setTimePeriod(tp);
		
		Reservation res = new Reservation(info);
		loc.addReservationLoc(res);
		
		int num = loc.calcCurrentCapacity(timest);
		loc.removeReservation(res);
		assertEquals(num, 10);
	}
}

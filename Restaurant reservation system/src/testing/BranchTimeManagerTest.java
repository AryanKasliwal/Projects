package testing;

import logistics.BranchTimeManager;
import logistics.Clock;
import logistics.Location;
import logistics.Reservation;
import logistics.TimePeriod;
import payment.Info;
import restaurantFood.Appetizer;
import restaurantFood.Dessert;
import restaurantFood.Food;
import restaurantFood.MainCourse;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Exceptions.ExInvalidClockRange;

public class BranchTimeManagerTest {
	
	// Test no: T60
	@Test
	public void testGetInterval() throws ExInvalidClockRange {
		Location location = new Location("MK", 50);
		int numGuests = 10;
		ArrayList <Food> dishes = new ArrayList<Food>();
		Food food1 = new Appetizer("Cesar salad", 5, 20);
		Food food2 = new Dessert("Apple pie", 7, 15);
		Food food3 = new MainCourse("Kebab", 15, 40);
		dishes.add(food3);
		dishes.add(food2);
		dishes.add(food1);
		String name = "Aryan";
		Info info = new Info(name, location, numGuests, dishes);
		Reservation reservation = new Reservation(info);
		BranchTimeManager btm = new BranchTimeManager(location);
		ArrayList <TimePeriod> result = new ArrayList<TimePeriod>();
		result = btm.getInterval(reservation);
		Clock startClock = Clock.createClockWithTime(11, 00);
		Clock endClock = Clock.createClockWithTime(22, 32);
		ArrayList <TimePeriod> resultTP = new ArrayList<TimePeriod>();
		TimePeriod tp = new TimePeriod(startClock, endClock);
		resultTP.add(tp);
		boolean flag = false;
		for (int i = 0; i < result.size(); i++) {
			if (result.get(i).compareTo(resultTP.get(i)) != 0){
				flag = true;
			}
		}
		assertEquals(flag, false);
	}
	
	// Test no: T61
	@Test
	public void testMultipleReservationsBTM() throws ExInvalidClockRange {
		Location location = new Location("MK", 50);
		BranchTimeManager btm = new BranchTimeManager(location);
		int numGuests = 50;
		ArrayList <Food> dishes = new ArrayList <Food> ();
		Food food1 = new Appetizer("Cesar salad", 5, 20);
		Food food2 = new Dessert("Apple pie", 7, 15);
		Food food3 = new MainCourse("Kebab", 15, 40);
		dishes.add(food3);
		dishes.add(food2);
		dishes.add(food1);
		String name = "Aryan";
		Info firstInfo = new Info(name, location, numGuests, dishes);
		Clock firstStartClock = Clock.createClockWithTime(12, 00);
		Clock firstEndClock = Clock.createClockWithTime(13, 00);
		TimePeriod firstResTP = new TimePeriod(firstStartClock, firstEndClock);
		firstInfo.setTimePeriod(firstResTP);
		Reservation reservation1 = new Reservation(firstInfo);
		location.addReservationLoc(reservation1);
		int secondNumGuests = 50;
		ArrayList <Food> dishes2 = new ArrayList <Food> ();
		dishes2.add(food3);
		dishes2.add(food2);
		dishes2.add(food1);
		String name2 = "Pratul";
		Info secondInfo = new Info(name2, location, secondNumGuests, dishes2);
		Reservation reservation2 = new Reservation(secondInfo);
		ArrayList <TimePeriod> result = btm.getInterval(reservation2);
		Clock firstIntervalStartClock = Clock.createClockWithTime(11, 00);
		Clock firstIntervalEndClock = Clock.createClockWithTime(11, 32);
		ArrayList <TimePeriod> resultTP = new ArrayList<TimePeriod>();
		TimePeriod tp = new TimePeriod(firstIntervalStartClock, firstIntervalEndClock);
		resultTP.add(tp);
		Clock secondIntervalStartClock = Clock.createClockWithTime(13, 00);
		Clock secondIntervalEndClock = Clock.createClockWithTime(22, 32);
		TimePeriod tp2 = new TimePeriod (secondIntervalStartClock, secondIntervalEndClock);
		resultTP.add(tp2);
		boolean flag = false;
		for (int i = 0; i < result.size(); i++) {
			if (result.get(i).compareTo(resultTP.get(i)) != 0){
				flag = true;
			}
		}
		assertEquals(flag, false);
	}
}

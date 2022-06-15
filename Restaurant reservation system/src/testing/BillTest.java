package testing;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import Exceptions.ExInvalidClockRange;
import payment.Bill;
import payment.Info;
import restaurantFood.Appetizer;
import restaurantFood.Food;
import logistics.Clock;
import logistics.Location;
import logistics.TimePeriod;

public class BillTest {



	@AfterAll
	public static void reset() {
		Bill.resetBookingID();
	}
	
	// Test no: T1
	@Test
	public void calcBillTest()
	{
		Info info;
		Location loc = new Location("MK", 10);
		Food f1;
		Food f2;
		f1 = new Appetizer("Cesar Salad",7,20);
		f2 = new Appetizer("Mushroom Soup",6,40);
		ArrayList<Food> dishes;
		dishes = new ArrayList<Food>();
		dishes.add(f1);
		dishes.add(f2);
		info = new Info("Jeff", loc, 3, dishes);
		Bill bill;
		bill = new Bill(info);
		
		bill.setBookingID();
		int num = bill.calcBill();
		assertEquals(60, num);

	}
	
	// Test no: T2
	@Test
	public void getbookingIDtest()
	{
		Info info;
		Location loc = new Location("MK", 10);
		Food f1;
		Food f2;
		f1 = new Appetizer("Cesar Salad",7,20);
		f2 = new Appetizer("Mushroom Soup",6,40);
		ArrayList<Food> dishes;
		dishes = new ArrayList<Food>();
		dishes.add(f1);
		dishes.add(f2);
		info = new Info("Jeff", loc, 3, dishes);
		Bill bill;
		bill = new Bill(info);
		bill.setBookingID();
		assertEquals("003", bill.getBookingID());
	}
	
	// Test no: T3
	@Test
	public void showBillTest() throws ExInvalidClockRange
	{
		final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(myOut));

		Info info;
		Location loc = new Location("MK", 10);
		Food f1;
		Food f2;
		f1 = new Appetizer("Cesar Salad",7,20);
		f2 = new Appetizer("Mushroom Soup",6,40);
		ArrayList<Food> dishes;
		dishes = new ArrayList<Food>();
		dishes.add(f1);
		dishes.add(f2);
		info = new Info("Jeff", loc, 3, dishes);
		Bill bill;
		bill = new Bill(info);
		bill.setBookingID();
	
		TimePeriod tp;
		Clock timest = Clock.createClockWithTime(11, 0);
		Clock timeend = Clock.createClockWithTime(12, 0);
		tp = new TimePeriod(timest, timeend);
		info.setTimePeriod(tp);
		
		bill.showBill();
		
		
		final String standardOutput = myOut.toString();
		
		String text = "Booking is for Jeff\r\n"
				+ "Booking is for 3 guests\r\n"
				+ "Your booking time is: 11:00 - 12:00\r\n"
				+ "Cesar Salad - 20 HKD\n"
				+ "Mushroom Soup - 40 HKD\n"
				+ "Total amount: 60 HKD\n";
		assertEquals(standardOutput, text);
	}
}

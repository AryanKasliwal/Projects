package testing;



import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Exceptions.ExIncorrectClockFormat;
import Exceptions.ExInvalidClockRange;
import logistics.Clock;
import logistics.Location;
import logistics.Reservation;
import logistics.TimePeriod;
import payment.Bill;
import payment.Info;
import restaurantFood.Appetizer;
import restaurantFood.Dessert;
import restaurantFood.Food;
import restaurantFood.MainCourse;
import restaurantFood.Restaurant;
import userInput.CheckBooking;


public class CheckBookingTest {

	private final static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final static ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final static PrintStream originalOut = System.out;
	private final static PrintStream originalErr = System.err;
	static Location myloc;
	static ArrayList<Food> foodlist;
	static Reservation myres;
	

	@BeforeAll
	public static void setUpStreams() throws ExIncorrectClockFormat, ExInvalidClockRange {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	    
	    foodlist = new ArrayList<>();
	    foodlist.add(new MainCourse("Main",10,10));
	    foodlist.add(new Appetizer("App",15,15));
	    foodlist.add(new Dessert("Dessert",5,5));
	    
	    Info info = new Info("Kush", myloc, 5, foodlist);
	   
	    Bill bill = new Bill(info);
	    info.setTimePeriod(new TimePeriod(Clock.createClockWithTime("11:00"), Clock.createClockWithTime("12:00")));
		
	    myres = new Reservation(info);
	    myres.setBill(bill);
	 
		bill.setBookingID();
		myloc = new Location("MK", 50);
		Restaurant.addLocation(myloc);
	    
	    
	}
	
	
	@AfterAll
	public static void resetAll() {    
		System.setOut(originalOut);
	    System.setErr(originalErr);
	    Restaurant.removeReservations();
	    Restaurant.removeAllLocation();
	    Bill.resetBookingID();
		
	}
	
	@AfterEach
	public void resetStreams() {
		outContent.reset();
	}
	
	// Test no: T46
	@Test
	public void testnoBookingID() {
		InputStream targetStream = new ByteArrayInputStream("12".getBytes(Charset.forName("UTF-8")));

		Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
		

		try {
			CheckBooking.check(sc);
		}
		catch(Exception e)
		{
			
		}
		final String standardOutput =  outContent.toString();
		final String text ="Enter Booking ID\r\n"
				+ "Your booking could not be found!\n"
				+ "Please check your booking ID!\r\n"
				+ "";
		
		assertEquals(text,standardOutput);
		
	}
	
	// Test no: T47
	@Test
	public void testBookingID() {
		InputStream targetStream = new ByteArrayInputStream("1".getBytes(Charset.forName("UTF-8")));

		Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
		Restaurant.addLocation(myloc);
		myloc.addReservationLoc(myres);
		try {
			CheckBooking.check(sc);
		}
		catch(Exception e)
		{
			
		}
		final String standardOutput =  outContent.toString();
		final String text ="Enter Booking ID\r\n"
				+ "Found your reservation\r\n"
				+ "Booking is for Kush\r\n"
				+ "Booking is for 5 guests\r\n"
				+ "Your booking time is: 11:00 - 12:00\r\n"
				+ "Main - 10 HKD\n"
				+ "App - 15 HKD\n"
				+ "Dessert - 5 HKD\n"
				+ "Total amount: 30 HKD\n";
		
		assertEquals(text,standardOutput);
		
	}
	
	// Test no: T48
	@Test
	public void testwrongBookingID() {
		InputStream targetStream = new ByteArrayInputStream("0".getBytes(Charset.forName("UTF-8")));

		Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
		
		ScannerTestThread thread = new ScannerTestThread(sc);
		thread.start();
		try {
			CheckBooking.check(sc);
		}
		catch(Exception e)
		{
			
		}
		final String standardOutput =  outContent.toString();
		final String text ="Enter Booking ID\r\n"
				+ "Error! Booking ID must be greater than 0!\r\n";
		
		assertEquals(text,standardOutput);
		
	}
	
	// Test no: T49
	@Test
	public void testwrongBookingID2() {
		InputStream targetStream = new ByteArrayInputStream("2".getBytes(Charset.forName("UTF-8")));

		Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
		myloc.addReservationLoc(myres);
		ScannerTestThread thread = new ScannerTestThread(sc);
		thread.start();
		try {
			CheckBooking.check(sc);
		}
		catch(Exception e)
		{
			
		}
		final String standardOutput =  outContent.toString();
		final String text ="Enter Booking ID\r\n"
				+ "Your booking could not be found!\n"
				+ "Please check your booking ID!\r\n";
		
		assertEquals(text,standardOutput);
		
	}
	
	// Test no: T50
	@Test
	public void testIncorrectTypeBookingID() {
		InputStream targetStream = new ByteArrayInputStream("abc".getBytes(Charset.forName("UTF-8")));

		Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
		myloc.addReservationLoc(myres);
		ScannerTestThread thread = new ScannerTestThread(sc);
		thread.start();
		try {
			CheckBooking.check(sc);
		}
		catch(Exception e)
		{
			
		}
		final String standardOutput =  outContent.toString();
		final String text ="Enter Booking ID\r\n"
				+ "Error! Please input a valid booking id number!\r\n";
		
		assertEquals(standardOutput.toString().contains(text), true);
		
	}

}

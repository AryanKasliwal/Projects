package testing;


	import static org.junit.Assert.assertEquals;

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
import userInput.DeleteBooking;



	public class DeleteBookingTest {

		private final static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		private final static ByteArrayOutputStream errContent = new ByteArrayOutputStream();
		private final static PrintStream originalOut = System.out;
		private final static PrintStream originalErr = System.err;
		static Location myloc;
		static ArrayList<Food> foodlist;
		static Reservation myres;
		

		@BeforeAll
		public static void setUp() throws ExIncorrectClockFormat, ExInvalidClockRange {
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
			
		}

		@AfterAll
		public static void restoreStreams() {
		    System.setOut(originalOut);
		    System.setErr(originalErr);
		    Restaurant.removeReservations();
		    Restaurant.removeAllLocation();
		    Bill.resetBookingID();
		
		}
		
		@AfterEach
		public void resetData() {
		    Restaurant.removeReservations();
		    Restaurant.removeAllLocation();
		    Bill.resetBookingID();
		    
		    outContent.reset();
		}
		
		// Test no: T51
		@Test
		public void NoBookingTest() {
			InputStream targetStream = new ByteArrayInputStream("12".getBytes(Charset.forName("UTF-8")));
			
			Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
			

			try {
				DeleteBooking.deleteBooking(sc);
			}
			catch(Exception e)
			{
				
			}
			final String standardOutput =  outContent.toString();
			final String text ="Enter booking ID to delete!\r\n"
					+ "Booking not found!, returning to menu\r\n";
			
			assertEquals(text,standardOutput);
		}
		
		// Test no: T52
		@Test
		public void DeleteBookingTestSuceed() {
			InputStream targetStream = new ByteArrayInputStream("1\nyes".getBytes(Charset.forName("UTF-8")));
			
			Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
			Restaurant.addLocation(myloc);
			myloc.addReservationLoc(myres);
			try {
				DeleteBooking.deleteBooking(sc);
			}
			catch(Exception e)
			{
				
			}
			boolean worked = false;
			if(Restaurant.findBooking(1)==null)
				worked = true;
			
			assertEquals(worked,true);
		}
		
		// Test no: T53
		@Test
		public void DeleteBookingTestFail() {
			InputStream targetStream = new ByteArrayInputStream("1\nNo".getBytes(Charset.forName("UTF-8")));
			
			Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
			Restaurant.addLocation(myloc);
			myloc.addReservationLoc(myres);
			try {
				DeleteBooking.deleteBooking(sc);
			}
			catch(Exception e)
			{
				
			}
			boolean worked = false;
			if(Restaurant.findBooking(1)!=null)
				worked = true;
			
			assertEquals(worked,true);
		}
		
		// Test no: T54
		@Test
		public void DeleteBookingTestIncorrect() {
			InputStream targetStream = new ByteArrayInputStream("abc".getBytes(Charset.forName("UTF-8")));
			
			Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
			Restaurant.addLocation(myloc);
			myloc.addReservationLoc(myres);
			ScannerTestThread thread = new ScannerTestThread(sc);
			thread.start();
			try {
				DeleteBooking.deleteBooking(sc);
			}
			catch(Exception e)
			{
				
			}
			final String standardOutput =  outContent.toString();
			final String text ="Enter booking ID to delete!\r\n"
					+ "Error! Please input a valid booking id number!\r\n";
			
			assertEquals(standardOutput.toString().contains(text), true);	
		}
		
		// Test no: T55
		@Test
		public void DeleteBookingTestIncorrect2() {
			InputStream targetStream = new ByteArrayInputStream("-1".getBytes(Charset.forName("UTF-8")));
			
			Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
			Restaurant.addLocation(myloc);
			myloc.addReservationLoc(myres);
			ScannerTestThread thread = new ScannerTestThread(sc);
			thread.start();
			try {
				DeleteBooking.deleteBooking(sc);
			}
			catch(Exception e)
			{
				
			}
			final String standardOutput =  outContent.toString();
			final String text ="Enter booking ID to delete!\r\n"
					+ "Error! Booking ID must be greater than 0!\r\n";
			
			assertEquals(standardOutput, text);
		}
		
		// Test no: T56
		@Test
		public void DeleteBookingTestExistingBooking() {
			InputStream targetStream = new ByteArrayInputStream("2".getBytes(Charset.forName("UTF-8")));
			
			Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
			Restaurant.addLocation(myloc);
			myloc.addReservationLoc(myres);
			ScannerTestThread thread = new ScannerTestThread(sc);
			thread.start();
			try {
				DeleteBooking.deleteBooking(sc);
			}
			catch(Exception e)
			{
				
			}
			final String standardOutput =  outContent.toString();
			final String text ="Enter booking ID to delete!\r\n"
					+ "Booking not found!, returning to menu\r\n";
			
			assertEquals(text,standardOutput);
		}
	}

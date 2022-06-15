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

import Exceptions.ExInvalidClockRange;
import fileReader.Read;
import logistics.Clock;
import payment.Bill;
import payment.Payment;
import restaurantFood.Food;
import restaurantFood.Restaurant;
import userInput.CheckBooking;

import userInput.DeleteBooking;
import userInput.FlowController;
import userInput.TickClock;


public class SystemTesting {

	private final static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final static ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final static PrintStream originalOut = System.out;
	private final static PrintStream originalErr = System.err;

	
	
	 
	
	class Paymentstub extends Payment
	{

		public Paymentstub(Bill bill) {
			super(bill);

		}
		
		@Override
		public boolean getSuccess()
		{
			return true;
		}
	}
	
	@BeforeAll
	public static void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@AfterAll
	public static void restoreStreams() {
	    System.setOut(originalOut);
	    System.setErr(originalErr);
	}
	@AfterEach
	public void reset() {
	    
		Restaurant.removeReservations();
	    Restaurant.removeAllLocation();
	    Bill.resetBookingID();
	    try {
			Clock.SetClock(Clock.getClock(), 0, 11);
		} catch (ExInvalidClockRange e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    outContent.reset();
	    
	    ArrayList<Food> dishes = Restaurant.getMenu();
	    while(dishes.size()>0)
	    	dishes.remove(0);
	}
	
	static class FlowControllerStub extends FlowController{
		
		public FlowControllerStub() {
			super();
		}
		
		public static void flowController(Scanner in) {
			Read.setmenu("menu.txt");
			Read.setuplocations("locations.txt");
		
			
			String input = "";
			boolean end = false;
			System.out.println("WELCOME TO ZAIZAERIYA RESERVATION AND MENU SYSTEM");
			while(!end)
			{
				System.out.println("\nPlease enter the number of the option you wish to choose\n1) Create a new Reservation\n2) Tick the system clock\n3) Check booking\n4) Cancel Booking\nType Exit to end program");
				input = in.next();
				switch(input)
				{
					case "1":
					{
						CreateReservationStub.addReservation(in);
						break;
					}
					case "2":
					{
						TickClock.tick(in);
						break;
					}
					case "3":
					{
						CheckBooking.check(in);
						break;
					}
					case "4":
					{
						DeleteBooking.deleteBooking(in);
						break;
					}
					case "EXIT":
					case "Exit":
					case "exit":
					{
						end = true;
						break;
					}
					default:
					{
						System.out.println("Input was invalid!");
					}
				}
				
			
			}
			in.close();
		}
	}
	
	
	
	
   
	// Test no: T84
	@Test
	public void testSingleBooking()
	{
		InputStream targetStream = new ByteArrayInputStream("1\nKush\n1\n4\n1 2 3 4\n11:00\nExit".getBytes(Charset.forName("UTF-8")));
		Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
		
		try {
				CreateReservationStub.setSucess(true);
				FlowControllerStub.flowController(sc);
			}
			catch(Exception e)
			{
				
			}

		
		final String standardOutput =  outContent.toString();
		
		String text = "WELCOME TO ZAIZAERIYA RESERVATION AND MENU SYSTEM\r\n"
				+ "\n"
				+ "Please enter the number of the option you wish to choose\n"
				+ "1) Create a new Reservation\n"
				+ "2) Tick the system clock\n"
				+ "3) Check booking\n"
				+ "4) Cancel Booking\n"
				+ "Type Exit to end program\r\n"
				+ "Enter your name: \r\n"
				+ "\r\n"
				+ "1. MongKok\r\n"
				+ "2. Sham Shui Po\r\n"
				+ "3. Kowloon Tong\r\n"
				+ "4. Kowloon Bay\r\n"
				+ "5. Central\r\n"
				+ "Enter the location number: \r\n"
				+ "Enter number of people dining in: \r\n"
				+ "1. Appetizer: Cesar Salad 20HKD\r\n"
				+ "2. Appetizer: Garlic Bread 10HKD\r\n"
				+ "3. Appetizer: Mushroom Soup 40HKD\r\n"
				+ "4. Appetizer: Sausage 10HKD\r\n"
				+ "5. Appetizer: Chicken Fingers 15HKD\r\n"
				+ "6. Main Course: Cheese Pizza 50HKD\r\n"
				+ "7. Main Course: Spaghetti 35HKD\r\n"
				+ "8. Main Course: Doria 27HKD\r\n"
				+ "9. Main Course: Baked Chicken 25HKD\r\n"
				+ "10. Main Course: Risotto 29HKD\r\n"
				+ "11. Dessert: Chocolate Ice Cream 30HKD\r\n"
				+ "12. Dessert: Vanilla Ice Cream 30HKD\r\n"
				+ "13. Dessert: Ice Cream Cake 100HKD\r\n"
				+ "14. Dessert: Pudding 20HKD\r\n"
				+ "15. Dessert: Tiramisu 18HKD\r\n"
				+ "\n"
				+ "Select dishes by entering the number seperated by a space!: \r\n"
				+ "11:00-22:38\r\n"
				+ "\n"
				+ "Select any time from these intervals\r\n"
				+ "Booking is for Kush\r\n"
				+ "Booking is for 4 guests\r\n"
				+ "Your booking time is: 11:00 - 11:21\r\n"
				+ "Cesar Salad - 20 HKD\n"
				+ "Garlic Bread - 10 HKD\n"
				+ "Mushroom Soup - 40 HKD\n"
				+ "Sausage - 10 HKD\n"
				+ "Total amount: 80 HKD\n"
				+ "\n"
				+ "Proceeding to payment....\r\n"
				+ "\n"
				+ "Checking if payment was sucessful....\r\n"
				+ "Payment Sucess\r\n"
				+ "\n"
				+ "Adding booking to list\n"
				+ "Your booking ID is 001\n"
				+ "\r\n"
				+ "\n"
				+ "Please enter the number of the option you wish to choose\n"
				+ "1) Create a new Reservation\n"
				+ "2) Tick the system clock\n"
				+ "3) Check booking\n"
				+ "4) Cancel Booking\n"
				+ "Type Exit to end program\r\n"
				+ "";
		assertEquals(standardOutput, text);		
	}
	
	// Test no: T85
	@Test
	public void Test2Booking()
	{
		InputStream targetStream = new ByteArrayInputStream("1\nKush\n1\n50\n1 2 3 4\n11:00\n1\nBob\n1\n10\n\n\n1 2 3\n12:00\nExit".getBytes(Charset.forName("UTF-8")));
		Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
		
		try {
				CreateReservationStub.setSucess(true);
				FlowControllerStub.flowController(sc);
			}
			catch(Exception e)
			{
				
			}
		final String standardOutput =  outContent.toString();
		
		String text = "WELCOME TO ZAIZAERIYA RESERVATION AND MENU SYSTEM\r\n"
				+ "\n"
				+ "Please enter the number of the option you wish to choose\n"
				+ "1) Create a new Reservation\n"
				+ "2) Tick the system clock\n"
				+ "3) Check booking\n"
				+ "4) Cancel Booking\n"
				+ "Type Exit to end program\r\n"
				+ "Enter your name: \r\n"
				+ "\r\n"
				+ "1. MongKok\r\n"
				+ "2. Sham Shui Po\r\n"
				+ "3. Kowloon Tong\r\n"
				+ "4. Kowloon Bay\r\n"
				+ "5. Central\r\n"
				+ "Enter the location number: \r\n"
				+ "Enter number of people dining in: \r\n"
				+ "1. Appetizer: Cesar Salad 20HKD\r\n"
				+ "2. Appetizer: Garlic Bread 10HKD\r\n"
				+ "3. Appetizer: Mushroom Soup 40HKD\r\n"
				+ "4. Appetizer: Sausage 10HKD\r\n"
				+ "5. Appetizer: Chicken Fingers 15HKD\r\n"
				+ "6. Main Course: Cheese Pizza 50HKD\r\n"
				+ "7. Main Course: Spaghetti 35HKD\r\n"
				+ "8. Main Course: Doria 27HKD\r\n"
				+ "9. Main Course: Baked Chicken 25HKD\r\n"
				+ "10. Main Course: Risotto 29HKD\r\n"
				+ "11. Dessert: Chocolate Ice Cream 30HKD\r\n"
				+ "12. Dessert: Vanilla Ice Cream 30HKD\r\n"
				+ "13. Dessert: Ice Cream Cake 100HKD\r\n"
				+ "14. Dessert: Pudding 20HKD\r\n"
				+ "15. Dessert: Tiramisu 18HKD\r\n"
				+ "\n"
				+ "Select dishes by entering the number seperated by a space!: \r\n"
				+ "11:00-22:38\r\n"
				+ "\n"
				+ "Select any time from these intervals\r\n"
				+ "Booking is for Kush\r\n"
				+ "Booking is for 50 guests\r\n"
				+ "Your booking time is: 11:00 - 11:21\r\n"
				+ "Cesar Salad - 20 HKD\n"
				+ "Garlic Bread - 10 HKD\n"
				+ "Mushroom Soup - 40 HKD\n"
				+ "Sausage - 10 HKD\n"
				+ "Total amount: 80 HKD\n"
				+ "\n"
				+ "Proceeding to payment....\r\n"
				+ "\n"
				+ "Checking if payment was sucessful....\r\n"
				+ "Payment Sucess\r\n"
				+ "\n"
				+ "Adding booking to list\n"
				+ "Your booking ID is 001\n"
				+ "\r\n"
				+ "\n"
				+ "Please enter the number of the option you wish to choose\n"
				+ "1) Create a new Reservation\n"
				+ "2) Tick the system clock\n"
				+ "3) Check booking\n"
				+ "4) Cancel Booking\n"
				+ "Type Exit to end program\r\n"
				+ "Enter your name: \r\n"
				+ "\r\n"
				+ "1. MongKok\r\n"
				+ "2. Sham Shui Po\r\n"
				+ "3. Kowloon Tong\r\n"
				+ "4. Kowloon Bay\r\n"
				+ "5. Central\r\n"
				+ "Enter the location number: \r\n"
				+ "Enter number of people dining in: \r\n"
				+ "1. Appetizer: Cesar Salad 20HKD\r\n"
				+ "2. Appetizer: Garlic Bread 10HKD\r\n"
				+ "3. Appetizer: Mushroom Soup 40HKD\r\n"
				+ "4. Appetizer: Sausage 10HKD\r\n"
				+ "5. Appetizer: Chicken Fingers 15HKD\r\n"
				+ "6. Main Course: Cheese Pizza 50HKD\r\n"
				+ "7. Main Course: Spaghetti 35HKD\r\n"
				+ "8. Main Course: Doria 27HKD\r\n"
				+ "9. Main Course: Baked Chicken 25HKD\r\n"
				+ "10. Main Course: Risotto 29HKD\r\n"
				+ "11. Dessert: Chocolate Ice Cream 30HKD\r\n"
				+ "12. Dessert: Vanilla Ice Cream 30HKD\r\n"
				+ "13. Dessert: Ice Cream Cake 100HKD\r\n"
				+ "14. Dessert: Pudding 20HKD\r\n"
				+ "15. Dessert: Tiramisu 18HKD\r\n"
				+ "\n"
				+ "Select dishes by entering the number seperated by a space!: \r\n"
				+ "Error!: Didn't enter a number!, please enter a number!\n"
				+ "\r\n"
				+ "1. Appetizer: Cesar Salad 20HKD\r\n"
				+ "2. Appetizer: Garlic Bread 10HKD\r\n"
				+ "3. Appetizer: Mushroom Soup 40HKD\r\n"
				+ "4. Appetizer: Sausage 10HKD\r\n"
				+ "5. Appetizer: Chicken Fingers 15HKD\r\n"
				+ "6. Main Course: Cheese Pizza 50HKD\r\n"
				+ "7. Main Course: Spaghetti 35HKD\r\n"
				+ "8. Main Course: Doria 27HKD\r\n"
				+ "9. Main Course: Baked Chicken 25HKD\r\n"
				+ "10. Main Course: Risotto 29HKD\r\n"
				+ "11. Dessert: Chocolate Ice Cream 30HKD\r\n"
				+ "12. Dessert: Vanilla Ice Cream 30HKD\r\n"
				+ "13. Dessert: Ice Cream Cake 100HKD\r\n"
				+ "14. Dessert: Pudding 20HKD\r\n"
				+ "15. Dessert: Tiramisu 18HKD\r\n"
				+ "\n"
				+ "Select dishes by entering the number seperated by a space!: \r\n"
				+ "11:21-22:43\r\n"
				+ "\n"
				+ "Select any time from these intervals\r\n"
				+ "Booking is for Bob\r\n"
				+ "Booking is for 10 guests\r\n"
				+ "Your booking time is: 12:00 - 12:16\r\n"
				+ "Cesar Salad - 20 HKD\n"
				+ "Garlic Bread - 10 HKD\n"
				+ "Mushroom Soup - 40 HKD\n"
				+ "Total amount: 70 HKD\n"
				+ "\n"
				+ "Proceeding to payment....\r\n"
				+ "\n"
				+ "Checking if payment was sucessful....\r\n"
				+ "Payment Sucess\r\n"
				+ "\n"
				+ "Adding booking to list\n"
				+ "Your booking ID is 002\n"
				+ "\r\n"
				+ "\n"
				+ "Please enter the number of the option you wish to choose\n"
				+ "1) Create a new Reservation\n"
				+ "2) Tick the system clock\n"
				+ "3) Check booking\n"
				+ "4) Cancel Booking\n"
				+ "Type Exit to end program\r\n";
		
		assertEquals(standardOutput, text);
	}
	
	//Test case no 86
	@Test
	public void TestBookingAndCheck() {
		InputStream targetStream = new ByteArrayInputStream("1\nKush\n2\n100\n15 13 12 3\n11:00\nyes\nyes\n1\nBob\n2\n20\n1 2 3 4 5\n11:46\n3\n1\n3\n2\n3\n0\n1\nExit".getBytes(Charset.forName("UTF-8")));
		Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
		ScannerTestThread t1 = new ScannerTestThread(sc);
		t1.start();
		try {
				CreateReservationStub.setSucess(false);
				CreateReservationStub.setretries(2);
				FlowControllerStub.flowController(sc);
			}
			catch(Exception e)
			{
				
			}
		final String standardOutput =  outContent.toString();
		
		String text= "WELCOME TO ZAIZAERIYA RESERVATION AND MENU SYSTEM\r\n"
				+ "\n"
				+ "Please enter the number of the option you wish to choose\n"
				+ "1) Create a new Reservation\n"
				+ "2) Tick the system clock\n"
				+ "3) Check booking\n"
				+ "4) Cancel Booking\n"
				+ "Type Exit to end program\r\n"
				+ "Enter your name: \r\n"
				+ "\r\n"
				+ "1. MongKok\r\n"
				+ "2. Sham Shui Po\r\n"
				+ "3. Kowloon Tong\r\n"
				+ "4. Kowloon Bay\r\n"
				+ "5. Central\r\n"
				+ "Enter the location number: \r\n"
				+ "Enter number of people dining in: \r\n"
				+ "1. Appetizer: Cesar Salad 20HKD\r\n"
				+ "2. Appetizer: Garlic Bread 10HKD\r\n"
				+ "3. Appetizer: Mushroom Soup 40HKD\r\n"
				+ "4. Appetizer: Sausage 10HKD\r\n"
				+ "5. Appetizer: Chicken Fingers 15HKD\r\n"
				+ "6. Main Course: Cheese Pizza 50HKD\r\n"
				+ "7. Main Course: Spaghetti 35HKD\r\n"
				+ "8. Main Course: Doria 27HKD\r\n"
				+ "9. Main Course: Baked Chicken 25HKD\r\n"
				+ "10. Main Course: Risotto 29HKD\r\n"
				+ "11. Dessert: Chocolate Ice Cream 30HKD\r\n"
				+ "12. Dessert: Vanilla Ice Cream 30HKD\r\n"
				+ "13. Dessert: Ice Cream Cake 100HKD\r\n"
				+ "14. Dessert: Pudding 20HKD\r\n"
				+ "15. Dessert: Tiramisu 18HKD\r\n"
				+ "\n"
				+ "Select dishes by entering the number seperated by a space!: \r\n"
				+ "11:00-22:13\r\n"
				+ "\n"
				+ "Select any time from these intervals\r\n"
				+ "Booking is for Kush\r\n"
				+ "Booking is for 100 guests\r\n"
				+ "Your booking time is: 11:00 - 11:46\r\n"
				+ "Tiramisu - 18 HKD\n"
				+ "Ice Cream Cake - 100 HKD\n"
				+ "Vanilla Ice Cream - 30 HKD\n"
				+ "Mushroom Soup - 40 HKD\n"
				+ "Total amount: 188 HKD\n"
				+ "\n"
				+ "Proceeding to payment....\r\n"
				+ "\n"
				+ "Checking if payment was sucessful....\r\n"
				+ "Payment failed, please retry\r\n"
				+ "\n"
				+ "Try again? Type Yes or No\r\n"
				+ "Retrying payment....\r\n"
				+ "Payment failed, please retry\r\n"
				+ "\n"
				+ "Try again? Type Yes or No\r\n"
				+ "Retrying payment....\r\n"
				+ "Payment Sucess\r\n"
				+ "\n"
				+ "Adding booking to list\n"
				+ "Your booking ID is 001\n"
				+ "\r\n"
				+ "\n"
				+ "Please enter the number of the option you wish to choose\n"
				+ "1) Create a new Reservation\n"
				+ "2) Tick the system clock\n"
				+ "3) Check booking\n"
				+ "4) Cancel Booking\n"
				+ "Type Exit to end program\r\n"
				+ "Enter your name: \r\n"
				+ "\r\n"
				+ "1. MongKok\r\n"
				+ "2. Sham Shui Po\r\n"
				+ "3. Kowloon Tong\r\n"
				+ "4. Kowloon Bay\r\n"
				+ "5. Central\r\n"
				+ "Enter the location number: \r\n"
				+ "Enter number of people dining in: \r\n"
				+ "1. Appetizer: Cesar Salad 20HKD\r\n"
				+ "2. Appetizer: Garlic Bread 10HKD\r\n"
				+ "3. Appetizer: Mushroom Soup 40HKD\r\n"
				+ "4. Appetizer: Sausage 10HKD\r\n"
				+ "5. Appetizer: Chicken Fingers 15HKD\r\n"
				+ "6. Main Course: Cheese Pizza 50HKD\r\n"
				+ "7. Main Course: Spaghetti 35HKD\r\n"
				+ "8. Main Course: Doria 27HKD\r\n"
				+ "9. Main Course: Baked Chicken 25HKD\r\n"
				+ "10. Main Course: Risotto 29HKD\r\n"
				+ "11. Dessert: Chocolate Ice Cream 30HKD\r\n"
				+ "12. Dessert: Vanilla Ice Cream 30HKD\r\n"
				+ "13. Dessert: Ice Cream Cake 100HKD\r\n"
				+ "14. Dessert: Pudding 20HKD\r\n"
				+ "15. Dessert: Tiramisu 18HKD\r\n"
				+ "\n"
				+ "Select dishes by entering the number seperated by a space!: \r\n"
				+ "11:46-22:28\r\n"
				+ "\n"
				+ "Select any time from these intervals\r\n"
				+ "Booking is for Bob\r\n"
				+ "Booking is for 20 guests\r\n"
				+ "Your booking time is: 11:46 - 12:17\r\n"
				+ "Cesar Salad - 20 HKD\n"
				+ "Garlic Bread - 10 HKD\n"
				+ "Mushroom Soup - 40 HKD\n"
				+ "Sausage - 10 HKD\n"
				+ "Chicken Fingers - 15 HKD\n"
				+ "Total amount: 95 HKD\n"
				+ "\n"
				+ "Proceeding to payment....\r\n"
				+ "\n"
				+ "Checking if payment was sucessful....\r\n"
				+ "Payment Sucess\r\n"
				+ "\n"
				+ "Adding booking to list\n"
				+ "Your booking ID is 002\n"
				+ "\r\n"
				+ "\n"
				+ "Please enter the number of the option you wish to choose\n"
				+ "1) Create a new Reservation\n"
				+ "2) Tick the system clock\n"
				+ "3) Check booking\n"
				+ "4) Cancel Booking\n"
				+ "Type Exit to end program\r\n"
				+ "Enter Booking ID\r\n"
				+ "Found your reservation\r\n"
				+ "Booking is for Kush\r\n"
				+ "Booking is for 100 guests\r\n"
				+ "Your booking time is: 11:00 - 11:46\r\n"
				+ "Tiramisu - 18 HKD\n"
				+ "Ice Cream Cake - 100 HKD\n"
				+ "Vanilla Ice Cream - 30 HKD\n"
				+ "Mushroom Soup - 40 HKD\n"
				+ "Total amount: 188 HKD\n"
				+ "\n"
				+ "Please enter the number of the option you wish to choose\n"
				+ "1) Create a new Reservation\n"
				+ "2) Tick the system clock\n"
				+ "3) Check booking\n"
				+ "4) Cancel Booking\n"
				+ "Type Exit to end program\r\n"
				+ "Enter Booking ID\r\n"
				+ "Found your reservation\r\n"
				+ "Booking is for Bob\r\n"
				+ "Booking is for 20 guests\r\n"
				+ "Your booking time is: 11:46 - 12:17\r\n"
				+ "Cesar Salad - 20 HKD\n"
				+ "Garlic Bread - 10 HKD\n"
				+ "Mushroom Soup - 40 HKD\n"
				+ "Sausage - 10 HKD\n"
				+ "Chicken Fingers - 15 HKD\n"
				+ "Total amount: 95 HKD\n"
				+ "\n"
				+ "Please enter the number of the option you wish to choose\n"
				+ "1) Create a new Reservation\n"
				+ "2) Tick the system clock\n"
				+ "3) Check booking\n"
				+ "4) Cancel Booking\n"
				+ "Type Exit to end program\r\n"
				+ "Enter Booking ID\r\n"
				+ "Error! Booking ID must be greater than 0!\r\n"
				+ "Found your reservation\r\n"
				+ "Booking is for Kush\r\n"
				+ "Booking is for 100 guests\r\n"
				+ "Your booking time is: 11:00 - 11:46\r\n"
				+ "Tiramisu - 18 HKD\n"
				+ "Ice Cream Cake - 100 HKD\n"
				+ "Vanilla Ice Cream - 30 HKD\n"
				+ "Mushroom Soup - 40 HKD\n"
				+ "Total amount: 188 HKD\n"
				+ "\n"
				+ "Please enter the number of the option you wish to choose\n"
				+ "1) Create a new Reservation\n"
				+ "2) Tick the system clock\n"
				+ "3) Check booking\n"
				+ "4) Cancel Booking\n"
				+ "Type Exit to end program\r\n"
				+ "";
		
		assertEquals(standardOutput, text);
	}
	//Test no: T87
	@Test
	public void TestTickClockAndCheck() {
		InputStream targetStream = new ByteArrayInputStream("2\n70\n1\nKush\n1\n20\n1 2 3 8\n12:10\n1\nBob\n2\n30\n1 2 3 4\n12:10\nExit".getBytes(Charset.forName("UTF-8")));
		Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
		try {
				CreateReservationStub.setSucess(true);
				FlowControllerStub.flowController(sc);
			}
			catch(Exception e)
			{
				
			}
		final String standardOutput =  outContent.toString();
		String text ="WELCOME TO ZAIZAERIYA RESERVATION AND MENU SYSTEM\r\n"
				+ "\n"
				+ "Please enter the number of the option you wish to choose\n"
				+ "1) Create a new Reservation\n"
				+ "2) Tick the system clock\n"
				+ "3) Check booking\n"
				+ "4) Cancel Booking\n"
				+ "Type Exit to end program\r\n"
				+ "Current time is: 11:00\r\n"
				+ "How many minutes should the clock be ticked forward? New Time is now 12:10!\n"
				+ "\r\n\n"
				+ "Please enter the number of the option you wish to choose\n"
				+ "1) Create a new Reservation\n"
				+ "2) Tick the system clock\n"
				+ "3) Check booking\n"
				+ "4) Cancel Booking\n"
				+ "Type Exit to end program\r\n"
				+ "Enter your name: \r\n"
				+ "\r\n"
				+ "1. MongKok\r\n"
				+ "2. Sham Shui Po\r\n"
				+ "3. Kowloon Tong\r\n"
				+ "4. Kowloon Bay\r\n"
				+ "5. Central\r\n"
				+ "Enter the location number: \r\n"
				+ "Enter number of people dining in: \r\n"
				+ "1. Appetizer: Cesar Salad 20HKD\r\n"
				+ "2. Appetizer: Garlic Bread 10HKD\r\n"
				+ "3. Appetizer: Mushroom Soup 40HKD\r\n"
				+ "4. Appetizer: Sausage 10HKD\r\n"
				+ "5. Appetizer: Chicken Fingers 15HKD\r\n"
				+ "6. Main Course: Cheese Pizza 50HKD\r\n"
				+ "7. Main Course: Spaghetti 35HKD\r\n"
				+ "8. Main Course: Doria 27HKD\r\n"
				+ "9. Main Course: Baked Chicken 25HKD\r\n"
				+ "10. Main Course: Risotto 29HKD\r\n"
				+ "11. Dessert: Chocolate Ice Cream 30HKD\r\n"
				+ "12. Dessert: Vanilla Ice Cream 30HKD\r\n"
				+ "13. Dessert: Ice Cream Cake 100HKD\r\n"
				+ "14. Dessert: Pudding 20HKD\r\n"
				+ "15. Dessert: Tiramisu 18HKD\r\n"
				+ "\n"
				+ "Select dishes by entering the number seperated by a space!: \r\n"
				+ "12:10-22:33\r\n"
				+ "\n"
				+ "Select any time from these intervals\r\n"
				+ "Booking is for Kush\r\n"
				+ "Booking is for 20 guests\r\n"
				+ "Your booking time is: 12:10 - 12:36\r\n"
				+ "Cesar Salad - 20 HKD\n"
				+ "Garlic Bread - 10 HKD\n"
				+ "Mushroom Soup - 40 HKD\n"
				+ "Doria - 27 HKD\n"
				+ "Total amount: 97 HKD\n"
				+ "\n"
				+ "Proceeding to payment....\r\n"
				+ "\n"
				+ "Checking if payment was sucessful....\r\n"
				+ "Payment Sucess\r\n"
				+ "\n"
				+ "Adding booking to list\n"
				+ "Your booking ID is 001\n"
				+ "\r\n"
				+ "\n"
				+ "Please enter the number of the option you wish to choose\n"
				+ "1) Create a new Reservation\n"
				+ "2) Tick the system clock\n"
				+ "3) Check booking\n"
				+ "4) Cancel Booking\n"
				+ "Type Exit to end program\r\n"
				+ "Enter your name: \r\n"
				+ "\r\n"
				+ "1. MongKok\r\n"
				+ "2. Sham Shui Po\r\n"
				+ "3. Kowloon Tong\r\n"
				+ "4. Kowloon Bay\r\n"
				+ "5. Central\r\n"
				+ "Enter the location number: \r\n"
				+ "Enter number of people dining in: \r\n"
				+ "1. Appetizer: Cesar Salad 20HKD\r\n"
				+ "2. Appetizer: Garlic Bread 10HKD\r\n"
				+ "3. Appetizer: Mushroom Soup 40HKD\r\n"
				+ "4. Appetizer: Sausage 10HKD\r\n"
				+ "5. Appetizer: Chicken Fingers 15HKD\r\n"
				+ "6. Main Course: Cheese Pizza 50HKD\r\n"
				+ "7. Main Course: Spaghetti 35HKD\r\n"
				+ "8. Main Course: Doria 27HKD\r\n"
				+ "9. Main Course: Baked Chicken 25HKD\r\n"
				+ "10. Main Course: Risotto 29HKD\r\n"
				+ "11. Dessert: Chocolate Ice Cream 30HKD\r\n"
				+ "12. Dessert: Vanilla Ice Cream 30HKD\r\n"
				+ "13. Dessert: Ice Cream Cake 100HKD\r\n"
				+ "14. Dessert: Pudding 20HKD\r\n"
				+ "15. Dessert: Tiramisu 18HKD\r\n"
				+ "\n"
				+ "Select dishes by entering the number seperated by a space!: \r\n"
				+ "12:10-22:38\r\n"
				+ "\n"
				+ "Select any time from these intervals\r\n"
				+ "Booking is for Bob\r\n"
				+ "Booking is for 30 guests\r\n"
				+ "Your booking time is: 12:10 - 12:31\r\n"
				+ "Cesar Salad - 20 HKD\n"
				+ "Garlic Bread - 10 HKD\n"
				+ "Mushroom Soup - 40 HKD\n"
				+ "Sausage - 10 HKD\n"
				+ "Total amount: 80 HKD\n"
				+ "\n"
				+ "Proceeding to payment....\r\n"
				+ "\n"
				+ "Checking if payment was sucessful....\r\n"
				+ "Payment Sucess\r\n"
				+ "\n"
				+ "Adding booking to list\n"
				+ "Your booking ID is 002\n"
				+ "\r\n"
				+ "\n"
				+ "Please enter the number of the option you wish to choose\n"
				+ "1) Create a new Reservation\n"
				+ "2) Tick the system clock\n"
				+ "3) Check booking\n"
				+ "4) Cancel Booking\n"
				+ "Type Exit to end program\r\n"
				+ "";
		assertEquals(standardOutput, text);
	}
	
	//Test no: T88
	@Test
	public void TestDelete() {
		InputStream targetStream = new ByteArrayInputStream("1\nKush\n1\n10\n1 2 3 4\n11:00\n3\n1\n4\n1\nYes\n3\n1\nExit".getBytes(Charset.forName("UTF-8")));
		Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
		try {
				CreateReservationStub.setSucess(true);
				FlowControllerStub.flowController(sc);
			}
			catch(Exception e)
			{
				
			}
		final String standardOutput =  outContent.toString();
		String text ="WELCOME TO ZAIZAERIYA RESERVATION AND MENU SYSTEM\r\n"
				+ "\n"
				+ "Please enter the number of the option you wish to choose\n"
				+ "1) Create a new Reservation\n"
				+ "2) Tick the system clock\n"
				+ "3) Check booking\n"
				+ "4) Cancel Booking\n"
				+ "Type Exit to end program\r\n"
				+ "Enter your name: \r\n"
				+ "\r\n"
				+ "1. MongKok\r\n"
				+ "2. Sham Shui Po\r\n"
				+ "3. Kowloon Tong\r\n"
				+ "4. Kowloon Bay\r\n"
				+ "5. Central\r\n"
				+ "Enter the location number: \r\n"
				+ "Enter number of people dining in: \r\n"
				+ "1. Appetizer: Cesar Salad 20HKD\r\n"
				+ "2. Appetizer: Garlic Bread 10HKD\r\n"
				+ "3. Appetizer: Mushroom Soup 40HKD\r\n"
				+ "4. Appetizer: Sausage 10HKD\r\n"
				+ "5. Appetizer: Chicken Fingers 15HKD\r\n"
				+ "6. Main Course: Cheese Pizza 50HKD\r\n"
				+ "7. Main Course: Spaghetti 35HKD\r\n"
				+ "8. Main Course: Doria 27HKD\r\n"
				+ "9. Main Course: Baked Chicken 25HKD\r\n"
				+ "10. Main Course: Risotto 29HKD\r\n"
				+ "11. Dessert: Chocolate Ice Cream 30HKD\r\n"
				+ "12. Dessert: Vanilla Ice Cream 30HKD\r\n"
				+ "13. Dessert: Ice Cream Cake 100HKD\r\n"
				+ "14. Dessert: Pudding 20HKD\r\n"
				+ "15. Dessert: Tiramisu 18HKD\r\n"
				+ "\n"
				+ "Select dishes by entering the number seperated by a space!: \r\n"
				+ "11:00-22:38\r\n"
				+ "\n"
				+ "Select any time from these intervals\r\n"
				+ "Booking is for Kush\r\n"
				+ "Booking is for 10 guests\r\n"
				+ "Your booking time is: 11:00 - 11:21\r\n"
				+ "Cesar Salad - 20 HKD\n"
				+ "Garlic Bread - 10 HKD\n"
				+ "Mushroom Soup - 40 HKD\n"
				+ "Sausage - 10 HKD\n"
				+ "Total amount: 80 HKD\n"
				+ "\n"
				+ "Proceeding to payment....\r\n"
				+ "\n"
				+ "Checking if payment was sucessful....\r\n"
				+ "Payment Sucess\r\n"
				+ "\n"
				+ "Adding booking to list\n"
				+ "Your booking ID is 001\n"
				+ "\r\n"
				+ "\n"
				+ "Please enter the number of the option you wish to choose\n"
				+ "1) Create a new Reservation\n"
				+ "2) Tick the system clock\n"
				+ "3) Check booking\n"
				+ "4) Cancel Booking\n"
				+ "Type Exit to end program\r\n"
				+ "Enter Booking ID\r\n"
				+ "Found your reservation\r\n"
				+ "Booking is for Kush\r\n"
				+ "Booking is for 10 guests\r\n"
				+ "Your booking time is: 11:00 - 11:21\r\n"
				+ "Cesar Salad - 20 HKD\n"
				+ "Garlic Bread - 10 HKD\n"
				+ "Mushroom Soup - 40 HKD\n"
				+ "Sausage - 10 HKD\n"
				+ "Total amount: 80 HKD\n"
				+ "\n"
				+ "Please enter the number of the option you wish to choose\n"
				+ "1) Create a new Reservation\n"
				+ "2) Tick the system clock\n"
				+ "3) Check booking\n"
				+ "4) Cancel Booking\n"
				+ "Type Exit to end program\r\n"
				+ "Enter booking ID to delete!\r\n"
				+ "Found booking!\n"
				+ "\r\n"
				+ "Booking is for Kush\r\n"
				+ "Booking is for 10 guests\r\n"
				+ "Your booking time is: 11:00 - 11:21\r\n"
				+ "Cesar Salad - 20 HKD\n"
				+ "Garlic Bread - 10 HKD\n"
				+ "Mushroom Soup - 40 HKD\n"
				+ "Sausage - 10 HKD\n"
				+ "Total amount: 80 HKD\n"
				+ "\n"
				+ "Confirm deletion of booking? Enter Yes or No to confirm\r\n"
				+ "Deleting...\r\n"
				+ "Deleted!\r\n"
				+ "\n"
				+ "Please enter the number of the option you wish to choose\n"
				+ "1) Create a new Reservation\n"
				+ "2) Tick the system clock\n"
				+ "3) Check booking\n"
				+ "4) Cancel Booking\n"
				+ "Type Exit to end program\r\n"
				+ "Enter Booking ID\r\n"
				+"Your booking could not be found!\n"
				+ "Please check your booking ID!\r\n"
				+ "\n"
				+ "Please enter the number of the option you wish to choose\n"
				+ "1) Create a new Reservation\n"
				+ "2) Tick the system clock\n"
				+ "3) Check booking\n"
				+ "4) Cancel Booking\n"
				+ "Type Exit to end program\r\n";
		assertEquals(standardOutput, text);
	}
		
	
}

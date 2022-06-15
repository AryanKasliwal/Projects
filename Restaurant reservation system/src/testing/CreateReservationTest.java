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
import fileReader.Read;
import logistics.Clock;
import logistics.Location;
import logistics.Reservation;
import logistics.TimePeriod;
import payment.Bill;
import payment.Info;
import payment.Payment;
import restaurantFood.Appetizer;
import restaurantFood.Dessert;
import restaurantFood.Food;
import restaurantFood.MainCourse;
import restaurantFood.Restaurant;
import userInput.CreateReservation;


public class CreateReservationTest {

	private final static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final static ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final static PrintStream originalOut = System.out;
	private final static PrintStream originalErr = System.err;
	private static Location loc = new Location("MK",10);
	private static Info info;
	private static ArrayList<Food> foodlist;
	private static Reservation res; 
	
	@BeforeAll
	public static void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	    Read.setmenu("menu.txt");
	    
	    	foodlist  = new ArrayList<>();
		    foodlist.add(new MainCourse("Main",10,10));
		    foodlist.add(new Appetizer("App",15,15));
		    foodlist.add(new Dessert("Dessert",5,5));
		    
		 info  = new Info("Kush", loc, 5, foodlist);
		 res = new Reservation(info);
	}
	
	@AfterEach
	public void reset() {
		outContent.reset();
		Restaurant.removeReservations();
	    Restaurant.removeAllLocation();
	    Bill.resetBookingID();
	}
	
	@AfterAll
	public static void resetAll() {    
		System.setOut(originalOut);
	    System.setErr(originalErr);
	    Restaurant.removeReservations();
	    Restaurant.removeAllLocation();
	    Bill.resetBookingID();
		
	}
	
	static class CreateReservationStubMain extends CreateReservation{
		
		 public static void addReservation(Scanner in, Boolean payment) {
			System.out.println("Enter your name: ");
			String name = in.next();
			
			System.out.println("");
			 
	        Location chosen_loc = selectLocation(in);
	        int num_people = selectDiners(in, chosen_loc);
	        ArrayList <Food> chosen_dishes = selectDishes(in);
	        
	        Info info = new Info(name, chosen_loc, num_people, chosen_dishes);
	        Reservation res = new Reservation(info);
	        
	        Clock[] clocks = selectTime(in , chosen_loc, res);
	        
	        if(clocks == null)
	        	return;
	        
	        TimePeriod tp = new TimePeriod(clocks[0], clocks[1]);
	        info.setTimePeriod(tp);
	
	        if (createPayment(in, info, res, payment))
	            chosen_loc.addReservationLoc(res);
	        else
	            System.out.println("Your booking was not confirmed, please retry ordering to make a new booking!");
 		 }

		 public static ArrayList<Food> selectDishes(Scanner in){
			return foodlist;
		 }
		 public static Location selectLocation(Scanner in){
		 	 return loc;
		 }
		 
		 public static Clock[] selectTime(Scanner in, Location loc, Reservation res){	
			 try {
				return new Clock[]{Clock.createClockWithTime("11:00"),Clock.createClockWithTime("12:00")};
			} catch (ExIncorrectClockFormat | ExInvalidClockRange e) {
				e.printStackTrace();
			}
			 return null;
		 }
		 
		 public static int selectDiners(Scanner in, Location chosen_loc){
			 return 10;
		 }
		 
		 public static boolean createPayment(Scanner in, Info info, Reservation res, Boolean payment){
			 if(payment)
				 res.setBill(new Bill(info));
			 else
				 return false;
			 return true;
		 }
	}
	
	// Test no: T62
	@Test
	public void TestCreateReservation() {
		InputStream targetStream = new ByteArrayInputStream("\n1 2 3 4".getBytes(Charset.forName("UTF-8")));
		
		Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
		ArrayList<Food> results = null;
		try {
			results = CreateReservation.selectDishes(sc);
		}
		catch(Exception e)
		{
			
		}
		ArrayList<Food> dishes = new ArrayList<Food>();

		for(int c = 0; c<4; c++)
		{
			dishes.add(Restaurant.getMenu().get(c));
		}
		
		boolean worked = true;
		
		for(int c = 0;c<results.size(); c++)
		{
			if(results.get(c)!=dishes.get(c))
					worked=false;
		}
		assertEquals(worked, true);
	}
	
	// Test no: T63
	@Test
	public void TestCreateReservationError1() {
		InputStream targetStream = new ByteArrayInputStream("\n16".getBytes(Charset.forName("UTF-8")));
		
		Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
		
		ScannerTestThread t1 = new ScannerTestThread(sc);
		t1.start();
		try {
			CreateReservation.selectDishes(sc);
		}
		catch(Exception e)
		{
			
		}
		
		String text = "Error!: Entered an invalid number!, please enter a number in range 1-15\n";
		assertEquals(outContent.toString().contains(text), true);
	}
	
	// Test no: T64
	@Test
	public void TestCreateReservationError2() {
		InputStream targetStream = new ByteArrayInputStream("\nab".getBytes(Charset.forName("UTF-8")));
		
		Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
		
		ScannerTestThread t1 = new ScannerTestThread(sc);
		t1.start();
		try {
			CreateReservation.selectDishes(sc);
		}
		catch(Exception e)
		{
			
		}
		
		String text = "Error!: Didn't enter a number!, please enter a number!\n";
		assertEquals(outContent.toString().contains(text), true);
	}
	
	// Test no: T65
	@Test
	public void TestCreateReservationError3() {
		InputStream targetStream = new ByteArrayInputStream("\n\n".getBytes(Charset.forName("UTF-8")));
		
		Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
		
		ScannerTestThread t1 = new ScannerTestThread(sc);
		t1.start();
		try {
			CreateReservation.selectDishes(sc);
		}
		catch(Exception e)
		{
			
		}
		
		String text = "Error!: Didn't enter a number!, please enter a number!\n";
		
		assertEquals(outContent.toString().contains(text), true);
	}
	
	// Test no: T66
	@Test
	public void TestselectDiners() {
		InputStream targetStream = new ByteArrayInputStream("5".getBytes(Charset.forName("UTF-8")));
		
		Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
		
		Restaurant.addLocation(loc);
		ScannerTestThread t1 = new ScannerTestThread(sc);
		int result = -1;
		t1.start();
		try {
			result = CreateReservation.selectDiners(sc, loc);
		}
		catch(Exception e)
		{
			
		}
		assertEquals(result, 5);
	}
	
	// Test no: T67
	@Test
	public void TestErrorselectDiners1() {
		InputStream targetStream = new ByteArrayInputStream("11".getBytes(Charset.forName("UTF-8")));
		
		Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
	
		Restaurant.addLocation(loc);
		ScannerTestThread t1 = new ScannerTestThread(sc);
		t1.start();
		try {
			CreateReservation.selectDiners(sc, loc);
		}
		catch(Exception e)
		{
			
		}
		String text = "Enter number of people dining in: \r\nError!: Number of diners, 11 exceeds max capacity of 10!\r\n";
		assertEquals(outContent.toString(),text);
	}
	
	// Test no: T68
	@Test
	public void TestErrorselectDiners2() {
		InputStream targetStream = new ByteArrayInputStream("-1".getBytes(Charset.forName("UTF-8")));
		
		Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
		
		Restaurant.addLocation(loc);
		ScannerTestThread t1 = new ScannerTestThread(sc);
		t1.start();
		try {
			CreateReservation.selectDiners(sc, loc);
		}
		catch(Exception e)
		{
			
		}
		String text = "Enter number of people dining in: \r\nError!: Number of diners, -1 is not a proper amount of diners!\nPlease enter a number from 1 - 10\r\n";
		assertEquals(outContent.toString(),text);
	}
	
	// Test no: T69
	@Test
	public void TestErrorselectDiners3() {
		InputStream targetStream = new ByteArrayInputStream("abc".getBytes(Charset.forName("UTF-8")));
		
		Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
		
		Restaurant.addLocation(loc);
		ScannerTestThread t1 = new ScannerTestThread(sc);
		t1.start();
		try {
			CreateReservation.selectDiners(sc, loc);
		}
		catch(Exception e)
		{
			
		}
		String text = "Error!: Didn't enter a number!, please enter a number!";
		assertEquals(outContent.toString().contains(text),true);
	}
	
	// Test no: T70
	@Test
	public void TestverifyTime() throws ExInvalidClockRange, ExIncorrectClockFormat {
		InputStream targetStream = new ByteArrayInputStream("11:15".getBytes(Charset.forName("UTF-8")));
		Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
		ArrayList<TimePeriod> tp = new ArrayList<>();
		tp.add(new TimePeriod("11:00-11:30"));

		Clock tmp = null;
		Clock real = Clock.createClockWithTime("11:15");
		try {
		
			tmp=CreateReservation.verifyclockinput(tp, sc);
				
		}
		catch(Exception e){
				
		}
		
		assertEquals(tmp.equals(real),true);
	}
	
	// Test no: T71
	@Test
	public void TestverifyTime2() throws ExInvalidClockRange, ExIncorrectClockFormat {
		InputStream targetStream = new ByteArrayInputStream("11:35".getBytes(Charset.forName("UTF-8")));
		Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
		ArrayList<TimePeriod> tp = new ArrayList<>();
		tp.add(new TimePeriod("11:00-11:30"));

		ScannerTestThread t = new ScannerTestThread(sc);
		t.start();
		try {
		
			CreateReservation.verifyclockinput(tp, sc);
				
		}
		catch(Exception e){
				
		}
		String text = "Error! The inputted time was not in the given range! Please input a time in the ranges!";
		assertEquals(outContent.toString().contains(text),true);
	}
	
	// Test no: T72
	@Test
	public void TestverifyTime3() throws ExInvalidClockRange, ExIncorrectClockFormat {
		InputStream targetStream = new ByteArrayInputStream("11::35".getBytes(Charset.forName("UTF-8")));
		Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
		ArrayList<TimePeriod> tp = new ArrayList<>();
		tp.add(new TimePeriod("11:00-11:30"));
		ScannerTestThread t = new ScannerTestThread(sc);
		t.start();
		try {
		
			CreateReservation.verifyclockinput(tp, sc);
				
		}
		catch(Exception e){
				
		}
		String text = "Error!: Incorrect input format for the clock, it should be hours:mins not 11::35";
		assertEquals(outContent.toString().contains(text),true);
	}
	
	// Test no: T73
	@Test
	public void TestverifyTime4() throws ExInvalidClockRange, ExIncorrectClockFormat {
		InputStream targetStream = new ByteArrayInputStream("25:75".getBytes(Charset.forName("UTF-8")));
		Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
		ArrayList<TimePeriod> tp = new ArrayList<>();
		tp.add(new TimePeriod("11:00-11:30"));
		ScannerTestThread t = new ScannerTestThread(sc);
		t.start();
		try {
		
			CreateReservation.verifyclockinput(tp, sc);
				
		}
		catch(Exception e){
				
		}
		String text = "Error!: Time of 25:75 is not in range!";
		assertEquals(outContent.toString().contains(text),true);
	}
	
	// Test no: T74
	@Test
	public void TestselectLocation() {
		InputStream targetStream = new ByteArrayInputStream("2".getBytes(Charset.forName("UTF-8")));
		Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
		Restaurant.addLocation(loc);
		ScannerTestThread t = new ScannerTestThread(sc);
		t.start();
		try {
			CreateReservation.selectLocation(sc);
		}
		catch(Exception e) {
			
		}
		
		String text = "Error!: Entered an invalid number!, please enter a number in range 1-1";
		assertEquals(outContent.toString().contains(text),true);
	}
	
	// Test no: T75
	@Test
	public void TestselectLocation2() {
		InputStream targetStream = new ByteArrayInputStream("abc".getBytes(Charset.forName("UTF-8")));
		Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
		Restaurant.addLocation(loc);
		ScannerTestThread t = new ScannerTestThread(sc);
		t.start();
		try {
			CreateReservation.selectLocation(sc);
		}
		catch(Exception e) {
			
		}
		
		String text = "Error!: Didn't enter a number!, please enter a number!";
		assertEquals(outContent.toString().contains(text),true);
	}
	
	// Test no: T76
	@Test
	public void TestselectLocation3() {
		InputStream targetStream = new ByteArrayInputStream("1".getBytes(Charset.forName("UTF-8")));
		Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
		Restaurant.addLocation(loc);
		Restaurant.addLocation(new Location("MK",10));
		ScannerTestThread t = new ScannerTestThread(sc);
		t.start();
		Location result =null;
		try {
			result = CreateReservation.selectLocation(sc);
		}
		catch(Exception e) {
			
		}

		assertEquals(result,loc);
	}
	
	// Test no: T77
	@Test
	public void TestselectTime() throws ExIncorrectClockFormat, ExInvalidClockRange {
		InputStream targetStream = new ByteArrayInputStream("11:00".getBytes(Charset.forName("UTF-8")));
		Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
		Restaurant.addLocation(loc);
		ScannerTestThread t = new ScannerTestThread(sc);
		t.start();
		Clock[] result =null;
		try {
			result = CreateReservation.selectTime(sc,loc,res);
		}
		catch(Exception e) {
			
		}
		boolean worked = false;
		if(result[0].equals(Clock.createClockWithTime("11:00")) && result[1].equals(Clock.createClockWithTime("11:30")))
			worked = true;
		assertEquals(worked,true);
	}
	
	// Test no: T78
	@Test
	public void TestselectTime2() throws ExIncorrectClockFormat, ExInvalidClockRange {
		Scanner sc = null;
		Food testFood = new Appetizer("App",Integer.MAX_VALUE-2000,15);
		foodlist.add(testFood);
		
		
		Restaurant.addLocation(loc);

		try {
			CreateReservation.selectTime(sc,loc,res);
		}
		catch(Exception e) {
			
		}
		
		assertEquals(outContent.toString().contains("No periods are available"),true);			
	}
	
	// Test no: T79
	@Test
	public void TestPayment() throws ExIncorrectClockFormat, ExInvalidClockRange {

		Scanner sc = null; 
	
		Clock[] time = {Clock.createClockWithTime("11:00"), Clock.createClockWithTime("12:00")}; 
		info.setTimePeriod(new TimePeriod(time[0], time[1]));			
		Boolean worked = true;
		try {
			CreateReservationStub.setSucess(true);
		worked = CreateReservationStub.createPayment(sc, info, res);
		}
		catch(Exception e) {
			
		}
		assertEquals(worked, true);
	}
	
	// Test no: T80
	@Test
	public void TestPayment2() throws ExIncorrectClockFormat, ExInvalidClockRange {
		InputStream targetStream = new ByteArrayInputStream("Yes".getBytes(Charset.forName("UTF-8")));
		Scanner sc = new Scanner(new InputStreamReader(targetStream));
		Clock[] time = {Clock.createClockWithTime("11:00"), Clock.createClockWithTime("12:00")}; 
		info.setTimePeriod(new TimePeriod(time[0], time[1]));			
		Boolean worked = false;
		try {
			CreateReservationStub.setSucess(false);
			CreateReservationStub.setretries(1);
			
			worked = CreateReservationStub.createPayment(sc, info, res);
		}
		catch(Exception e) {
			
		}
		
		assertEquals(worked, true);
	}
	
	// Test no: T81
	@Test
	public void TestPayment3() throws ExIncorrectClockFormat, ExInvalidClockRange {
		InputStream targetStream = new ByteArrayInputStream("No".getBytes(Charset.forName("UTF-8")));
		Scanner sc = new Scanner(new InputStreamReader(targetStream));
		Clock[] time = {Clock.createClockWithTime("11:00"), Clock.createClockWithTime("12:00")}; 
		info.setTimePeriod(new TimePeriod(time[0], time[1]));			
		Boolean worked = false;
		try {
			CreateReservationStub.setSucess(false);
			CreateReservationStub.setretries(1);
			
			worked = CreateReservationStub.createPayment(sc, info, res);
		}
		catch(Exception e) {
			
		}
		assertEquals(worked, false);
	}
	
	//Test no: T82
	@Test
	public void TestAddReservation() throws ExIncorrectClockFormat, ExInvalidClockRange {
		InputStream targetStream = new ByteArrayInputStream("Kush".getBytes(Charset.forName("UTF-8")));
		Scanner sc = new Scanner(new InputStreamReader(targetStream));
		Restaurant.addLocation(loc);
		try {
			CreateReservationStubMain.addReservation(sc,true);
			
		}
		catch(Exception e) {
			
		}
		Reservation created  = loc.getReservations().get(0);

		created.showBill();
		String text="Enter your name: \r\n"
				+ "\r\n"
				+ "Booking is for Kush\r\n"
				+ "Booking is for 10 guests\r\n"
				+ "Your booking time is: 11:00 - 12:00\r\n"
				+ "Main - 10 HKD\n"
				+ "App - 15 HKD\n"
				+ "Dessert - 5 HKD\n"
				+ "Total amount: 30 HKD\n";
		assertEquals(outContent.toString(), text);
	}
	
	////Test no: T83
	@Test
	public void TestAddReservation2() throws ExIncorrectClockFormat, ExInvalidClockRange {
		InputStream targetStream = new ByteArrayInputStream("Kush".getBytes(Charset.forName("UTF-8")));
		Scanner sc = new Scanner(new InputStreamReader(targetStream));
		Restaurant.addLocation(loc);
		try {
			CreateReservationStubMain.addReservation(sc,false);
			
		}
		catch(Exception e) {
			
		}
	
		String text="Enter your name: \r\n"
				+ "\r\n"
				+ "Your booking was not confirmed, please retry ordering to make a new booking!\r\n";
		assertEquals(outContent.toString(), text);
	}
	
}
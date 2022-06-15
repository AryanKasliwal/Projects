package testing;



import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fileReader.Read;
import logistics.Location;
import restaurantFood.Appetizer;
import restaurantFood.Dessert;
import restaurantFood.Food;
import restaurantFood.MainCourse;
import restaurantFood.Restaurant;



public class ReaderTest {
	
	private final static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final static ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final static PrintStream originalOut = System.out;
	private final static PrintStream originalErr = System.err;

	@BeforeAll
	public static void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@AfterAll
	public static void restoreStreams() {
	    System.setOut(originalOut);
	    System.setErr(originalErr);
	    ArrayList<Food> dishes = Restaurant.getMenu();
	    while(dishes.size()>0)
	    	dishes.remove(0);
	}
	
	@AfterEach
	public void reset() {
		outContent.reset();
	}
	
	// Test no: T04
	@Test
	public void FileErrorMenu() {
		Read.setmenu("test");
		assertEquals("File error for menu\r\n", outContent.toString());
	}
	
	// Test no: T05
	@Test
	public void FileErrorLocation() {
		Read.setuplocations("test");
		assertEquals("File error for locations\r\n", outContent.toString());
	}
	
	// Test no: T06
	@Test
	public void WrongFileLocation() {
		Read.setuplocations("testLocation.txt");
		assertEquals("Reading error for location\r\n", outContent.toString());
	}
	
	// Test no: T07
	@Test
	public void WrongFileMenu() {
		Read.setmenu("testMenu.txt");
		assertEquals("Reading error for menu\r\n", outContent.toString());
	}
	
	// Test no: T08
	@Test
	public void TestMenuFile() {
		Read.setmenu("testMenu2.txt");
		
		ArrayList<Food> test = Restaurant.getMenu();
		Food main = new MainCourse("testmain",10,10);
		Food appe = new Appetizer("testapp",5,5);
		Food des = new Dessert("testdes",20,20);
		
		ArrayList<Food> dish = new ArrayList<Food>();
		dish.add(appe);
		dish.add(main);
		dish.add(des);
		
		boolean flag = true;
		
		if(test.size() == dish.size())
		{
			for(int i = 0; i<test.size(); i++)
			{
				if(!(dish.get(i).getName().equals(test.get(i).getName()))&&(dish.get(i).getPrice()==test.get(i).getPrice()))
				{
					flag = false;
				}
			}
		}
		
		assertEquals(true, flag);

	}
	
	// Test no: T09
	@Test 
	public void TestLocation()
	{
		Read.setuplocations("testLocation2.txt");
		
		Location l1 = new Location("MK", 10);
		Location l2 = new Location("SSP", 15);
		Location l3 = new Location("CTR", 20);
		
		ArrayList<Location> list = new ArrayList<Location>();
		
		list.add(l1);
		list.add(l2);
		list.add(l3);
		
		boolean flag = true;
		
		ArrayList<Location> test = Restaurant.getLocations();
		
		if(list.size()==test.size())
		{
			for(int i=0; i< list.size(); i++)
			{
				if(list.get(i).equals(test.get(i))) {
					flag = false;
				}
					
			}
		}
		assertEquals(true, flag);	
	}	
}

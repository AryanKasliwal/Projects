package fileReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import restaurantFood.Appetizer;
import restaurantFood.Dessert;
import restaurantFood.MainCourse;
import restaurantFood.Restaurant;
import logistics.Location;
public class Read {

	public static void setmenu(String filename ) {
		Scanner reader = null;
	

		try {
			
			File menu = new File(filename);
			reader = new Scanner(menu);

			String currentType = "Appetizer";	

		while(reader.hasNext())
			{
				String line = reader.nextLine();
				String[] split = line.split(",");
				if(split.length == 1) {
					currentType =  line;
					continue;
				}
				
					switch(currentType) {
						case "Appetizer": {
							Restaurant.addMenuItem(new Appetizer(split[0], Integer.parseInt(split[1]),  Integer.parseInt(split[2])));	
							break;
						}
						case "Main Course": {
							Restaurant.addMenuItem(new MainCourse(split[0], Integer.parseInt(split[1]),  Integer.parseInt(split[2])));	
							break;
						}
						case "Dessert": {
							Restaurant.addMenuItem(new Dessert(split[0], Integer.parseInt(split[1]),  Integer.parseInt(split[2])));	
							break;
						}
						default: throw new Exception();
					}
			}
		} 
		catch(FileNotFoundException e) {
			System.out.println("File error for menu");
		}
		catch(Exception e)
		{
			System.out.println("Reading error for menu");
		}
		finally {
			if(reader!=null)
			reader.close();
		}
	}
	
	

	public static void setuplocations(String filename)
	{
	
		File locations = new File(filename);

		Scanner reader = null;
		try {
			reader = new Scanner(locations);

			while(reader.hasNext())
			{
				String line = reader.nextLine();

				String[] splited = line.split(",");
				Restaurant.addLocation(new Location(splited[0], Integer.parseInt(splited[1])));
			}
		} catch (FileNotFoundException e) {
			System.out.println("File error for locations");
		}
		catch(Exception e)
		{
			System.out.println("Reading error for location");
		}
		finally {
			if(reader!=null)
			reader.close();
		}
	}
}

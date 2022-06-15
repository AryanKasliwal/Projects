package userInput;

import java.util.InputMismatchException;
import java.util.Scanner;
import logistics.Reservation;
import restaurantFood.Restaurant;

public class CheckBooking {
	
	
	
	public static boolean check(Scanner in) {
		System.out.println("Enter Booking ID");
		
		int id = -1;
		while(true)
		try {
				id = in.nextInt();
				if(id>0)
					break;
				else
					System.out.println("Error! Booking ID must be greater than 0!");
			}
			catch(InputMismatchException  e)
			{
				System.out.println("Error! Please input a valid booking id number!");
			}
			
		
				Object[] res = Restaurant.findBooking(id);
	
		if(res!=null)
		{
			
					System.out.println("Found your reservation");
					((Reservation) res[0]).showBill();
					return true;
				
		}
			
		else{
			System.out.println("Your booking could not be found!\nPlease check your booking ID!");
			return false;
		}
		
	}


	
}

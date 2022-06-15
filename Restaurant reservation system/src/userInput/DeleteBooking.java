package userInput;


import java.util.InputMismatchException;
import java.util.Scanner;

import logistics.Location;
import logistics.Reservation;
import restaurantFood.Restaurant;

public class DeleteBooking {

	public static void deleteBooking(Scanner in)
	{
		System.out.println("Enter booking ID to delete!");
		int id = -1;
		
		while(true)
		try {
			
			id = in.nextInt();
			if(id>0)
				break;
			else
				System.out.println("Error! Booking ID must be greater than 0!");
		}
		catch(InputMismatchException e)
		{
			System.out.println("Error! Please input a valid booking id number!");
		}
		
		
				Object[] res = Restaurant.findBooking(id);
			
				if(res!=null)
				{
					Reservation r = (Reservation) res[0];
					Location loc = (Location) res[1];
					System.out.println("Found booking!\n");
					
					r.showBill();
					
					System.out.println("\nConfirm deletion of booking? Enter Yes or No to confirm");
					String input = in.next();
					
					if(input.toLowerCase().equals("yes"))
					{
						System.out.println("Deleting...");
						loc.removeReservation(r);
						System.out.println("Deleted!");
						return;
					}
					else
						return;
				}
					
				
			System.out.println("Booking not found!, returning to menu");
			return;
		
		
	}

	
}



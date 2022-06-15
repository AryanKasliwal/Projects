package userInput;
import java.util.Scanner;

import fileReader.Read;
public class FlowController {

	private static FlowController instance;
	
	protected FlowController() { 
		instance = new FlowController();

	}
	
	public static FlowController getInstance() { return instance; }
	

	public static void main(String[] args) {
		
		flowController(new Scanner(System.in));		
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
					CreateReservation.addReservation(in);
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

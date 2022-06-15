package userInput;

import java.util.InputMismatchException;
import java.util.Scanner;

import logistics.Clock;

public class TickClock {

	public static void tick(Scanner in) {
		
		Clock curClock = Clock.getClock();
		System.out.println("Current time is: "+curClock);
		System.out.print("How many minutes should the clock be ticked forward? ");
		int tick=0;
		while(true)
			try {
				tick =in.nextInt();
				
				if(tick>0)
						break;
				else
					System.out.println("Error! Please input a postiive number!");
			}
			catch(InputMismatchException e)	{
				System.out.println("Error! Not a number!");
			}
	
		curClock.tickMinutes(tick);
		System.out.println("New Time is now "+curClock+"!\n");
			
	}
	
}

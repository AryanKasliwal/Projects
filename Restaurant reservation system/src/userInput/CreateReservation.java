package userInput;


import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import logistics.Clock;
import logistics.Location;
import logistics.Reservation;
import logistics.TimePeriod;
import payment.Bill;
import payment.Info;
import payment.Payment;
import restaurantFood.Food;
import restaurantFood.Restaurant;
import Exceptions.ExExceedCapacity;
import Exceptions.ExIncorrectClockFormat;
import Exceptions.ExInvalidClockRange;
import Exceptions.ExInvalidDinerAmount;

public class CreateReservation {

    public static void addReservation(Scanner in) {
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

        if (createPayment(in, info, res))
            chosen_loc.addReservationLoc(res);
        else
            System.out.println("Your booking was not confirmed, please retry ordering to make a new booking!");
    }
 
    
    public static ArrayList<Food> selectDishes(Scanner in) {
    	ArrayList <Food> menu = Restaurant.getMenu();
    	ArrayList<Food> chosen_dishes = new ArrayList<>();

        for (int i = 0; i < menu.size(); i++)
            System.out.println(i + 1 + ". " + menu.get(i).getType() + ": " + menu.get(i).getName() + " " + menu.get(i).getPrice() + "HKD");

        while (true) {
            try {
                chosen_dishes = new ArrayList<>();

                System.out.println("\nSelect dishes by entering the number seperated by a space!: ");

                in.nextLine();
                String line = in .nextLine();
                String[] split = line.split(" ");

                for (String c: split)
                    chosen_dishes.add(menu.get(Integer.parseInt(c) - 1));
  
            	return chosen_dishes;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Error!: Entered an invalid number!, please enter a number in range 1-" + menu.size() + '\n');
            }catch (NumberFormatException e) {
                System.out.println("Error!: Didn't enter a number!, please enter a number!\n");                
            }
            
            for (int i = 0; i < menu.size(); i++)
                System.out.println(i + 1 + ". " + menu.get(i).getType() + ": " + menu.get(i).getName() + " " + menu.get(i).getPrice() + "HKD");
        }
    }

    public static int selectDiners(Scanner in, Location chosen_loc) {
		System.out.println("Enter number of people dining in: ");
		int num_people = -1;
		
		while (true) {
			try {
				num_people = in .nextInt();

				if (num_people > chosen_loc.getMaxCapcacity())
					throw new ExExceedCapacity(num_people, chosen_loc.getMaxCapcacity());
				if (num_people < 1)
					throw new ExInvalidDinerAmount(num_people, chosen_loc.getMaxCapcacity());
			  	return num_people;
			} catch (InputMismatchException e) {
				System.out.println("Error!: Didn't enter a number!, please enter a number!\n");
				in.nextLine();
			} catch (ExExceedCapacity | ExInvalidDinerAmount  e) {
				System.out.println(e.getMessage()); 
				in.nextLine();
			}
		}
    }

    public static Clock[] selectTime(Scanner in , Location chosen_loc, Reservation res) {
        ArrayList < TimePeriod > periods = chosen_loc.getIntevervals(res);
	    
        if (periods.size() == 0) {
            System.out.println("No periods are available");
            return null;
        }
	    
        Clock start = verifyclockinput(periods, in);
        Clock end = null;
	    end = start.clone();
        end.tickMinutes(res.getTotalEatingTime());
        Clock[] clocks = {start, end};

        return clocks;
    }


    public static Location selectLocation(Scanner in) {
    	 ArrayList < Location > locations = Restaurant.getLocations();

         for (int i = 0; i < locations.size(); i++)
             System.out.println(i + 1 + ". " + locations.get(i).getName());

         Location chosen_loc = null;
         
         while(true) {
             try {
                 System.out.println("Enter the location number: ");
                 chosen_loc = locations.get( in .nextInt() - 1);
                 return chosen_loc;
             } catch (IndexOutOfBoundsException e) {
                 System.out.println("Error!: Entered an invalid number!, please enter a number in range 1-" + locations.size());
             } catch (InputMismatchException e) {
                 System.out.println("Error!: Didn't enter a number!, please enter a number!"); 
                 in .nextLine();
             } 
             
             for (int i = 0; i < locations.size(); i++)
                 System.out.println(i + 1 + ". " + locations.get(i).getName());
         } 
    }

    public static boolean createPayment(Scanner in , Info info,Reservation res) {
    	Bill bill = new Bill(info);
        bill.showBill();
        Payment pay = null;
        
        System.out.println("\nProceeding to payment....");
        slowdown();
        pay = new Payment(bill);
        System.out.println("\nChecking if payment was sucessful....");
        slowdown(); 
         
        boolean worked = false;
        
        while (true) {
            if (pay.makePayment()) {
                bill.setBookingID();
                System.out.println("\nAdding booking to list\nYour booking ID is " + bill.getBookingID() + "\n");
                res.setBill(bill);
                worked = true;
                break;
            } 
            else {
                boolean retry = true;
                boolean end = true;
                
                while (end) {
                    System.out.println("\nTry again? Type Yes or No");
                    String input = in.next();
                    
                    switch (input) {
                        case "yes":
                        case "YES":
                        case "y":
                        case "Y":
                        case "Yes":
                        {
							retry = true;
							end = false;
							break;
                        }
                       default:
                       {
                    	   retry = false;
                           end = false;
                           break;
                       }
                    }
                }

                if (!retry)
                    return false;

                System.out.println("Retrying payment....");
            }
        }
        return worked;
    }
    
    public static Clock verifyclockinput(ArrayList<TimePeriod> periods, Scanner in) {	
    	Clock tmp = null;
    
    	while(true) {
        	try {
				for (int c = 0; c < periods.size(); c++)
					System.out.println(periods.get(c).toString());

				System.out.println("\nSelect any time from these intervals");
				String time = in.nextLine();

				tmp = Clock.createClockWithTime(time.trim());
				for(TimePeriod c: periods) {
					if(c.contains(tmp)) {
						return tmp;
					}
				}
				
				System.out.println("Error! The inputted time was not in the given range! Please input a time in the ranges!");
        	}
        	catch(ExIncorrectClockFormat | ExInvalidClockRange e) {
        		System.out.println(e.getMessage());
        	}
    	}
    }
    
    public static void slowdown() {
    	try {
            Thread.sleep((long) Math.floor(Math.random() * (1501) + 1500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

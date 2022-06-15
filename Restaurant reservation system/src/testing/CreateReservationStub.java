package testing;

import java.util.ArrayList;
import java.util.Scanner;

import logistics.Clock;
import logistics.Location;
import logistics.Reservation;
import logistics.TimePeriod;
import payment.Bill;
import payment.Info;
import payment.Payment;
import restaurantFood.Food;
import userInput.CreateReservation;

public class CreateReservationStub extends CreateReservation {
	
	
	
	
	
	private static Boolean suceed;
	private static int retries = 0;
	public static void setSucess(boolean suceed) {
		CreateReservationStub.suceed=suceed;
	}
	public static void setretries(int retries) {
		CreateReservationStub.retries=retries;
	}
	
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
	
	  
	 
	  
	  
	  
	  
	  
	  
	  
	public static boolean createPayment(Scanner in , Info info,Reservation res) {
    	Bill bill = new Bill(info);
        bill.showBill();
        Payment pay = null;
        
        System.out.println("\nProceeding to payment....");
        slowdown();
        pay = new PaymentStub(bill, suceed, retries);
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
                	retries--;
            	
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
	
	
	 public static void slowdown() {
	    	try {
	            Thread.sleep(1);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }

}


class PaymentStub extends Payment{
	private boolean sucess;
	private int retry;
	
	public PaymentStub(Bill bill, Boolean sucess, int retry) {
		super(bill);
		this.sucess = sucess;
		this.retry = retry;
	
	}

	@Override
	public boolean getSuccess() {
		if(sucess || (!sucess && retry==0))
		return true;
		else
		{
			retry--;
			return false;
		}
		
	}
	

}

package payment;


import restaurantFood.Food;


public class Bill {
	private int bookingID;
	private Info info;
	private static int nextbookingID = 1;
	
	
	public Bill(Info info) {
	this.info = info;
	
	}
	
	public void setBookingID() {
	
			bookingID = nextbookingID;
			nextbookingID++;
	
	}
	
	public int calcBill() {
		int total = 0;
		for(Food d: info.getDishes())
			total += d.getPrice();
		return total;
	}
	
	public void showBill() {
	
		System.out.println("Booking is for "+info.getName());
		System.out.println("Booking is for "+info.getNumGuests()+" guests");
		System.out.println("Your booking time is: "+ info.getstart()+" - "+info.getend());
		for(Food d: info.getDishes())
			System.out.printf("%s - %d HKD\n", d.getName(), d.getPrice());
		
		int tot = calcBill();
		System.out.printf("Total amount: %s HKD\n", tot);
	}

	public String getBookingID() 
	{ 
		
			return String.format("%03d", bookingID);
	}

	public int getBookingIDNum() {
		return bookingID;
	}
	
	public static void resetBookingID() {
		nextbookingID=1;
	}
}

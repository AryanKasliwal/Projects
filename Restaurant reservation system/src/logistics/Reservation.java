package logistics;
import payment.Bill;
import payment.Info;
import restaurantFood.Food;

public class Reservation {
	
	private Info info;
	private Bill bill;

	
	public Reservation(Info info) {
		this.info = info;
	}
	
	public int getTotalEatingTime() {
		return Food.getTotalTime(this.info.getDishes());
	}

	public int getNumGuests() {
		return this.info.getNumGuests();
	}
	


	public String getBookingID() { 
		return this.bill.getBookingID(); 
	}
	public int getCost() {
		return bill.calcBill();
	}
	public void setBill(Bill b)
	{
		bill = b;
	}
	public TimePeriod getTimePeriod() {
		return this.info.getTimePeriod();
	}
	public int getBookingNum() {
		return bill.getBookingIDNum();
	}
	public void showBill() {
		bill.showBill();
	}
}

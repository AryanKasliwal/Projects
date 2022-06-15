package payment;

import java.util.*;

public class Payment  {

	private Bill bill;
	
	public Payment(Bill bill) { this.bill = bill; }
	
	public boolean getSuccess()
	{
		Random rand = new Random();
		return rand.nextInt() % 2 == 0;
	}
	
	public boolean makePayment() {
	
			if(getSuccess()) {
				System.out.println("Payment Sucess");
				return true;
			}
			else {
				System.out.println("Payment failed, please retry"); 
				return false;
			}	
		
	}
	
	public void showBill() { bill.showBill(); }
}

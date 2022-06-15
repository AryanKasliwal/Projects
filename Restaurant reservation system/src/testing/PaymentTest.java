package testing;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Exceptions.ExIncorrectClockFormat;
import Exceptions.ExInvalidClockRange;
import payment.Bill;
import payment.Payment;
import restaurantFood.Restaurant;

public class PaymentTest {

	
	private final static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final static ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final static PrintStream originalOut = System.out;
	private final static PrintStream originalErr = System.err;
	
	@BeforeAll
	public static void setUpStreams() throws ExIncorrectClockFormat, ExInvalidClockRange {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}
	 
	@AfterAll
	public static void resetAll() {    
		System.setOut(originalOut);
	    System.setErr(originalErr);
	}
	
	@AfterEach
	public void reset() {
		Bill.resetBookingID();
		outContent.reset();
	}
	
	// Test no: T39
	@Test
	public void PaymentSuccess()
	{
		Bill bill = null;
		
		class Paymentstub extends Payment
		{

			public Paymentstub(Bill bill) {
				super(bill);
			}
			
			@Override
			public boolean getSuccess()
			{
				return true;
			}
		}
		Paymentstub pay = new Paymentstub(bill);
		
		pay.makePayment();
	
		assertEquals(outContent.toString(), "Payment Sucess\r\n");
		
		
	}
	
	// Test no: T40
	@Test
	public void PaymentFail()
	{
		Bill bill = null;
		
		class Paymentstub extends Payment
		{

			public Paymentstub(Bill bill) {
				super(bill);
			
			}
			
			@Override
			public boolean getSuccess()
			{
				return false;
			}
		}
		Paymentstub pay = new Paymentstub(bill);
		
		 pay.makePayment();
		assertEquals(outContent.toString(), "Payment failed, please retry\r\n");
	}
	
}

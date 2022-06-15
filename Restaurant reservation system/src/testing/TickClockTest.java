package testing;

import static org.junit.Assert.assertEquals;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Exceptions.ExInvalidClockRange;
import logistics.Clock;
import userInput.TickClock;

public class TickClockTest {

	private final static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final static ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final static PrintStream originalOut = System.out;
	private final static PrintStream originalErr = System.err;


	@BeforeAll
	public static void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@AfterAll
	public static void restoreStreams() throws ExInvalidClockRange {
	    System.setOut(originalOut);
	    System.setErr(originalErr);
	    Clock.SetClock(Clock.getClock(), 0, 11);
	    
	}
	
	@AfterEach
	public void reset() {
		outContent.reset();
	}
	
	// Test no: T57
	@Test
	public void negInput() throws IOException
	{
		
		InputStream targetStream = new ByteArrayInputStream("-10".getBytes(Charset.forName("UTF-8")));
		
		

		Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
		
		Thread r = new ScannerTestThread(sc);

		r.start();
		
		try {
			TickClock.tick(sc);
		}
		catch(Exception e)
		{
			
		}
		final String standardOutput =  outContent.toString();
		
		String text = "Current time is: 11:10\r\n"
				+ "How many minutes should the clock be ticked forward? Error! Please input a postiive number!\r\n";
		assertEquals(text,standardOutput);
	}

	// Test no: T58
	@Test
	public void wrongInput() throws IOException{
		InputStream targetStream = new ByteArrayInputStream("abc".getBytes(Charset.forName("UTF-8")));
		Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
		Thread r = new ScannerTestThread(sc);
		r.start();
		try {
		TickClock.tick(sc);
		}
		catch(Exception e){
			
		}
		final String standardOutput =  outContent.toString();
		String text = "Current time is: 11:10\r\n"
				+ "How many minutes should the clock be ticked forward? Error! Not a number!";
		assertEquals(standardOutput.toString().contains(text), true);
	}
	
	//Test no: T59
	@Test
	public void correctInput() throws IOException{
		InputStream targetStream = new ByteArrayInputStream("10".getBytes(Charset.forName("UTF-8")));
		Scanner sc = new Scanner(new InputStreamReader(targetStream)); 
		TickClock.tick(sc);
		final String standardOutput =  outContent.toString();
		String text = "Current time is: 11:00\r\n"
				+ "How many minutes should the clock be ticked forward? New Time is now 11:10!\n"
				+ "\r\n"
				+ "";
		assertEquals(text, standardOutput);
	}
}
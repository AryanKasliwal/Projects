package testing;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Exceptions.ExIncorrectClockFormat;
import Exceptions.ExInvalidClockRange;
import logistics.Clock;


public class ClockTest {


	Clock test;

	@BeforeEach
	public void setUp() throws Exception { 
		
		test =   Clock.createClockWithTime("11:00");
		
		
	}

	// Test no: T19
	@Test
	public void testCreateClockInt() throws ExInvalidClockRange {
		Clock compare = Clock.createClockWithTime(11,0);
		boolean result = compare.toString().equals("11:00");
		assertEquals(true, result);
		
	}
	
	// Test no: T20
	@Test
	public void testCreateClockString() throws ExIncorrectClockFormat, ExInvalidClockRange {
		Clock compare = Clock.createClockWithTime("13:00");
		boolean result = compare.toString().equals("13:00");
		assertEquals(true, result);
		
	}
	
	// Test no: T21
	@Test
	public void testClockDisplayLessThan10() {
		
		boolean result = test.toString().equals("11:00");
		assertEquals(true, result);
	}
	
	// Test no: T22
	@Test
	public void testClockDisplayMoreThan10() throws ExIncorrectClockFormat, ExInvalidClockRange {
		test = Clock.createClockWithTime("11:10");
		boolean result = test.toString().equals("11:10");
		assertEquals(true, result);
	}
	
	// Test no: T23
	@Test
	public void testCompareToSame() throws ExIncorrectClockFormat, ExInvalidClockRange {
		Clock compare = Clock.createClockWithTime("11:00");
		assertEquals(0, test.compareTo(compare));
	}
	
	// Test no: T24
	@Test
	public void testCompareToLess() throws ExIncorrectClockFormat, ExInvalidClockRange {
		Clock compare = Clock.createClockWithTime("12:00");
		assertEquals(-60, test.compareTo(compare));
	}
	
	// Test no: T25
	@Test
	public void testCompareToMore() throws ExIncorrectClockFormat, ExInvalidClockRange {
		Clock compare = Clock.createClockWithTime("10:00");
		assertEquals(60, test.compareTo(compare));
	}
	
	// Test no: T26
	@Test
	public void testEquals() throws ExIncorrectClockFormat, ExInvalidClockRange {
		Clock compare = Clock.createClockWithTime("10:00");
		assertEquals(false, test.equals(compare));
		
	}
	
	// Test no: T27
	@Test
	public void testNotEquals() throws ExIncorrectClockFormat, ExInvalidClockRange {
		Clock compare = Clock.createClockWithTime("11:00");
		assertEquals(true, test.equals(compare));
		
	}
	
	// Test no: T28
	@Test
	public void testClone() throws ExIncorrectClockFormat, ExInvalidClockRange, CloneNotSupportedException {
		Clock tmp = Clock.createClockWithTime("12:00");
		Clock cloneClock = tmp.clone();
		assertEquals(true, cloneClock.equals(tmp));
		
	}
	
	// Test no: T29
	@Test
	public void testIteratorhasNext() throws ExIncorrectClockFormat, ExInvalidClockRange, CloneNotSupportedException {
		Clock.Iterator it = new Clock.Iterator(Clock.createClockWithTime("12:00"), Clock.createClockWithTime("12:10"));
		
		assertEquals(it.hasNext(), false);
		
	}
	
	// Test no: T30
	@Test
	public void testIteratorhasNext_2() throws ExIncorrectClockFormat, ExInvalidClockRange, CloneNotSupportedException {
		Clock.Iterator it = new Clock.Iterator(Clock.createClockWithTime("12:00"), Clock.createClockWithTime("12:30"));
		assertEquals(it.hasNext(), true);
		
	}
	
	// Test no: T31
	@Test
	public void testIteratorNext() throws ExIncorrectClockFormat, ExInvalidClockRange, CloneNotSupportedException {
		Clock.Iterator it = new Clock.Iterator(Clock.createClockWithTime("12:00"), Clock.createClockWithTime("13:30"));
		
		Clock next =  it.next();
		assertEquals(next.equals(Clock.createClockWithTime("12:00")),true);
		
	}
	
	// Test no: T32
	@Test
	public void testIteratorNext_2() throws ExIncorrectClockFormat, ExInvalidClockRange, CloneNotSupportedException {
		Clock.Iterator it = new Clock.Iterator(Clock.createClockWithTime("12:00"), Clock.createClockWithTime("13:30"));
		it.next();
		it.next();
		Clock next =  it.next();
		assertEquals(next.equals(Clock.createClockWithTime("13:00")),true);
	}
	
	// Test no: T33
	@Test
	public void testIterator() throws ExIncorrectClockFormat, ExInvalidClockRange {
					
	    Clock.Iterator it = new Clock.Iterator(Clock.createClockWithTime("12:00"));
	    	it.next();
	    assertEquals(it.next().equals(Clock.createClockWithTime("11:30")),true);
	}
	
	// Test no: T34
	@Test
	public void testIterator_2() throws ExIncorrectClockFormat, ExInvalidClockRange {
		
	    Clock.Iterator it = new Clock.Iterator(Clock.createClockWithTime("11:00"), Clock.createClockWithTime("12:00"),60);
	    	it.next();
	    assertEquals(it.next().equals(Clock.createClockWithTime("12:00")),true);
	}
	
	// Test no: T35
	@Test
	public void ExceptionTest() throws ExInvalidClockRange {
	    String testex ="";
		try {
		Clock.createClockWithTime("1200");
		}
		catch(ExIncorrectClockFormat e)
		{
			testex = e.getMessage();
		}

	    assertEquals(testex.equals("Error!: Incorrect input format for the clock, it should be hours:mins not 1200"),true);
	}
	
	// Test no: T36
	@Test
	public void ExceptionTest_2() throws ExInvalidClockRange {
	    String testex ="";
		try {
		Clock.createClockWithTime("abcd");
		}
		catch(ExIncorrectClockFormat e)
		{
			testex = e.getMessage();
		}

	    assertEquals(testex.equals("Error!: Incorrect input format for the clock, it should be hours:mins not abcd"),true);
	}
	
	// Test no: T37
	@Test
	public void ExceptionTest_3() throws ExIncorrectClockFormat {
	    String testex ="";
		try {
		Clock.createClockWithTime("25:00");
		}
		catch(ExInvalidClockRange e)
		{
			testex = e.getMessage();
		}
		
	    assertEquals(testex.equals("Error!: Time of 25:00 is not in range!"),true);
	}
	
	// Test no: T38
	@Test
	public void ExceptionTest_4() throws ExInvalidClockRange {
	    String testex ="";
		try {
		Clock.createClockWithTime("ab:cd");
		}
		catch(ExIncorrectClockFormat e)
		{
			testex = e.getMessage();
		}

	    assertEquals(testex.equals("Error!: Incorrect input format for the clock, it should be hours:mins not ab:cd"),true);
	}
}

	
	
	


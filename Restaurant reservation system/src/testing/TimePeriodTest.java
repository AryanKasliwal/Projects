package testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Exceptions.ExInvalidClockRange;

import static org.junit.Assert.assertEquals;

import logistics.Clock;
import logistics.TimePeriod;

public class TimePeriodTest {
	Clock startClock;
	Clock endClock;
	TimePeriod tp;
	
	@BeforeEach
	public void setUp() throws ExInvalidClockRange {
		startClock = Clock.createClockWithTime(11, 0);
		endClock = Clock.createClockWithTime(12, 0);
		tp = new TimePeriod(startClock, endClock);
	}
	
	// Test no: T10
	@Test
	public void testIntervalContains() throws ExInvalidClockRange {
		Clock testTime = Clock.createClockWithTime(11, 30);
		boolean result = tp.contains(testTime);
		assertEquals(result, true);
	}
	
	// Test no: T11
	@Test
	public void testIntervalNotContains() throws ExInvalidClockRange {
		Clock testTime = Clock.createClockWithTime(12, 30);
		boolean result = tp.contains(testTime);
		assertEquals(result, false);
	}
	
	// Test no: T12
	@Test
	public void testSameTimePeriodcompareTo() {
		TimePeriod testTP = new TimePeriod(startClock, endClock);
		int result = tp.compareTo(testTP);
		assertEquals(result, 0);
	}
	
	// Test no: T13
	@Test
	public void testDifferentTimePeriodCompareTo() throws ExInvalidClockRange {
		Clock tempStartClock = Clock.createClockWithTime(12, 0);
		Clock tempEndClock = Clock.createClockWithTime(13, 0);
		TimePeriod testTP = new TimePeriod(tempStartClock, tempEndClock);
		int result = tp.compareTo(testTP);
		assertEquals(result, -60);
	}
	
	// Test no: T14
	@Test
	public void testgetTotalMin() {
		int result = tp.getTotalMin();
		assertEquals(result, 60);
	}
	
	// Test no: T15
	@Test
	public void testEquals() throws ExInvalidClockRange {
		Clock tempStartClock = Clock.createClockWithTime(12, 0);
		Clock tempEndClock = Clock.createClockWithTime(13, 0);
		TimePeriod testTP = new TimePeriod(tempStartClock, tempEndClock);
		boolean result = tp.equals(testTP);
		assertEquals(result, false);
	}
	
	// Test no: T16
	@Test
	public void TestString() throws ExInvalidClockRange {
		Clock tempStartClock = Clock.createClockWithTime(12, 0);
		Clock tempEndClock = Clock.createClockWithTime(13, 0);
		TimePeriod testTP = new TimePeriod(tempStartClock, tempEndClock);
		TimePeriod newTP = new TimePeriod("12:00-13:00");
		assertEquals(newTP.equals(testTP), true);
	}
}

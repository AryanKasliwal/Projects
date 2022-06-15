package Exceptions;

public class ExInvalidClockRange extends Exception {
	public ExInvalidClockRange(int hours, String mins)
	{
		
		super("Error!: Time of "+hours+":"+mins+" is not in range!");
	}
}

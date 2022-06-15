package Exceptions;

public class ExIncorrectClockFormat extends Exception {
	
	public ExIncorrectClockFormat(String in)
	{
		super("Error!: Incorrect input format for the clock, it should be hours:mins not "+in);
	}

}

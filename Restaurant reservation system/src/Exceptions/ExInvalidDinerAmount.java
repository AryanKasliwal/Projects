package Exceptions;

public class ExInvalidDinerAmount extends Exception {
	private static final long serialVersionUID = 1L;
	public ExInvalidDinerAmount (int num, int max)
	{
	
		super("Error!: Number of diners, "+num+" is not a proper amount of diners!\nPlease enter a number from 1 - "+max);
		
		
		
	}

}

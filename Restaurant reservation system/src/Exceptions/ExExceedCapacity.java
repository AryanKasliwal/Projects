package Exceptions;

public class ExExceedCapacity extends Exception {
	private static final long serialVersionUID = 1L;
	public ExExceedCapacity(int num, int max)
	{
	
		super("Error!: Number of diners, "+num+" exceeds max capacity of "+max+"!");
		
		
		
	}

}

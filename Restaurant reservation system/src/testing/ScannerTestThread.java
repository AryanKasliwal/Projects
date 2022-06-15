package testing;

import java.util.Scanner;

public class ScannerTestThread extends Thread{
	Scanner s;

	public ScannerTestThread(Scanner r){
		s = r;
	}
	
	@Override 
	public void run() {
		try {
			sleep(300);	
		} 
		catch (InterruptedException e) {
				e.printStackTrace();
		}
		s.close();
	}
}
				

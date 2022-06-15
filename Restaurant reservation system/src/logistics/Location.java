package logistics;
import java.util.ArrayList;;

public class Location {
	
	private int maxCapacity;
	private ArrayList <Reservation> reservations = new ArrayList<>();
	private BranchTimeManager manager;
	private String name;
	
	
	public Location(String name, int maxCapacity) {
		this.name = name;
		this.maxCapacity = maxCapacity;
		manager = new BranchTimeManager(this);
	}
	
	public void addReservationLoc(Reservation res)
	{
		reservations.add(res);
	}
	public ArrayList<Reservation> getReservations(){
		return reservations;
	}
	public ArrayList<TimePeriod> getIntevervals(Reservation res)
	{
		return manager.getInterval(res);
	}
	
	public int calcCurrentCapacity(Clock clock) {
        int currentCapacity = 0;
        
			Clock temp = clock.clone();
			temp.tickMinutes(1);
			
			for (Reservation res : reservations) {
	            if (res.getTimePeriod().contains(temp)) { 
	                currentCapacity += res.getNumGuests();
	            } 
		} 
			
			return currentCapacity;
        
    }
	

	public String getName() { return this.name; }
	
	
	public int getMaxCapcacity() { return maxCapacity;}

	public void removeReservation(Reservation r) {
		reservations.remove(r);
		
	}
}

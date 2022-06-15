package logistics;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import Exceptions.ExIncorrectClockFormat;
import Exceptions.ExInvalidClockRange;

public class BranchTimeManager {

    private Location location;

    public BranchTimeManager(Location location){
        this.location = location;
    }

    
    public ArrayList<TimePeriod> getInterval( Reservation reservation) {
       
        Clock nowtime = null;
       
			nowtime = Clock.getClock().clone();
	

        Clock.Iterator iterator = null;
		try {
			iterator = new Clock.Iterator(nowtime, Clock.createClockWithTime(23,0),1);
		} catch (ExInvalidClockRange e) {}

        LinkedHashMap<String, Integer> timings = new LinkedHashMap<>();

        while(iterator.hasNext()) {
            Clock temp = iterator.next();
            timings.put(temp.toString(), this.location.calcCurrentCapacity(temp));
        }

        Clock startClock = null;
		ArrayList<TimePeriod> availableIntervals = new ArrayList<>();
		int eatingTime = reservation.getTotalEatingTime();
		
		if (eatingTime > 720) {
			return new ArrayList<TimePeriod>();
		}

        try {
        	
        	String lastChecked = null;
        	boolean addNow = false;
        	int continues = eatingTime;
        	
	        for (Map.Entry<String, Integer> set: timings.entrySet()){
	        	
	        	if (continues != eatingTime) {
					continues++;
					continue;
				}

				Clock futureClock = Clock.createClockWithTime(set.getKey());
				futureClock.tickMinutes(eatingTime);
				
				if (futureClock.compareTo(Clock.createClockWithTime("23:00")) >= 0) {
					break;
				}

				continues = timings.get(futureClock.toString()) + reservation.getNumGuests() > this.location.getMaxCapcacity() ? 0: eatingTime;
				addNow = false;
				if (continues == 0 && startClock != null)
					addNow = true;
				else
					lastChecked = set.getKey();
				
	            if (addNow){
					availableIntervals.add(new TimePeriod(startClock, Clock.createClockWithTime(set.getKey())));
	                startClock = null;
	                continue;
	            }

	            if (startClock == null && set.getValue() + reservation.getNumGuests() <= this.location.getMaxCapcacity())
					startClock = Clock.createClockWithTime(set.getKey());
	        }
	        
	        if (startClock != null && timings.get(lastChecked) + reservation.getNumGuests() <= this.location.getMaxCapcacity())
				availableIntervals.add(new TimePeriod(startClock, Clock.createClockWithTime(lastChecked)));
        }
        catch (ExIncorrectClockFormat | ExInvalidClockRange e) {
        	System.out.println(e.getMessage());
        }


    	return availableIntervals;
    }
}

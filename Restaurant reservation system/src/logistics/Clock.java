package logistics;

import Exceptions.ExIncorrectClockFormat;
import Exceptions.ExInvalidClockRange;

public class Clock implements Comparable<Clock>, Cloneable{
    private int hours;
    private int minutes;

    private final static Clock clock = new Clock();
    
    private Clock(){
        this.hours = 11;
        this.minutes = 0;
    }
    public static void SetClock(Clock set, int mins, int hrs) throws ExInvalidClockRange
    {
    	if(hrs >24 || hrs<0 || mins <0 || mins>59)
    	{	String minstxt = mins+"";
    		if(mins<10)
    			minstxt="0"+mins;
    		throw new ExInvalidClockRange(hrs, minstxt);
        	
    	}
    	set.hours = hrs;
    	set.minutes = mins;
    		
    }

    private Clock(int hours, int minutes) throws ExInvalidClockRange  {
    
    	if(hours >24 || hours<0 || minutes <0 || minutes>59)
    	{	String minstxt = minutes+"";
    		if(minutes<10)
    			minstxt="0"+minutes;
    		throw new ExInvalidClockRange(hours, minstxt);
        	
    	}
    		
        this.hours = hours;
        this.minutes = minutes;
    }

    public static Clock getClock(){
        return Clock.clock;
    }

    public void tickHours (int hours){
        this.hours+=hours;
        this.hours%=24;
    }

    public void tickMinutes(int minutes){
        this.minutes += minutes; 
        this.tickHours(Math.floorDiv(this.minutes, 60));
        this.minutes = this.minutes% 60;
       
    }
    
    public static Clock createClockWithTime(int hours, int minutes) throws ExInvalidClockRange {
    	return new Clock(hours, minutes); 
    }

    public static Clock createClockWithTime(String clockString) throws ExIncorrectClockFormat, ExInvalidClockRange {
    	try {
            String[] splitString = clockString.split(":");
            if(splitString.length<2)
                throw new ExIncorrectClockFormat(clockString);

            return new Clock(Integer.parseInt(splitString[0]), Integer.parseInt(splitString[1]));
        
    	} catch(NumberFormatException e) {
    		throw new ExIncorrectClockFormat(clockString);
    	}
    }

    public Clock clone() {
    	try {
            return (Clock) super.clone();
    	}
    	catch(CloneNotSupportedException e){
    		return null;
    	}
    }

    @Override
    public int compareTo(Clock otherClock) {
        return ((this.hours == 0 ? 24: this.hours) - otherClock.hours)*60 + (this.minutes - otherClock.minutes);
    }

    public boolean equals(Clock otherClock){
        return (this.hours == otherClock.hours) && (this.minutes == otherClock.minutes);
    }

    @Override
    public String toString(){
    	if(this.minutes>=10)
        return String.format("%d:%d", this.hours, this.minutes);
    	else
    		return this.hours+":0"+this.minutes;
    }

    public static class Iterator {

	        private Clock currTime;
	        private final Clock endTime;
	        private final int tick;
	
	        public Iterator(Clock startTime, Clock endTime, int tickMinutes){
	            this.currTime = startTime;
	            this.endTime = endTime;
	            this.tick = tickMinutes;
	        }
	
	        public Iterator(Clock startTime, Clock endTime){
	            this.currTime = startTime;
	            this.endTime = endTime;
	            this.tick = 30;
	        }
	
	        public Iterator(Clock endTime){
	             this.currTime = Clock.getClock().clone(); 
	           
	
	            this.endTime = endTime;
	            this.tick = 30;
	        }
	
	        public boolean hasNext(){
	            Clock temp = null;
	            
	           temp = this.currTime.clone(); 
	         
	
	            assert temp != null;
	            temp.tickMinutes(this.tick);
	
	            return this.endTime.compareTo(temp) >= 0;
	        }
	
	        public Clock next(){
	            Clock toReturn=null;
			
					toReturn = this.currTime.clone();
					this.currTime.tickMinutes(this.tick);
	            return toReturn;
	    }
    }
}

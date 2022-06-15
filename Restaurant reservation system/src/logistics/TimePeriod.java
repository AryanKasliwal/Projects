package logistics;

import Exceptions.ExIncorrectClockFormat;
import Exceptions.ExInvalidClockRange;

public class TimePeriod implements Comparable<TimePeriod> {
    private Clock start;
    private Clock end;

    public TimePeriod(Clock start, Clock end) {
        this.start = start;
        this.end = end;
    }
    
    public Clock getStart() { return start;}
    
    public Clock getEnd() { return end;}
    public TimePeriod(String time)
    {
    	String[] split = time.split("-");
    	try {
			start = Clock.createClockWithTime(split[0]);
		  	end =Clock.createClockWithTime(split[1]);
		} catch (ExIncorrectClockFormat | ExInvalidClockRange e) {
			System.out.println(e.getMessage());
		}
    }

    public boolean contains(Clock clock){
        return this.start.compareTo(clock) <=0 && this.end.compareTo(clock) >=0;
    }


    @Override
    public int compareTo(TimePeriod timePeriod) {
        int res = this.start.compareTo(timePeriod.start);
        if (res == 0)
            return this.end.compareTo(timePeriod.end);
        else return res;
    }
    
    public int getTotalMin()
    {
    	return end.compareTo(start); 
    }
    
    public boolean equals(TimePeriod timePeriod){
        return this.start.equals(timePeriod.start) && this.end.equals(timePeriod.end);
    }

    @Override
    public String toString(){
        return String.format("%s-%s", this.start, this.end);
    }


}

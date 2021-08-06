import java.time.Month;

public class Day implements Cloneable{

    private int year;
    private int month;
    private int day;
    private static final String MonthNames="JanFebMarAprMayJunJulAugSepOctNovDec";

    //Constructor
    public Day(int y, int m, int d) {
        this.year=y;
        this.month=m;
        this.day=d;
    }

    public void set(String sDay) //Set year,month,day based on a string like 01-Jan-2020
    {
        String[] sDayParts = sDay.split("-");
        this.day = Integer.parseInt(sDayParts[0]);
        this.month = MonthNames.indexOf(sDayParts[1])/3+1;
        this.year = Integer.parseInt(sDayParts[2]);
    }

    public Day(String sDay){
        set(sDay);
    }

    // check if a given year is a leap year
    static public boolean isLeapYear(int y)
    {
        if (y%400==0)
            return true;
        else if (y%100==0)
            return false;
        else if (y%4==0)
            return true;
        else
            return false;
    }

    // check if y,m,d valid
    static public boolean valid(int y, int m, int d)
    {
        if (m<1 || m>12 || d<1) return false;
        switch(m){
            case 1: case 3: case 5: case 7:
            case 8: case 10: case 12:
                return d<=31;
            case 4: case 6: case 9: case 11:
                return d<=30;
            case 2:
                if (isLeapYear(y))
                    return d<=29;
                else
                    return d<=28;
        }
        return false;
    }

    // Return a string for the day like dd MMM yyyy
    public String toString() {
        return day + "-" + MonthNames.substring((month-1)*3,(month)*3) + "-" + year;
        /*
        final String[] MonthNames = {
                "Jan", "Feb", "Mar", "Apr",
                "May", "Jun", "Jul", "Aug",
                "Sep", "Oct", "Nov", "Dec"};

        return day+" "+ MonthNames[month-1] + " "+ year;
         */
    }

    @Override
    public Day clone() {
        Day copy = null;
        try {
            copy = (Day) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return copy;
    }
}

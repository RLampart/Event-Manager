import java.time.LocalDate;
import java.util.Calendar;

public class Event implements Comparable<Event> {
    private int expGuests;
    private String time;
    private String date;
    private String name;
    private String venue;

    public Event(String nname, String ddate, String ttime, String vvenue, int guests) {
        name = nname;
        time = ttime;
        date = ddate;
        expGuests = guests;
        venue = vvenue;
    }

    public String timeToString() {
        return time;
    }

    public String datetoString() {
        return date;
    }

    public int get_Expected_Guests() {
        return expGuests;
    }

    public String getName() {
        return name;
    }

    public String getVenue() {
        return venue;
    }

    public int getHour() {
        String[] times = time.split(":");
        return Integer.parseInt(times[0]);
    }

    public int getMinute() {
        String[] times = time.split(":");
        return Integer.parseInt(times[1]);
    }

    public int getDay() {
        String[] dates = date.split("/");
        return Integer.parseInt(dates[0]);
    }

    public int getMonth() {
        String[] dates = date.split("/");
        return Integer.parseInt(dates[1]);
    }

    public int getYear() {
        String[] dates = date.split("/");
        return Integer.parseInt(dates[2]);
    }

    public String toString() {
        return name + ">" + date + ">" + time +
                ">" + venue + ">" + expGuests;
    }

    public void changeName(String nName) {
        name = nName;
    }

    public void changeDate(String nDate) {
        date = nDate;
    }

    public void changeVenue(String nVenue) {
        venue = nVenue;
    }

    public void changeExpectedGuests(int nExpectedGuests) {
        expGuests = nExpectedGuests;
    }

    public void changeTime(String nTime) {
        time = nTime;
    }

    public boolean isValid() {
        LocalDate n_cal = LocalDate.now();
        boolean valid = false;
        if (getYear() > n_cal.getYear())
            valid = true;
        else if (getYear() == n_cal.getYear() && getMonth() > n_cal.getMonthValue())
            valid = true;
        else if (getYear() == n_cal.getYear() && getMonth() == n_cal.getMonthValue()
                && getDay() >= n_cal.getDayOfMonth())
            valid = true;
        return valid;
    }

    public int compareTo(Event that) {
        Calendar n_cal = Calendar.getInstance();
        Calendar t_cal = Calendar.getInstance();
        n_cal.set(getYear(), getMonth(), getDay(), getHour(), getMinute());
        t_cal.set(that.getYear(), that.getMonth(), that.getDay(), that.getHour(), that.getMinute());
        return n_cal.compareTo(t_cal);
    }

}
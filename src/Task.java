import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

public class Task {
    protected String name;
    protected String type;
    protected int date;
    protected double startTime;
    protected double duration;

    public Task(String name, String type, int date, double startTime, double duration) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.startTime = startTime;
        this.duration = duration;
    }

    /**
     * Returns true or false depending on whether the task is within a certain time period.
     * @param startDate - the date a task will start
     * @param endDate - the date a task will end
     * @return - whether or not the task is between the start and end date
     */
    public boolean withinTimePeriod(int startDate, int endDate) {
        if(startDate <= date && endDate >= date) {
            return true;
        }
        return false;
    }
    
    /**
     * Tests if the proposed start time and duration for a yet to be Task would
     * cause the Task to overlap with this Task.
     * @param inDate The proposed date of the Task.
     * @param inStartTime The proposed start time of the Task being tested.
     * @param inDuration The proposed duration of the Task being tested.
     * @return True if the proposed parameters cause an overlap, false otherwise.
     */
    public boolean isOverlapping(int inDate, double inStartTime, double inDuration) {
    	LocalDateTime thisStartTime, thisEndTime, providedStartTime, providedEndTime;
    	thisStartTime = this.getStartDateTime();
    	thisEndTime = this.getEndDateTime();
    	Task testTask = new Task("test", "Class", inDate, inStartTime, inDuration);
    	providedStartTime = testTask.getStartDateTime();
    	providedEndTime = testTask.getEndDateTime();
    	
    	// test if provided start date-time is after this start date-time, but before this end date-time
    	if( providedStartTime.isAfter(thisStartTime) && providedStartTime.isBefore(thisEndTime) )
    		return true;
    	// test if provided end date-time is after this start date-time, but before this end date-time
    	if( providedEndTime.isAfter(thisStartTime) && providedEndTime.isBefore(thisEndTime))
    		return true;
    	
    	// test if provided start date-time is after this start date-time, but before this end date-time
    	if( thisStartTime.isAfter(providedStartTime) && thisStartTime.isBefore(providedEndTime) )
    		return true;
    	// test if provided end date-time is after this start date-time, but before this end date-time
    	if( thisEndTime.isAfter(providedStartTime) && thisEndTime.isBefore(providedEndTime))
    		return true;
    	
    	return false;
    }
    
    /**
     * @return A LocalDateTime object representing the date and time the task starts.
     */
    public LocalDateTime getStartDateTime() {
    	LocalDate convertedDate = LocalDate.parse(Integer.toString(date), DateTimeFormatter.ofPattern("yyyyMMdd"));
    	LocalTime convertedTime = LocalTime.of( (int)startTime, (int)((startTime % 1.0) * 60.0));
    	return LocalDateTime.of(convertedDate, convertedTime);
    }
    
    /**
     * @return A LocalDateTime object representing the date and time the task ends.
     */
    public LocalDateTime getEndDateTime() {
    	return this.getStartDateTime().plus(Duration.ofHours((long)duration).plusMinutes((long)((duration % 1.0) * 60.0)));
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getDate() {
        return date;
    }

    public double getStartTime() {
        return startTime;
    }

    public double getDuration() {
        return duration;
    }
    
}


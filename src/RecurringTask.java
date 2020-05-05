import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RecurringTask extends Task {
    private int endDate;
    private int frequency;
    private Scheduler scheduler;

    public RecurringTask(String name, String type, int date, double startTime, double duration, int endDate, int frequency) {
        super(name, type, date, startTime, duration);
        this.endDate = endDate;
        this.frequency = frequency;
    }

    public void setScheduler(Scheduler inScheduler) {
    	this.scheduler = inScheduler;
    }
    
    /**
     * Returns each occurrence of the recurring task. 
     * 
     * @return A List containing each occurrence of the recurring task.
     */
    public List<Task> getEffectiveTasks() {
        List<Task> effectiveTasks = new ArrayList<Task>();

        DateTimeFormatter dataFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate currentDate = LocalDate.parse(Integer.toString(date), DateTimeFormatter.ofPattern("yyyyMMdd"));        
       	LocalDate localEndDate = LocalDate.parse(Integer.toString(endDate), DateTimeFormatter.ofPattern("yyyyMMdd"));
    	localEndDate = localEndDate.plusDays(1);

        while(currentDate.isBefore(localEndDate)) {
        	int effectiveDate = Integer.parseInt(dataFormat.format(currentDate));
        	effectiveTasks.add(new Task(name, type, effectiveDate, startTime, duration));
        	if(frequency == 1) {
        		currentDate = currentDate.plusDays(1);
        	}
        	else if(frequency == 7) {
        		currentDate = currentDate.plusWeeks(1);
        	}
        	else {
        		currentDate = currentDate.plusMonths(1);
        	}
        }
        
        if(scheduler == null || scheduler.getAntiTasks() == null)
        	return effectiveTasks;
        
        // removing effective task instances which are cancelled by anti-tasks
        for(Task antiTask : scheduler.getAntiTasks()) {
        	if(antiTask.startTime == this.startTime && antiTask.duration == this.duration) {
        		for(int i = 0; i < effectiveTasks.size(); i++) {
        			if(effectiveTasks.get(i).getDate() == antiTask.getDate()) {
        				effectiveTasks.remove(i);
        				i--;
        			}
        		}
        	}
        }
        
        return effectiveTasks;
    }

    /**
     * Returns each occurrence of the recurring task within the given time period.
     * 
     * @param startDate Start date of time period.
     * @param endDate End date of time period.
     * @return A list containing each occurrence of the recurring task within the specified time period.
     */
    public List<Task> getEffectiveTasks(int startDate, int endDate) {
    	List<Task> effectiveTasks = this.getEffectiveTasks();
    	for(int i = 0; i < effectiveTasks.size(); i++) {
    		if(!effectiveTasks.get(i).withinTimePeriod(startDate, endDate)) {
    			effectiveTasks.remove(i);
    			i--;
    		}	
    	}
    	return effectiveTasks;
    }

    @Override
    public int getEndDate() {
        return endDate;
    }

    @Override
    public int getFrequency() {
        return frequency;
    }
    
    @Override
    public boolean withinTimePeriod(int startDate, int endDate) {
    	List<Task> effectiveTasks = getEffectiveTasks();
    	for(Task task : effectiveTasks) {
    		if(task.date >= startDate && task.date <= endDate) {
    			return true;
    		}
    	}

    	return false;
    }
    
    /**
     * Tests if the proposed start time and duration for a yet to be Task would
     * cause the Task to overlap with any occurrence of this recurring task.
     * 
     * @param inDate The proposed date of the Task being tested.
     * @param inStartTime The proposed start time of the Task being tested.
     * @param inDuration The proposed duration of the Task being tested.
     * @return True if the proposed parameters cause an overlap, false otherwise.
     */
    @Override
    public boolean isOverlapping(int inDate, double inStartTime, double inDuration) {
    	List<Task> effectiveTasks = this.getEffectiveTasks();
    	for(Task task : effectiveTasks) {
    		if(task.isOverlapping(inDate, inStartTime, inDuration))
    			return true;
    	}
    	return false;
    }
}

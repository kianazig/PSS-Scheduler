import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RecurringTask extends Task {
    private int endDate;
    private int frequency;

    public RecurringTask(String name, String type, int date, double startTime, double duration, int endDate, int frequency) {
        super(name, type, date, startTime, duration);
        this.endDate = endDate;
        this.frequency = frequency;
    }

    /**
     * Returns each occurrence of the recurring task. 
     * @return A List containing each occurrence of the recurring task.
     */
    public List<Task> getEffectiveTasks() {//NEEDS TESTING
        List<Task> effectiveTasks = new ArrayList<Task>();

        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMdd");
        Date formattedStartDate = null, formattedEndDate = null;
        Calendar calendar = null;
        try {
			formattedStartDate = dataFormat.parse(String.valueOf(date));//NEEDS TESTING
			formattedEndDate = dataFormat.parse(String.valueOf(endDate));
			calendar = new GregorianCalendar();
		} catch (ParseException e) {
			System.out.println("Date not parsed properly.");
			e.printStackTrace();
		}

        calendar.setTime(formattedStartDate);
        while(calendar.getTime().compareTo(formattedEndDate) <= 0) {
        	int effectiveDate = Integer.parseInt(dataFormat.format(calendar.getTime()));
        	effectiveTasks.add(new Task(name, type, effectiveDate, startTime, duration));
        	if(frequency == 1) {
        		calendar.roll(Calendar.DATE, true);
        	}
        	else if(frequency == 7) {
        		calendar.roll(Calendar.WEEK_OF_YEAR, true);
        	}
        	else {
        		calendar.roll(Calendar.MONTH, true);
        	}
        }
        
        return effectiveTasks;
    }

    /**
     * Returns each occurrence of the recurring task within the given time period.
     * @param startDate Start date of time period.
     * @param endDate End date of time period.
     * @return A list containing each occurrence of the recurring task within the specified time period.
     */
    public List<Task> getEffectiveTasks(int startDate, int endDate) {//NEEDS TESTING
        List<Task> effectiveTasks = new ArrayList<Task>();

        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMdd");
        Date formattedStartDate = null, formattedTaskEndDate = null, formattedTimePeriodEndDate = null;
        Calendar calendar = null;
        try {
			formattedStartDate = dataFormat.parse(String.valueOf(date));//NEEDS TESTING
			formattedTimePeriodEndDate = dataFormat.parse(String.valueOf(endDate));
			formattedTaskEndDate = dataFormat.parse(String.valueOf(this.endDate));
			calendar = new GregorianCalendar();
		} catch (ParseException e) {
			System.out.println("Date not parsed properly.");
			e.printStackTrace();
		}

        calendar.setTime(formattedStartDate);
        while(calendar.getTime().compareTo(formattedTaskEndDate) <= 0 && calendar.getTime().compareTo(formattedTimePeriodEndDate) <= 0) {
        	int effectiveDate = Integer.parseInt(dataFormat.format(calendar.getTime()));
        	effectiveTasks.add(new Task(name, type, effectiveDate, startTime, duration));
        	if(frequency == 1) {
        		calendar.roll(Calendar.DATE, true);
        	}
        	else if(frequency == 7) {
        		calendar.roll(Calendar.WEEK_OF_YEAR, true);
        	}
        	else {
        		calendar.roll(Calendar.MONTH, true);
        	}
        }
        
        return effectiveTasks;
    }

    public int getEndDate() {
        return endDate;
    }

    public int getFrequency() {
        return frequency;
    }
    
    @Override
    public boolean withinTimePeriod(int startDate, int endDate) {//NEEDS TESTING
    	List<Task> effectiveTasks = getEffectiveTasks();
    	for(Task task : effectiveTasks) {
    		if(task.date >= startDate && task.date <= endDate) {
    			return true;
    		}
    	}

    	return false;
    }
}

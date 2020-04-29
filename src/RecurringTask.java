import java.util.*;

public class RecurringTask extends Task {
    private int endDate;
    private int frequency;

    public RecurringTask(String name, String type, int date, double startTime, double duration, int endDate, int frequency) {
        super(name, type, date, startTime, duration);
        this.endDate = endDate;
        this.frequency = frequency;
    }

    public List<Task> getEffectiveTasks() {
        List<Task> effectiveTasks = new ArrayList<Task>();

        // TO BE IMPLEMENTED

        return effectiveTasks;
    }

    public List<Task> getEffectiveTasks(int startDate, int endDate) {
        List<Task> effectiveTasks = new ArrayList<Task>();

        // TO BE IMPLEMENTED

        return effectiveTasks;
    }

    public int getEndDate() {
        return endDate;
    }

    public int getFrequency() {
        return frequency;
    }
    
    @Override
    public boolean withinTimePeriod(int startDate, int endDate) {
        //TODO: Implement
    	return false;
    }
}

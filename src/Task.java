
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


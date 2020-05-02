import java.util.LinkedList;
import java.util.List;

public class Scheduler {

    private List<Task> listOfTasks;

    void addTask(Task newTask)
    {
        listOfTasks.add(newTask);
    }

    void deleteTask(Task newTask)
    {
        int counter = 0;
        while(counter < listOfTasks.size())
        {
            if(newTask.equals(listOfTasks.get(counter)))
            {
                listOfTasks.remove(listOfTasks.get(counter));
                return;
            }

            ++counter;
        }

        System.out.print("Task does not exist.");
        return;
    }

    Task getTask(String retrievalName)
    {
        int counter = 0;
        while(counter < listOfTasks.size())
        {
            if(retrievalName.equals(listOfTasks.get(counter).getName()))
            {
                return listOfTasks.get(counter);
            }

            ++counter;
        }

        System.out.print("Task name does not exist.");
        return null;
    }

    Task getTask(int retrievalDate)
    {
        int counter = 0;
        while(counter < listOfTasks.size())
        {
            if(retrievalDate==listOfTasks.get(counter).getDate())
            {
                return listOfTasks.get(counter);
            }

            ++counter;
        }

        System.out.print("Date does not exist.");
        return null;
    }
    
    /**
     * Returns the corresponding end date for a given start date and duration.
     * 
     * @param startDate The starting date. 
     * @param duration The duration of time to pass between start date and end date. (1=Day, 7=Week, 30=Month)
     * @return int representing end date.
     */
    public int getEndDate(int startDate, int duration) {
    	//TODO: Implement
    	return -1;
    }
    
    /**
     * Returns a list of tasks within a given time period.
     * 
     * @param startDate Starting date of time period (inclusive).
     * @param EndDate Ending date of time period (inclusive).
     * @return List of tasks within time period.
     */
    public List<Task> getTasksInTimePeriod(int startDate, int endDate){
    	//TODO: TEST
    	List<Task> tasksInTimePeriod = new LinkedList<>();
    	for(Task task : listOfTasks) {
    		if(task.withinTimePeriod(startDate, endDate)) {
    			if(task instanceof RecurringTask) {//If the task is a recurring task, we will individually add each instance within the time period.
    				tasksInTimePeriod.addAll(((RecurringTask)task).getEffectiveTasks(startDate, endDate));
    			}
    			else {
    				tasksInTimePeriod.add(task);
    			}		
    		}
    	}
    	return tasksInTimePeriod;
    }
    
    /**
     * Writes all tasks to given file.
     * 
     * @param fileName Name of file to write to.
     */
    public void writeToFile(String fileName) {
    	//TODO: Implement
    	
    }
    
    /**
     * Writes the given list of tasks to a file.
     * 
     * @param fileName Name of file to write to.
     * @param tasks Tasks to be written to file.
     */
    public void writeToFile(String fileName, List<Task> tasks) {
    	//TODO: Implement
    }
    
    /**
     * Checks if file already exists.
     * 
     * @param fileName Name of file to check.
     * @return True if exists, false otherwise.
     */
    public boolean writeFileExists(String fileName) {
    	//TODO: Implement
    	return false;
    }
    
    /**
     * Reads and stores tasks from file. 
     * 
     * @param fileName Name of file to read from.
     */
    public void readFromFile(String fileName) {
    	//TODO: Implement
    }
}

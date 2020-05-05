import java.io.*;
import java.util.*;
import java.text.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Scheduler {

  private List<Task> listOfTasks = new ArrayList<>();

  void addTask(Task newTask) {
	if(newTask instanceof RecurringTask)
		((RecurringTask)newTask).setScheduler(this);
    listOfTasks.add(newTask);
  }

  /**
   * Deletes a task.
   * @param taskToDelete Task to be deleted.
   * @return True if successfully deleted, false otherwise.
   */
  public boolean deleteTask(Task taskToDelete) {
    int counter = 0;
    while (counter < listOfTasks.size()) {
      if (taskToDelete.equals(listOfTasks.get(counter))) {
        listOfTasks.remove(listOfTasks.get(counter));
        return true;
      }

      ++counter;
    }

    return false;
  }

  Task getTask(String retrievalName) {
    int counter = 0;
    while (counter < listOfTasks.size()) {
      if (retrievalName.equals(listOfTasks.get(counter).getName())) {
        return listOfTasks.get(counter);
      }

      ++counter;
    }

    return null;
  }

  Task getTask(int retrievalDate) {
    int counter = 0;
    while (counter < listOfTasks.size()) {
      if (retrievalDate == listOfTasks.get(counter).getDate()) {
        return listOfTasks.get(counter);
      }

      ++counter;
    }

    System.out.print("Date does not exist.");
    return null;
  }

  /**
   * Tests if the provided name is a unique task name.
   * @param inName The name being tested.
   * @return True if the name does not belong to another task in the Scheduler already, false otherwise.
   */
  public boolean isNameUnique(String inName) {
	  for(Task task : listOfTasks) {
		  if(inName.equals(task.getName())) {
			  return false;
		  }
	  }
	  return true;
  }

  /**
   * Tests if the proposed start time and duration for a yet to be Task would
   * cause the Task to overlap with a Task already in the Scheduler.
   * @param inDate The proposed date of the Task.
   * @param inStartTime The proposed start time of the Task.
   * @param inDuration The proposed duration of the Task.
   * @return The task that the proposed parameters would conflict with, null if the
   * proposed parameters causes no conflicts.
   */
  public Task isOverlapping(int inDate, double inStartTime, double inDuration) {
	  for(Task task : listOfTasks) {
		  if(task.isOverlapping(inDate, inStartTime, inDuration)) {
			  return task;
		  }
	  }
	  return null;
  }
  
  /**
   * @return A list of AntiTasks in the Scheduler.
   */
  public List<Task> getAntiTasks() {
	  List<Task> antiTasks = new LinkedList<Task>();
	  for(Task task : listOfTasks) {
		  if(task.getType().equals("Cancellation")) {
			  antiTasks.add(task);
		  }
	  }
	  return antiTasks;
  }
  
  /**
   * Returns the corresponding end date for a given start date and duration.
   * 
   * @param startDate The starting date.
   * @param duration  The duration of time to pass between start date and end
   *                  date. (1=Day, 7=Week, 30=Month)
   * @return int representing end date.
   */
  public int getEndDate(int startDate, int duration) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    try {
      Date date = dateFormat.parse(String.valueOf(startDate));

      Calendar c = Calendar.getInstance();
      c.setTime(date);

      if (duration == 1) {
        c.add(Calendar.DATE, 1);
      } else if (duration == 7) {
        c.add(Calendar.WEEK_OF_MONTH, 1);
      } else if (duration == 30) {
        c.add(Calendar.MONTH, 1);
      }

      date = c.getTime();

      return Integer.parseInt(dateFormat.format(date));
    } catch (Exception e) {
      e.printStackTrace();
    }

    return -1;
  }

  /**
   * Returns a list of tasks within a given time period.
   * 
   * @param startDate Starting date of time period (inclusive).
   * @param EndDate   Ending date of time period (inclusive).
   * @return List of tasks within time period.
   */
  public List<Task> getTasksInTimePeriod(int startDate, int endDate) {
    // TODO: TEST
    List<Task> tasksInTimePeriod = new LinkedList<>();
    for (Task task : listOfTasks) {
      if (task.withinTimePeriod(startDate, endDate)) {
        if (task instanceof RecurringTask) {// If the task is a recurring task, we will individually add each instance
                                            // within the time period.
          tasksInTimePeriod.addAll(((RecurringTask) task).getEffectiveTasks(startDate, endDate));
        } else if(!task.getType().contentEquals("Cancellation")) {
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
    this.writeToFile(fileName, listOfTasks);
  }

  /**
   * Writes the given list of tasks to a file.
   * 
   * @param fileName Name of file to write to.
   * @param tasks    Tasks to be written to file.
   */
  public void writeToFile(String fileName, List<Task> tasks) {
    JSONArray list = new JSONArray();

    for (Task task : tasks) {
      JSONObject obj = new JSONObject();
      obj.put("Name", task.getName());
      obj.put("Type", task.getType());
      obj.put("StartTime", task.getStartTime());
      obj.put("Duration", task.getDuration());

      if (task instanceof RecurringTask) {
        obj.put("StartDate", task.getDate());
        obj.put("EndDate", ((RecurringTask) task).getEndDate());
        obj.put("Frequency", ((RecurringTask) task).getFrequency());
      } else {
        obj.put("Date", task.getDate());
      }

      list.add(obj);
    }

    try (FileWriter file = new FileWriter(fileName)) {
      file.write(list.toJSONString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Checks if file already exists.
   * 
   * @param fileName Name of file to check.
   * @return True if exists, false otherwise.
   */
  public boolean writeFileExists(String fileName) {
    File f = new File(fileName);

    return f.exists() && !f.isDirectory();
  }

  /**
   * Reads and stores tasks from file.
   * 
   * @param fileName Name of file to read from.
   */
  public void readFromFile(String fileName) {
    try (FileReader reader = new FileReader(fileName)) {
      Object obj = new JSONParser().parse(reader);
      JSONArray taskList = (JSONArray) obj;

      List<Task> tasksToAdd = new ArrayList<>();
      Boolean isOverlapping = false;

      for (Object task : taskList) {
        JSONObject taskObject = (JSONObject) task;

        String name = (String) taskObject.get("Name");
        String type = (String) taskObject.get("Type");
        double startTime = ((Number) taskObject.get("StartTime")).doubleValue();
        double duration = ((Number) taskObject.get("Duration")).doubleValue();

        if (taskObject.containsKey("EndDate")) {
          int startDate = ((Number) taskObject.get("StartDate")).intValue();
          int endDate = ((Number) taskObject.get("EndDate")).intValue();
          int frequency = ((Number) taskObject.get("Frequency")).intValue();

          if (!listOfTasks.isEmpty() && isOverlapping(startDate, startTime, duration, endDate, frequency) == null) {
            isOverlapping = true;
            break;
          }

          tasksToAdd.add(new RecurringTask(name, type, startDate, startTime, duration, endDate, frequency));
        } else {
          int date = ((Number) taskObject.get("Date")).intValue();

          if (!listOfTasks.isEmpty() && isOverlapping(date, startTime, duration) == null) {
            isOverlapping = true;
            break;
          }

          tasksToAdd.add(new Task(name, type, date, startTime, duration));
        }
      }

      if (isOverlapping) {
        System.out.println("Did not import data due to date conflicts!");
      } else {
        for (Task task : tasksToAdd) {
          addTask(task);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Checks if the type of the task is valid.
   * @param isTransientTask True if task is transient, false otherwise.
   * @param type The given type to check.
   * @return True if valid, false otherwise.
   */
  public boolean isValidTaskType(boolean isTransientTask, String type) {
	  if(isTransientTask) {
		  if (type.equals("Visit") || type.equals("Shopping") || type.equals("Appointment")) {
			  return true;
		  }
	  }
	  else {
		  if (type.equals("Class") || type.equals("Study") || type.equals("Sleep") || type.equals("Exercise")
				  || type.equals("Work") || type.equals("Meal")) {
			  return true;
		  }
	  }
	  return false;
  }

  /**
   * Checks if the recurring task with the given details conflicts with any existing task.
   * @param date Start date
   * @param startTime Start time
   * @param duration Duration
   * @param endDate End date
   * @param frequency Frequency
   * @return
   */
  public Task isOverlapping(int date, double startTime, double duration, int endDate, int frequency) {
	  RecurringTask newTask = new RecurringTask("TEST", "TEST", date, startTime, duration, endDate, frequency);
	  List<Task> effectiveTasks = newTask.getEffectiveTasks();
	  
	  for(Task newTaskInstance : effectiveTasks) {
		  int inDate = newTaskInstance.getDate();
		  double inStartTime = newTaskInstance.getStartTime();
		  double inDuration = newTaskInstance.getDuration();
		  for(Task task : listOfTasks) {
			  if(task.isOverlapping(inDate, inStartTime, inDuration)) {
				  return task;
			  }
		  }
	  }
	  
	  return null;
  }
  
  /**
   * Checks if the given parameters match with an instance of any recurring task.
   * @param date Start date
   * @param startTime Start time
   * @param duration Duration
   * @return Task it overlaps
   */
  public Task matchesRecurringTask(int date, double startTime, double duration) {
	  for(Task task : listOfTasks) {
		  if (task instanceof RecurringTask) {
			  List<Task> effectiveTasks = ((RecurringTask) task).getEffectiveTasks();
			  for(Task effectiveTask : effectiveTasks) {
				  if (effectiveTask.getDate() == date && effectiveTask.getStartTime() == startTime && effectiveTask.getDuration() == duration) {
					  return task;
				  }
			  }
		  }
	  }
	  
	  return null;
  }
}

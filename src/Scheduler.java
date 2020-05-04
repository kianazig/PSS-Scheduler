import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Scheduler {

  private List<Task> listOfTasks = new ArrayList<>();

  void addTask(Task newTask) {
    listOfTasks.add(newTask);
  }

  void deleteTask(Task newTask) {
    int counter = 0;
    while (counter < listOfTasks.size()) {
      if (newTask.equals(listOfTasks.get(counter))) {
        listOfTasks.remove(listOfTasks.get(counter));
        return;
      }

      ++counter;
    }

    System.out.print("Task does not exist.");
    return;
  }

  Task getTask(String retrievalName) {
    int counter = 0;
    while (counter < listOfTasks.size()) {
      if (retrievalName.equals(listOfTasks.get(counter).getName())) {
        return listOfTasks.get(counter);
      }

      ++counter;
    }

    System.out.print("Task name does not exist.");
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
   * Returns the corresponding end date for a given start date and duration.
   * 
   * @param startDate The starting date.
   * @param duration  The duration of time to pass between start date and end
   *                  date. (1=Day, 7=Week, 30=Month)
   * @return int representing end date.
   */
  public int getEndDate(int startDate, int duration) {
    // TODO: Implement
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
        } else {
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
      obj.put("StartDate", task.getDate());
      obj.put("StartTime", task.getStartTime());
      obj.put("Duration", task.getDuration());

      if (task instanceof RecurringTask) {
        obj.put("EndDate", ((RecurringTask) task).getEndDate());
        obj.put("Frequency", ((RecurringTask) task).getFrequency());
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

      for (Object task : taskList) {
        JSONObject taskObject = (JSONObject) task;

        String name = (String) taskObject.get("Name");
        String type = (String) taskObject.get("Type");
        double startTime = ((Number) taskObject.get("StartTime")).doubleValue();
        double duration = ((Number) taskObject.get("Duration")).doubleValue();

        Task newTask;
        if (taskObject.containsKey("EndDate")) {
          newTask = new RecurringTask(name, type, ((Number) taskObject.get("StartDate")).intValue(), startTime,
              duration, ((Number) taskObject.get("EndDate")).intValue(),
              ((Number) taskObject.get("Frequency")).intValue());
        } else {
          newTask = new Task(name, type, ((Number) taskObject.get("Date")).intValue(), startTime, duration);
        }

        addTask(newTask);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

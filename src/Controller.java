import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Controller {
  public static void main(String[] args) {
    System.out.println("Test: Project Set Up Successful");
  }

  /**
   * Reads and adds tasks from JSON file.
   * 
   * @param filename - Filename of the JSON. Ex: C:\Users\Bob\Desktop\Set1.json
   */
  private static void readFromFile(String filename) {
    try (FileReader reader = new FileReader(filename)) {
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

        // Waiting for Scheduler class to be finished so it can call the addTask()
        // method.
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

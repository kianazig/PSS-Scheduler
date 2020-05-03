import java.io.*;

public class Controller {
  private static Scheduler scheduler;
  private static UserInterface ui;
  private static Controller controller;

  public static void main(String[] args) {
    System.out.println("Test: Project Set Up Successful");
    ui = new UserInterface();
    scheduler = new Scheduler();
    controller = new Controller();
    try {
    	while(controller.displayMenu()) {
    		
    	}
    } catch(IOException io) {
    	System.exit(1);
    }
  }

  /**
   * Displays the menu options and redirects program flow appropriately from
   * user's choices.
   * 
   * @throws IOException
   */
  private boolean displayMenu() throws IOException {
    int chosenMenuOption = ui.promptForMenuOption();
    switch (chosenMenuOption) {
      case 1:
        createATask();
        break;
      case 2:
        viewATask();
        break;
      case 3:
        deleteATask();
        break;
      case 4:
        editATask();
        break;
      case 5:
        writeSchedule();
        break;
      case 6:
        readFromFile();
        break;
      case 7:
        viewSchedule();
        break;
      case 8:
    	return false;
      default:
        // TODO: ERROR
    }

    return true;
  }

  /**
   * Allows the user to view their daily, weekly, or monthly schedule.
   * 
   * @throws IOException
   */
  private void viewSchedule() throws IOException {
    int frequency = ui.promptForFrequency();
    int startDate = ui.promptForDate();
    int endDate = scheduler.getEndDate(startDate, frequency);

    ui.printListOfTasks(scheduler.getTasksInTimePeriod(startDate, endDate));
  }

  /**
   * Prompts a user for a file and reads the schedule from that file.
   * 
   * @throws IOException
   */
  private static void readFromFile() throws IOException {
    String fileName = ui.promptForFileName();
    boolean fileExists = scheduler.writeFileExists(fileName);
    if (fileExists) {
      scheduler.readFromFile(fileName);
    } else {
      ui.printFileNameError();
      // TODO: What to do next if file doesn't exists?
      // Back to main menu or ask again?
    }
  }

  /**
   * Writes the users schedule to a file.
   * 
   * @throws IOException
   */
  private void writeSchedule() throws IOException {
    int frequency = ui.promptForFrequency();
    int startDate = ui.promptForDate();
    int endDate = scheduler.getEndDate(startDate, frequency);

    String fileName = ui.promptForFileName();
    boolean fileExists = scheduler.writeFileExists(fileName);
    boolean overwriteFile = true;
    if (fileExists) {
      overwriteFile = ui.promptForFileOverwrite();
    }

    if (overwriteFile) {
      scheduler.writeToFile(fileName, scheduler.getTasksInTimePeriod(startDate, endDate));
    } else {
      // TODO: What to do if user doesn't want to overrwite file?
      // Back to main menu or allow for new file name input?
    }

  }

  /**
   * Prompts user for task and allows them to edit it.
   */
  private void editATask() {
    // TODO Auto-generated method stub

  }

  /**
   * Prompts user for task and deletes it.
   * 
   * @throws IOException
   */
  private void deleteATask() throws IOException {
    String taskName = ui.promptForTaskName();
    scheduler.deleteTask(scheduler.getTask(taskName));
  }

  /**
   * Prompts user for task and displays it.
   * 
   * @throws IOException
   */
  private void viewATask() throws IOException {
    String taskName = ui.promptForTaskName();
    ui.printTask(scheduler.getTask(taskName));
  }

  /**
   * Prompts user for task information and creates task.
   */
  private void createATask() throws IOException {
    // TODO: addTask needs to be expanded, or this info sent elsewhere. Then Test.
	String name = ui.promptForTaskName();
	String type = ui.promptForTaskType();
	int date = ui.promptForDate();
	double startTime = ui.promptForTime();
	double duration = ui.promptForDuration();
	scheduler.addTask(new Task(name, type, date, startTime, duration));
  }
}

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
    
    // main loop of the program, displays menu until user decides to exit
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
      System.out.println(scheduler.getTasksInTimePeriod(startDate, endDate));

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
    if(scheduler.getTask(taskName) != null) {
    	if (scheduler.deleteTask(scheduler.getTask(taskName)) == false) {
    		ui.printTaskNameError();
    	}
    	else {
    		ui.printTaskSuccessfullyDeleted();
    	}
    }
    else {
    	ui.printTaskNameError();
    }
    
  }

  /**
   * Prompts user for task and displays it.
   * 
   * @throws IOException
   */
  private void viewATask() throws IOException {
    String taskName = ui.promptForTaskName();
    if(scheduler.getTask(taskName) != null) {
    	ui.printTask(scheduler.getTask(taskName));
    }
    else {
    	ui.printTaskNameError();
    }
    
  }

  /**
   * Prompts user for task information and creates task.
   */
  private void createATask() throws IOException {
    // TODO: addTask needs to be expanded, or this info sent elsewhere. Then Test.
	// TODO: Need to know if user is creating a transient, recurring, or antitask.
	  
	int taskClass = ui.promptForTaskClass();
	
	if(taskClass == 1) {//Transient Task
		createTransientTask();
	}
	else if(taskClass == 2) {//Recurring Task
		createRecurringTask();
	}
	else if(taskClass == 3) {//AntiTask
		createAntiTask();
	}
  }
  
  /**
   * Creates a transient task.
   * @throws IOException 
   */
  private void createTransientTask() throws IOException {
	    String name = ui.promptForTaskName();
	    while(!scheduler.isNameUnique(name)) {
	    	ui.printTaskNameExists();
	    	name = ui.promptForTaskName();
	    }
	    
	    String type = ui.promptForTaskType();
	    while(!scheduler.isValidTaskType(true, type)) {
	    	ui.printInvalidTaskType(true);
	    	type = ui.promptForTaskType();
	    }
	    
	    int date = ui.promptForDate();
	    double startTime = ui.promptForTime();
	    double duration = ui.promptForDuration();
	    // test if the provided times cause any overlap with tasks already in the scheduler
	    Task overlapTask = scheduler.isOverlapping(date, startTime, duration);
	    // if there are no tasks that overlap with the provided parameters, do nothing
	    while(overlapTask != null) {
	    	// otherwise, print the conflicting task and ask the user to try again
	    	// perhaps a printOverlapMessage method can be put in the UserInterface class
	    	System.out.println("Provided times conflict with the following task: ");
	    	ui.printTask(overlapTask);
	    	System.out.println("Please provide different time parameters");
	    	date = ui.promptForDate();
	    	startTime = ui.promptForTime();
	    	duration = ui.promptForDuration();
	    	overlapTask = scheduler.isOverlapping(date, startTime, duration);
	    }
	    
	    scheduler.addTask(new Task(name, type, date, startTime, duration));
  }
  
  /**
   * Creates a recurring task.
 * @throws IOException 
   */
  private void createRecurringTask() throws IOException {
	    String name = ui.promptForTaskName();
	    while(!scheduler.isNameUnique(name)) {
	    	ui.printTaskNameExists();
	    	name = ui.promptForTaskName();
	    }
	    
	    String type = ui.promptForTaskType();
	    while(!scheduler.isValidTaskType(false, type)) {
	    	ui.printInvalidTaskType(false);
	    	type = ui.promptForTaskType();
	    }
	    
	    int date = ui.promptForDate();
	    double startTime = ui.promptForTime();
	    double duration = ui.promptForDuration();
	    int endDate = ui.promptForEndDate();
	    int frequency = ui.promptForFrequency();
	    // test if the provided times cause any overlap with tasks already in the scheduler
	    Task overlapTask = scheduler.isOverlapping(date, startTime, duration, endDate, frequency);
	    // if there are no tasks that overlap with the provided parameters, do nothing
	    while(overlapTask != null) {
	    	// otherwise, print the conflicting task and ask the user to try again
	    	// perhaps a printOverlapMessage method can be put in the UserInterface class
	    	System.out.println("Provided times conflict with the following task: ");
	    	ui.printTask(overlapTask);
	    	System.out.println("Please provide different time parameters");
	    	date = ui.promptForDate();
	    	startTime = ui.promptForTime();
	    	duration = ui.promptForDuration();
	    	endDate = ui.promptForEndDate();
	    	frequency = ui.promptForFrequency();
	    	overlapTask = scheduler.isOverlapping(date, startTime, duration, endDate, frequency);
	    }
	    
	    scheduler.addTask(new RecurringTask(name, type, date, startTime, duration, endDate, frequency));
  }
  
  /**
   * Creates an antitask.
   * @throws IOException 
   */
  private void createAntiTask() throws IOException {
	    String name = ui.promptForTaskName();
	    while(!scheduler.isNameUnique(name)) {
	    	ui.printTaskNameExists();
	    	name = ui.promptForTaskName();
	    }
	    
	    String type = "Cancellation";
	    
	    int date = ui.promptForDate();
	    double startTime = ui.promptForTime();
	    double duration = ui.promptForDuration();
	    
	    //TODO: Check that this matches recurring task instance.
	    
	    Task matchingRecurringTask = scheduler.matchesRecurringTask(date, startTime, duration);
	    
	    // test if the provided times cause any overlap with tasks already in the scheduler
	    Task overlapTask = scheduler.isOverlapping(date, startTime, duration);
	    // if there are no tasks that overlap with the provided parameters, do nothing
	    while(matchingRecurringTask == null) {
	    	// otherwise, print the conflicting task and ask the user to try again
	    	// perhaps a printOverlapMessage method can be put in the UserInterface class
	    	System.out.println("This antitask does not match any instances of any recurring tasks.");
	    	System.out.println("Please provide different time parameters");
	    	date = ui.promptForDate();
	    	startTime = ui.promptForTime();
	    	duration = ui.promptForDuration();
	    	matchingRecurringTask = scheduler.matchesRecurringTask(date, startTime, duration);
	    }
	    
	    System.out.println("Replaced an instance of the following recurring task: ");
	    ui.printTask(matchingRecurringTask);
	    scheduler.addTask(new Task(name, type, date, startTime, duration));
  }
}

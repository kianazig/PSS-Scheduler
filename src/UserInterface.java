import java.util.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class UserInterface {
  private Scanner sc = new Scanner(System.in);

  /**
   * Iterates through a list of tasks and passes each to printTask().
   * 
   * @param tasks - a list of Task to be printed
   */
  public void printListOfTasks(List<Task> tasks) {
    for (int i = 0; i < tasks.size(); i++) {
      printTask(tasks.get(i));
    }
  }

  /**
   * Prints the start date, start time and name of the task.
   * 
   * @param task - a single Task to be printed
   */
  public void printTask(Task task) {
    String taskName = task.getName();
    int taskStartDate = task.getDate();
    double taskStartTime = task.getStartTime();
    double taskDuration = task.getDuration();
    String taskType = task.getType();
    int taskEndDate = task.getEndDate();
    int taskFrequency = task.getFrequency();
    String reformattedDate = null;
    String reformattedTime = null;
    String reformattedDuration = null;
    String reformattedEndDate = null;
    String freqUserFriendly = null;

    // Format date
    try {
      String taskStartDateStr = String.valueOf(taskStartDate);
      SimpleDateFormat userDateFormat = new SimpleDateFormat("MM/dd/yyyy");
      SimpleDateFormat dataDateFormat = new SimpleDateFormat("yyyyMMdd");
      reformattedDate = userDateFormat.format(dataDateFormat.parse(taskStartDateStr));

      if(task instanceof RecurringTask) {
        String taskEndDateStr = String.valueOf(taskEndDate);
        reformattedEndDate = userDateFormat.format(dataDateFormat.parse(taskEndDateStr));
        if(taskFrequency == 1) {
          freqUserFriendly = "daily";
        }
        else if(taskFrequency == 7) {
          freqUserFriendly = "weekly";
        }
        else if(taskFrequency == 30) {
          freqUserFriendly = "monthly";
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Format time
    int intPart = (int) taskStartTime;
    double decimalPart = taskStartTime - intPart;
    if (decimalPart == 0) {
      reformattedTime = String.valueOf(intPart) + ":00";
    } else if (decimalPart == 0.25) {
      reformattedTime = String.valueOf(intPart) + ":15";
    } else if (decimalPart == 0.50) {
      reformattedTime = String.valueOf(intPart) + ":30";
    } else if (decimalPart == 0.75) {
      reformattedTime = String.valueOf(intPart) + ":45";
    }

    int intPartDuration = (int) taskDuration;
    double decimalPartDuration = taskDuration - intPartDuration;
    if (decimalPartDuration == 0) {
      reformattedDuration = String.valueOf(intPartDuration) + ":00";
    } else if (decimalPartDuration == 0.25) {
      reformattedDuration = String.valueOf(intPartDuration) + ":15";
    } else if (decimalPartDuration == 0.50) {
      reformattedDuration = String.valueOf(intPartDuration) + ":30";
    } else if (decimalPartDuration == 0.75) {
      reformattedDuration = String.valueOf(intPartDuration) + ":45";
    }

    // Recurring: Print task in format: MM/dd/yyyy, HH:mm - EndDate - [TaskName], TaskType
    if(task instanceof RecurringTask) {
      System.out.println("[ " + taskName + " ]");
      System.out.println("  " + reformattedDate + ", " + reformattedTime + " - " + reformattedEndDate);
      System.out.println("  Type: " + taskType);
      System.out.println("  Duration: " + reformattedDuration);
      System.out.println("  Frequency: " + freqUserFriendly);
    }
    // Normal: Print task in format: MM/dd/yyyy, HH:mm - [TaskName], TaskType
    else {
      System.out.println("[ " + taskName + " ]");
      System.out.println("  " + reformattedDate + ", " + reformattedTime);
      System.out.println("  Type: " + taskType);
      System.out.println("  Duration: " + reformattedDuration);
    }
  }

  /**
   * Asks user to enter the name of the task.
   * 
   * @return - the task name
   * @throws IOException
   */
  public String promptForTaskName() throws IOException {
    String taskName;
    System.out.print("Enter the task name: ");
    taskName = sc.nextLine();
    return taskName;
  }

  /**
   * Asks user to enter the type of the task.
   * 
   * @return - the task type
   * @throws IOException
   */
  public String promptForTaskType() throws IOException {
    String taskType;
    System.out.print("Enter the task type: ");
    taskType = sc.nextLine();
    return taskType;
  }

  /**
   * Asks user to enter the date that a task should start.
   * 
   * @return - the task start date
   * @throws IOException
   */
  public int promptForDate() throws IOException {
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    String taskDateStr;
    String[] dateParts = null;
    int taskDate;

    sdf.setLenient(false);
    while (true) {
      System.out.print("Enter the task start date (in format, MM/DD/YYYY): ");
      taskDateStr = sc.nextLine();

      // Check if input is valid
      if(taskDateStr.matches("(\\d{2})\\/(\\d{2})\\/(\\d{2,4})$")) {
        try {
          sdf.parse(taskDateStr);
          break;
        } catch (ParseException e) {
          System.out.println("Input is invalid!");
        }
      } 
      else {
        System.out.println("Input is invalid!");
      }
    }
    dateParts = taskDateStr.split("/");
    taskDateStr = dateParts[2] + dateParts[0] + dateParts[1];
    taskDate = Integer.valueOf(taskDateStr);

    return taskDate;
  }
  
  /**
   * Asks user to enter the date that a task should end.
   * 
   * @return - the task end date
   * @throws IOException
   */
  public int promptForEndDate() throws IOException {
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    String taskDateStr;
    String[] dateParts = null;
    int taskDate;

    sdf.setLenient(false);
    while (true) {
      System.out.print("Enter the task end date (in format, MM/DD/YYYY): ");
      taskDateStr = sc.nextLine();

      // Check if input is valid
      if(taskDateStr.matches("(\\d{2})\\/(\\d{2})\\/(\\d{2,4})$")) {
        try {
          sdf.parse(taskDateStr);
          break;
        } catch (ParseException e) {
          System.out.println("Input is invalid!");
        }
      } 
      else {
        System.out.println("Input is invalid!");
      }
    }
    dateParts = taskDateStr.split("/");
    taskDateStr = dateParts[2] + dateParts[0] + dateParts[1];
    taskDate = Integer.valueOf(taskDateStr);

    return taskDate;
  }

  /**
   * Checks if there are letters in the array, if yes return false else true
   * 
   * @param arr to be checked for letters
   * @return boolean
   */
  public boolean isNumeric(String[] arr) {
    for (int i = 0; i < arr.length; i++) {
      if (!arr[i].matches("[0-9]+")) {
        return false;
      }
    }
    return true;
  }

  /**
   * Asks user to enter the time that a task should start.
   * 
   * @return double representing the task start time
   * @throws IOException
   */
  public double promptForTime() throws IOException {
    String taskTimeStr;
    String[] timeParts = null;
    double taskTime;

    while (true) {
      System.out.print("Enter the task start time (in 24-hour format, HH:MM): ");
      taskTimeStr = sc.nextLine();

      // Check if input is valid
      timeParts = taskTimeStr.split(":");
      
      if(timeParts.length == 2 && isNumeric(timeParts)) {
    	  if(Integer.parseInt(timeParts[0]) <= 24 && Integer.parseInt(timeParts[1]) <= 59) {
    		  if(timeParts[1].length() == 2) {
    			  break;
    		  }
    	  }
      }
      
      System.out.println("Input is invalid!");
    }
    
    int hour = Integer.valueOf(timeParts[0]);
    int minutes = Integer.valueOf(timeParts[1]);
    double formattedMinutes = roundMinutes(minutes);
    if (formattedMinutes == 1) {
      formattedMinutes = 0;
      hour = hour + 1;
    }
    formattedMinutes = formattedMinutes / 100;
    taskTime = hour + formattedMinutes;

    return taskTime;
  }

  /**
   * Takes the user-formatted minutes and changes it to program-readable minutes.
   * 
   * @param min the int value for minutes
   * @return the rounded double for minutes
   */
  private int roundMinutes(int min) {
    int formattedMin;

    if (min >= 0 && min <= 7) {
      formattedMin = 0;
    } else if (min >= 8 && min <= 23) {
      formattedMin = 25;
    } else if (min >= 24 && min <= 38) {
      formattedMin = 50;
    } else if (min >= 39 && min <= 53) {
      formattedMin = 75;
    } else {
      formattedMin = 1;
    }
    return formattedMin;
  }

  /**
   * Asks user to enter the duration of the task.
   * 
   * @return double representing the task duration
   * @throws IOException
   */
  public double promptForDuration() throws IOException {
    String taskDurationStr;
    String[] timeParts = null;
    double taskDuration;

    while (true) {
      System.out.print("Enter the task duration (in format, HH:MM): ");
      taskDurationStr = sc.nextLine();

      // Check if input is valid
      timeParts = taskDurationStr.split(":");
      if(timeParts.length == 2 && isNumeric(timeParts)) {
    	  if(timeParts[1].length() == 2 && Integer.parseInt(timeParts[0]) < 24) {
    		  break;
    	  }
      }
      System.out.println("Input is invalid!");
    }
    
    int hour = Integer.valueOf(timeParts[0]);
    int minutes = Integer.valueOf(timeParts[1]);
    double formattedMinutes = roundMinutes(minutes);
    if (formattedMinutes == 1) {
      formattedMinutes = 0;
      hour = hour + 1;
    }
    formattedMinutes = formattedMinutes / 100;
    taskDuration = hour + formattedMinutes;

    return taskDuration;
  }

  /**
   * Asks user to enter the frequency at which the task should occur.
   * 
   * @return int representing the task frequency
   * @throws IOException
   */
  public int promptForFrequency() throws IOException {
    int taskFrequency = 0;

    while (true) {
      try {
        System.out.print("Enter the task frequency (1 for daily, 7 for weekly, 30 for monthly): ");
        sc = new Scanner(System.in);
        taskFrequency = sc.nextInt();
        sc.nextLine();
        // Check if input is valid
        if (taskFrequency != 1 && taskFrequency != 7 && taskFrequency != 30) {
          System.out.println("Input is invalid!");
        } else {
          break;
        }
      } catch (InputMismatchException e) {
        System.out.println("Input is invalid!");
      }
    }

    return taskFrequency;
  }

  /**
   * Asks user to enter a file name.
   * 
   * @return String of the file name
   * @throws IOException
   */
  public String promptForFileName() throws IOException {
    String fileName = null;

    while (true) {
      System.out.print("Enter the file name: ");
      fileName = sc.nextLine();

      if (fileName.contains(".")) {
        String[] splitStr = fileName.split("\\.");

        if (splitStr[splitStr.length - 1].equalsIgnoreCase("json")) {
          break;
        }
      }

      System.out.println("Input is of an invalid file type! Make sure it is a json file.");
    }

    return fileName;
  }

  /**
   * Asks the user whether they wish to overwrite an existing file.
   * 
   * @return boolean depending on whether user wishes to overwrite the file
   */
  public boolean promptForFileOverwrite() {
    String response = null;
    boolean isValid = false;
    System.out.println("There is currently an existing file with the same name. Do you wish to overwrite it?");
    System.out.print("Enter 'y' or 'n': ");

    while (!isValid) {
      response = sc.nextLine();
      if (response.equalsIgnoreCase("y") || response.equalsIgnoreCase("n")) {
        isValid = true;
        break;
      }
      System.out.print("Input is invalid. Please enter 'y' or 'n': ");
    }

    if (response.equalsIgnoreCase("y")) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Prompts the user with main menu options and returns an integer representing
   * their choice.
   * 
   * @return int representing user's menu option choice.
   */
  public int promptForMenuOption() {
    int chosenMenuOption = 0;
    System.out
        .println("\nMain Menu: \n" + "1: Create A Task\n" + "2: View A Task\n" + "3: Delete A Task\n" + "4: Edit A Task\n"
            + "5: Write Schedule to File\n" + "6: Read Schedule from File\n" + "7. View Schedule\n" + "8. Exit");
    System.out.print("Please enter a number: ");
    boolean incorrectInput = true;
    do {
      try {
        chosenMenuOption = sc.nextInt();
        sc.nextLine();
        while (chosenMenuOption < 1 || chosenMenuOption > 8) {
          System.out.print("Invalid input, try again: ");
          chosenMenuOption = sc.nextInt();
          sc.nextLine();
        }
        incorrectInput = false;
      } catch (Exception E) {
        System.out.print("Invalid input, try again: ");
        sc.nextLine();
      }
    } while (incorrectInput);
    return chosenMenuOption;
  }

  /**
   * Alerts the user that file does not exist.
   */
  public void printFileNameError() {
    System.out.println("Error! This file does not exist.");
  }
  
  /**
   * Alerts the user that task name is invalid.
   */
  public void printTaskNameError() {
	  System.out.println("Error! No task with this name exists.");
  }
  
  /**
   * Alerts the user that task was successfully deleted.
   */
  public void printTaskSuccessfullyDeleted() {
	  System.out.println("Task successfully deleted.");
  }
  
  /**
   * Alerts the user that a task with the given name already exists.
   */
  public void printTaskNameExists() {
	  System.out.println("Provided name is not unique, please provide a unique task name.");
  }
  
  /**
   * Asks the user if they want to create a transient, recurring, or antitask. 
   * @return String representing type of task. 
   */
  public int promptForTaskClass() {
	  int chosenMenuOption = 0;
	  System.out.println("\nWhat type of task would you like to create: \n" + "1: Transient Task\n" + "2: Recurring Task\n" + "3: AntiTask\n");
	  System.out.print("Please enter a number: ");
	  boolean incorrectInput = true;
	  do {
		  try {
			  chosenMenuOption = sc.nextInt();
			  sc.nextLine();
			  while (chosenMenuOption < 1 || chosenMenuOption > 3) {
				  System.out.print("Invalid input, try again: ");
				  chosenMenuOption = sc.nextInt();
				  sc.nextLine();
	     }
			  incorrectInput = false;
		 } catch (Exception E) {
			 System.out.print("Invalid input, try again: ");
			 sc.nextLine();
		 }
	  } while (incorrectInput);
	  
	  return chosenMenuOption;
  }

  /**
   * Alerts the user that the task 'type' is invalid.
   * @param isTransientTask True if task is transient, false otherwise.
   */
  public void printInvalidTaskType(boolean isTransientTask) {
	  if(isTransientTask) {
		  System.out.println("Invalid Task Type! Please enter Visit, Shopping, or Appointment.");
	  }
	  else {
		  System.out.println("Invalid Task Type! Please enter Class, Study, Sleep, Exercise, Work, or Meal.");
	  }
  }

  public int promptForChanges(int type) {
    int chosenOption = 0;

    if(type == 1) { // Recurring
      System.out.println("\nWhat would you like to change: \n" + "1: Name\n" + "2: Type\n" + "3: Start Date\n" + "4: Start Time\n" + "5: Duration\n"
                          + "6: End Date\n" + "7: Frequency\n" + "8: Return to main menu\n");
      System.out.print("Please enter a number: ");

    }
    else if(type == 0) { // Transient/Anti
      System.out.println("\nWhat would you like to change: \n" + "1: Name\n" + "2: Type\n" + "3: Start Date\n" + "4: Start Time\n" + "5: Duration\n" 
                          + "6: Return to main menu\n");
      System.out.print("Please enter a number: ");
    }

    boolean incorrectInput = true;
	  do {
		  try {
			  chosenOption = sc.nextInt();
        sc.nextLine();
        if(type == 1) {
          while (chosenOption < 1 || chosenOption > 8) {
            System.out.print("Invalid input, try again: ");
            chosenOption = sc.nextInt();
            sc.nextLine();
          }
        }
        else if(type == 0) {
          while (chosenOption < 1 || chosenOption > 6) {
            System.out.print("Invalid input, try again: ");
            chosenOption = sc.nextInt();
            sc.nextLine();
          }
        }
			  incorrectInput = false;
		 } catch (Exception E) {
			 System.out.print("Invalid input, try again: ");
			 sc.nextLine();
		 }
	  } while (incorrectInput);

    return chosenOption;
  }
}

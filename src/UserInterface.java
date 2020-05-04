import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

public class UserInterface {
    private Scanner sc = new Scanner(System.in);

    /**
     * Iterates through a list of tasks and passes each to printTask().
     * @param tasks - a list of Task to be printed
     */
    public void printListOfTasks(List<Task> tasks) {
        for(int i = 0; i < tasks.size(); i++) {
            printTask(tasks.get(i));
        }
    }

    /**
     * Prints the start date, start time and name of the task.
     * @param task - a single Task to be printed
     */
    public void printTask(Task task) {
        String taskName = task.getName();
        int taskStartDate = task.getDate();
        String reformattedDate = null;
        double taskStartTime = task.getStartTime();
        String reformattedTime = null;
        
        // Format date
        try {
            String taskStartDateStr = String.valueOf(taskStartDate);
            SimpleDateFormat userDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat dataDateFormat = new SimpleDateFormat("yyyyMMdd");
            reformattedDate = userDateFormat.format(dataDateFormat.parse(taskStartDateStr));
        } catch (Exception e) { e.printStackTrace(); }

        // Format time
        int intPart = (int) taskStartTime;
        double decimalPart = taskStartTime - intPart;
        if(decimalPart == 0) {
            reformattedTime = String.valueOf(intPart) + ":00";
        }
        else if(decimalPart == 0.25) {
            reformattedTime = String.valueOf(intPart) + ":15";
        }
        else if(decimalPart == 0.50) {
            reformattedTime = String.valueOf(intPart) + ":30";
        }
        else if(decimalPart == 0.75) {
            reformattedTime = String.valueOf(intPart) + ":45";
        }

        // Print task in format: MM/dd/yyyy, HH:mm - TaskName
        System.out.println(reformattedDate + ", " + reformattedTime + " - " + taskName);
    }

    /**
     * Asks user to enter the name of the task.
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
     * @return - the task start date
     * @throws IOException
     */
    public int promptForDate() throws IOException {
        boolean isValid = false;
        String taskDateStr;
        String[] dateParts = null;
        int taskDate;
        while(!isValid) {
            System.out.print("Enter the task start date (in format, MM/DD/YYYY): ");
            taskDateStr = sc.nextLine();

            // Check if input is valid
            dateParts = taskDateStr.split("/");
            if(dateParts.length != 3 || !isNumeric(dateParts)) {
                System.out.println("Input is invalid!");
                break;
            }
            isValid = true;
        }
        taskDateStr = dateParts[2] + dateParts[0] + dateParts[1];
        taskDate = Integer.valueOf(taskDateStr);

        return taskDate;
    }

    /**
     * Checks if there are letters in the array, if yes return false else true
     * @param arr to be checked for letters
     * @return boolean 
     */
    public boolean isNumeric(String[] arr) {
        for(int i = 0; i < arr.length; i++) {
            if(arr[i].matches("^[a-zA-Z]*$]")) {
                return false;
            }
        }
        return true;
    }

    /**
     * Asks user to enter the time that a task should start.
     * @return double representing the task start time
     * @throws IOException
     */
    public double promptForTime() throws IOException {
        boolean isValid = false;
        String taskTimeStr;
        String[] timeParts = null;
        double taskTime;

        while(!isValid) {
            System.out.print("Enter the task start time (in 24-hour format, HH:MM): ");
            taskTimeStr = sc.nextLine();

            // Check if input is valid
            timeParts = taskTimeStr.split(":");
            if(timeParts.length != 2 || !isNumeric(timeParts)) {
                System.out.println("Input is invalid!");
                break;
            }
            isValid = true;
        }
        int hour = Integer.valueOf(timeParts[0]);
        int minutes = Integer.valueOf(timeParts[1]);
        double formattedMinutes = roundMinutes(minutes);
        if(formattedMinutes == 1) {
            formattedMinutes = 0;
            hour = hour + 1;
        }
        formattedMinutes = formattedMinutes/100;
        taskTime = hour + formattedMinutes;

        return taskTime;
    }

    /**
     * Takes the user-formatted minutes and changes it to program-readable minutes.
     * @param min the int value for minutes
     * @return the rounded double for minutes
     */
    private int roundMinutes(int min) {
        int formattedMin;

        if(min >= 0 && min <= 7) {
            formattedMin = 0;
        }
        else if(min >= 8 && min <= 23) {
            formattedMin = 25;
        }
        else if(min >= 24 && min <= 38) {
            formattedMin = 50;
        }
        else if(min >= 39 && min <= 53) {
            formattedMin = 75;
        }
        else {
            formattedMin = 1;
        }
        return formattedMin;
    }

    /**
     * Asks user to enter the duration of the task.
     * @return double representing the task duration
     * @throws IOException
     */
    public double promptForDuration() throws IOException {
        boolean isValid = false;
        String taskDurationStr;
        String[] timeParts= null;
        double taskDuration;

        while(!isValid) {
            System.out.print("Enter the task duration (in format, HH:MM): ");
            taskDurationStr = sc.nextLine();

            // Check if input is valid
            timeParts = taskDurationStr.split(":");
            if(timeParts.length != 2 || !isNumeric(timeParts)) {
                System.out.println("Input is invalid!");
                break;
            }
            isValid = true;
        }
        int hour = Integer.valueOf(timeParts[0]);
        int minutes = Integer.valueOf(timeParts[1]);
        double formattedMinutes = roundMinutes(minutes);
        if(formattedMinutes == 1) {
            formattedMinutes = 0;
            hour = hour + 1;
        }
        formattedMinutes = formattedMinutes/100;
        taskDuration = hour + formattedMinutes;

        return taskDuration;
    }

    /**
     * Asks user to enter the frequency at which the task should occur.
     * @return int representing the task frequency
     * @throws IOException
     */
    public int promptForFrequency() throws IOException {
        boolean isValid = false;
        int taskFrequency = 0;

        while(!isValid) {
            System.out.print("Enter the task frequency (1 for daily, 7 for weekly, 30 for monthly): ");
            taskFrequency = sc.nextInt();
            sc.nextLine();

            // Check if input is valid
            if(taskFrequency != 1 || taskFrequency != 7 || taskFrequency != 30) {
                System.out.println("Input is invalid!");
                break;
            }
            isValid = true;
        }

        return taskFrequency;
    }

    /**
     * Asks user to enter a file name.
     * @return String of the file name
     * @throws IOException
     */
    public String promptForFileName() throws IOException {
        boolean isValid = false;
        String fileName = null;

        while (!isValid) {
            System.out.print("Enter the file name: ");
            fileName = sc.nextLine();

            String[] splitStr = fileName.split(".");
            if(!(splitStr[1].equalsIgnoreCase("json"))) {
                System.out.println("Input is of an invalid file type! Make sure it is a json file.");
                break;
            }
        }
        
        return fileName;
    }

    /**
     * Asks the user whether they wish to overwrite an existing file. 
     * @return boolean depending on whether user wishes to overwrite the file
     */
    public boolean promptForFileOverwrite() {
        String response = null;
        boolean isValid = false;
        System.out.println("There is currently an existing file with the same name. Do you wish to overwrite it?");
        System.out.print("Enter 'y' or 'n': ");

        while(!isValid) {
            response = sc.nextLine();
            if(response.equalsIgnoreCase("y") || response.equalsIgnoreCase("n")) {
                isValid = true;
                break;
            }
            System.out.print("Input is invalid. Please enter 'y' or 'n': ");
        }

        if(response.equalsIgnoreCase("y")) {
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Prompts the user with main menu options and returns an integer representing their choice.
     * @return int representing user's menu option choice. 
     */
    public int promptForMenuOption() {
    	int chosenMenuOption = 0;
    	System.out.println("Main Menu: \n"
    	  		+ "1: Create A Task\n"
    	  		+ "2: View A Task\n"
    	  		+ "3: Delete A Task\n"
    	  		+ "4: Edit A Task\n"
    	  		+ "5: Write Schedule to File\n"
    	  		+ "6: Read Schedule from File\n"
    	  		+ "7. View Schedule\n"
    	  		+ "8. Exit");
    	System.out.print("Please enter a number: ");
    	boolean incorrectInput = true;
    	do {
    		try {
    			chosenMenuOption = sc.nextInt();
    			sc.nextLine();
    			while(chosenMenuOption < 1 || chosenMenuOption > 8 ) {
    				System.out.print("Invalid input, try again: ");
    				chosenMenuOption = sc.nextInt();
    				sc.nextLine();
    			}
    			incorrectInput = false;
    		} catch(Exception E) {
    			System.out.println("Invalid input, try again: ");
    			sc.nextLine();
    		}
    	} while(incorrectInput);
        return chosenMenuOption;
    }
    
    /**
     * Alerts the user that file does not exist.
     */
	public void printFileNameError() {
		System.out.println("Error! This file does not exist.");
	}
}

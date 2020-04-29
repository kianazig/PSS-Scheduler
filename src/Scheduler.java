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


}

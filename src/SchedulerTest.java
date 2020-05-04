import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SchedulerTest {
	private Scheduler scheduler;

	@BeforeEach
	void setUp() throws Exception {
		scheduler = new Scheduler();
	}
	
	@Test
	public void getTaskTest() {
		Task task = new Task("do stats homework", "Study", 20200503, 14.0, 2.0);
		scheduler.addTask(task);
		assertEquals(task, scheduler.getTask("do stats homework"));
	}
	
	@Test
	public void deleteTaskTest() {
		Task task = new Task("do stats homework", "Study", 20200503, 14.0, 2.0);
		scheduler.addTask(task);
		assertEquals(task, scheduler.getTask("do stats homework"));
		scheduler.deleteTask(task);
		assertEquals(null, scheduler.getTask("do stats homework"));
	}
	
	@Test
	public void testGetTasksInTimePeriodWithoutRecurring() {
		Task task1 = new Task("do stats homework", "Study", 20200503, 14.0, 2.0);
		scheduler.addTask(task1);
		List<Task> scheduledTasks = scheduler.getTasksInTimePeriod(20200502, 20200504);
		assert(scheduledTasks.contains(task1));
		
		scheduledTasks = scheduler.getTasksInTimePeriod(20190502, 20190504);
		assert(!scheduledTasks.contains(task1));
	}
	
	@Test
	public void testGetTasksInTimePeriodWithRecurring() {
		scheduler.addTask(new RecurringTask("OOP Class", "Class", 20200114, 19.00, 7.0, 20200507, 7));
		scheduler.addTask(new Task("do stats homework", "Study", 20200425, 14.0, 2.0));
		List<Task> scheduledTasks = scheduler.getTasksInTimePeriod(20200419, 20200507);
		assertEquals(4, scheduledTasks.size());
	}

	@AfterEach
	void tearDown() throws Exception {
	}

}

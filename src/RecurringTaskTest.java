import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecurringTaskTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	public void testGetEffectiveTasks() {
		//testing for weekly tasks
		RecurringTask task = new RecurringTask("OOP Class", "Class", 20200114, 19.00, 2.0, 20200508, 7);
		List<Task> effectiveTasks = task.getEffectiveTasks();
		assertEquals(17, effectiveTasks.size());
		
		//testing for weekly tasks within specified time period
		effectiveTasks = task.getEffectiveTasks(20200501, 20200508);
		assertEquals(1, effectiveTasks.size());
		
		//testing for daily tasks
		RecurringTask task1 = new RecurringTask("Study For Final", "Study", 20200501, 14.00, 1.0, 20200506, 1);
		effectiveTasks = task1.getEffectiveTasks();
		assertEquals(6, effectiveTasks.size());
		
		//testing for daily tasks within specified time period
		effectiveTasks = task1.getEffectiveTasks(20200503, 20200603);
		assertEquals(4, effectiveTasks.size());
		
		//testing for monthly tasks
		RecurringTask task2 = new RecurringTask("Once a month thing", "Study", 20200101, 18.00, 1.0, 20200506, 30);
		effectiveTasks = task2.getEffectiveTasks();
		assertEquals(5, effectiveTasks.size());
	}
	
	@Test
	public void testWithinTimePeriod() {
		RecurringTask task = new RecurringTask("OOP Class", "Class", 20200114, 19.00, 2.0, 20200508, 7);
		
		//testing middle to outside end date
		assert(task.withinTimePeriod(20200301, 20200601));
		
		//testing before start date to middle
		assert(task.withinTimePeriod(20191205, 20200128));
		
		//testing in between start date and end date of recurring task
		assert(task.withinTimePeriod(20200406, 20200408));
		assert(!task.withinTimePeriod(20200301, 20200302));
		
		//testing outside of date period
		assert(!task.withinTimePeriod(20200601, 20200702));
		
	}
	
}

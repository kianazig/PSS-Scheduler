import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskTest {

	private Task basicTask;
	
	@BeforeEach
	void setUp() throws Exception {
		basicTask = new Task("study for OOP final", "Study", 20200504, 16.00, 2.25);
	}

	@Test
	void isOverlappingTest() {
		assertEquals(false, basicTask.isOverlapping(20200504, 6.00, 1.00));
		assertEquals(true, basicTask.isOverlapping(20200504, 15.5, 1.0));
		assertEquals(true, basicTask.isOverlapping(20200504, 16.5, 1.0));
		assertEquals(true, basicTask.isOverlapping(20200504, 16.5, 3.0));
		assertEquals(true, basicTask.isOverlapping(20200504, 15.5, 5.0));
		
		assertEquals(false, basicTask.isOverlapping(20200503, 6.00, 24.0));
		assertEquals(false, basicTask.isOverlapping(20200503, 15.5, 24.5));
		assertEquals(true, basicTask.isOverlapping(20200503, 16.5, 25.0));
		assertEquals(true, basicTask.isOverlapping(20200503, 16.5, 28.0));
	}

}

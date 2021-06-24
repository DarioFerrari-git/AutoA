/**
 * 
 */
package sm.intersection;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sm.intersection.Car;
import sm.intersection.DIRECTION;

/**
 * @author sm
 *
 */
public class CarTest {
	
	private Car car;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		car = new Car("car", 50);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		car = null;
	}

	/**
	 * Test method for {@link sm.intersection.Car#addRoute()}.
	 */
	@Test
	public void testAddRoute() {
		assertEquals(car.getRoutes().size(), 0);
		List<DIRECTION> route = new ArrayList<>();
		route.add(DIRECTION.STRAIGHT);
		route.add(DIRECTION.LEFT);
		route.add(DIRECTION.RIGHT);
		car.addRoute(route);
		assertNotNull(car.getRoutes().get(0));
		assertNull(car.getRoutes().get(1));
		car.addRoute(route);
		assertNotNull(car.getRoutes().get(0));
		assertNotNull(car.getRoutes().get(1));
		assertNull(car.getRoutes().get(2));
		assertEquals(car.getRoutes().size(), 2);
	}

}

/**
 * 
 */
package sm.arg.intersection;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sm.intersection.Car;
import sm.intersection.STATUS;
import sm.intersection.UrgentCar;
import sm.intersection.WAY;

/**
 * @author sm
 *
 */
public class CrossingCarTest {

	private CrossingCar car;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.car = new CrossingCar(
				new UrgentCar(
						new Car("car", 50), 
						1), 
				WAY.WEST, 
				STATUS.APPROACHING, 
				50);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		car = null;
	}

	/**
	 * Test method for {@link sm.arg.intersection.CrossingCar#getTimeToCross()}.
	 */
	@Test
	public final void testGetTimeToCross() {
		assertEquals(3.6, this.car.getTimeToCross(), 0.01);
	}

}

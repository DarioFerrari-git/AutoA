/**
 * 
 */
package sm.arg.intersection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author sm
 *
 */
public class SmartRoadTest {

	private SmartRoad<RSU<?>> road;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		BaseRSU rsu = new BaseRSU("a", WAY.EAST, 1);
		RSU<Double> dRsu = new DistanceRSU(rsu, 50);
		RSU<Boolean> bRSU = new HumanRSU(rsu, false);
		List<RSU<?>> rsus = new ArrayList<>();
		rsus.add(dRsu);
		rsus.add(bRSU);
		SmartRoad<RSU<?>> sroad = new SmartRoad<>(
				new Road(
						"b", 
						Collections.emptyList()), 
				rsus);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		road = null;
	}

	/**
	 * Test method for {@link sm.arg.intersection.SmartRoad#getRsus()}.
	 */
	@Test
	public final void testGetRsus() {
		assertEquals(50.0, road.getRsus().get(0).getMeasurement(), 0.1);
	}

}

/**
 * 
 */
package sm.intersection;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sm.arg.intersection.DistanceRSU;
import sm.intersection.BaseRSU;
import sm.intersection.HumanRSU;
import sm.intersection.RSU;
import sm.intersection.Road;
import sm.intersection.SmartRoad;

/**
 * @author sm
 *
 */
public class SmartRoadTest {

	private SmartRoad road;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		BaseRSU rsu = new BaseRSU("a", 1);
		RSU<Double> dRsu = new DistanceRSU(rsu, 50);
		RSU<Boolean> bRSU = new HumanRSU(rsu, false);
		List<RSU<?>> rsus = new ArrayList<>();
		rsus.add(dRsu);
		rsus.add(bRSU);
		road = new SmartRoad(new Road("b", Collections.emptyList()), rsus);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		road = null;
	}

	/**
	 * Test method for {@link sm.intersection.SmartRoad#getRsus()}.
	 */
	@Test
	public final void testGetRsus() {
		for (RSU<?> rsu : road.getRsus()) {
			if (rsu.getType().isAssignableFrom(Double.class)) {
				Double d = rsu.getMeasurement();
				assertEquals(50.0, d, 0.1);
			} else if (rsu.getType().isAssignableFrom(Boolean.class)) {
				Boolean b = rsu.getMeasurement();
				assertEquals(false, b);
			}
		}
	}

}

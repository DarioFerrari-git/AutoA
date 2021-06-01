/**
 * 
 */
package sm.arg.intersection;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author sm
 *
 */
public class FourWaysJunctionConfigTest {
	
	private FourWaysJunctionConfig fourWays;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		fourWays = new FourWaysJunctionConfig(
				"4ways", 
				new NumArgsPolicy(), 
				new DistanceRSU(
						new BaseRSU(
								"rsu", 
								1), 
						50));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		fourWays = null;
	}

	/**
	 * Test method for {@link sm.arg.intersection.FourWaysJunctionConfig#FourWaysJunctionConfig(java.lang.String, sm.arg.intersection.CrossingPolicy, sm.arg.intersection.RSU<?>[])}.
	 */
	@Test
	public final void testFourWaysJunctionConfig() {
		assertEquals(4, fourWays.getJunction().ways().size());
		assertEquals(4, fourWays.getJunction().nRoads());
		for (WAY way : WAY.values()) {
			assertTrue(fourWays.getJunction().ways().contains(way));
			assertTrue(fourWays.getJunction().getRoads().containsKey(way));
			assertEquals(1, fourWays.getJunction().getRoads().get(way).nRsus());
			assertEquals(3, fourWays.getJunction().getRoads().get(way).getRoad().nLanes());
			for (DIRECTION d : DIRECTION.values()) {
				assertTrue(fourWays.getJunction().getRoads().get(way).getRoad().getLanes().contains(d));
			}
			for (RSU<?> rsu : fourWays.getJunction().getRoads().get(way).getRsus()) {
				assertTrue(rsu instanceof DistanceRSU);
				assertTrue(rsu.getType().isAssignableFrom(Double.class));
			}
		}
	}

	/**
	 * Test method for {@link sm.arg.intersection.FourWaysJunctionConfig#addCar(sm.arg.intersection.UrgentCar, java.lang.String)}.
	 */
	@Test
	public final void testAddCar() {
		assertTrue(fourWays.getCars().isEmpty());
	}

}

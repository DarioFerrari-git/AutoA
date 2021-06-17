/**
 * 
 */
package sm.arg.intersection;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sm.intersection.BaseRSU;
import sm.intersection.Car;
import sm.intersection.DIRECTION;
import sm.intersection.RSU;
import sm.intersection.UrgentCar;
import sm.intersection.WAY;

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
	 * Test method for {@link sm.arg.intersection.FourWaysJunctionConfig#addCar(sm.intersection.UrgentCar, java.lang.String)}.
	 */
	@Test
	public final void testAddCar() {
		assertTrue(fourWays.getCars().isEmpty());
		fourWays.addCar(new UrgentCar(new Car("car1", 50), 0.9), WAY.NORTH.toString());
		assertEquals(1, fourWays.getCars().size());
		assertEquals(WAY.NORTH, fourWays.getCars().get(0).getWay());
		assertEquals(50, fourWays.getCars().get(0).getDistance(), 0.1);
		fourWays.addCar(new UrgentCar(new Car("car2", 50), 0.9), WAY.EAST.toString());
		assertEquals(2, fourWays.getCars().size());
		assertEquals(WAY.EAST, fourWays.getCars().get(1).getWay());
		assertEquals(50, fourWays.getCars().get(1).getDistance(), 0.1);
		fourWays.addCar(new UrgentCar(new Car("car3", 50), 0.9), WAY.WEST.toString());
		assertEquals(3, fourWays.getCars().size());
		assertEquals(WAY.WEST, fourWays.getCars().get(2).getWay());
		assertEquals(50, fourWays.getCars().get(2).getDistance(), 0.1);
		fourWays.addCar(new UrgentCar(new Car("car4", 50), 0.9), WAY.SOUTH.toString());
		assertEquals(4, fourWays.getCars().size());
		assertEquals(WAY.SOUTH, fourWays.getCars().get(3).getWay());
		assertEquals(50, fourWays.getCars().get(3).getDistance(), 0.1);
		List<WAY> ways = fourWays.getCars().stream().map(c -> c.getWay()).collect(Collectors.toList());
		for (WAY way : WAY.values()) {
			 assertTrue(ways.contains(way));
		}
	}

}

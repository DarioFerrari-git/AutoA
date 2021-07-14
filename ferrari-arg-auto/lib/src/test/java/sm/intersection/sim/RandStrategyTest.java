/**
 * 
 */
package sm.intersection.sim;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sm.arg.intersection.CrossingCar;
import sm.arg.intersection.DistanceRSU;
import sm.arg.intersection.FourWaysJunctionConfig;
import sm.arg.intersection.NumArgsPolicy;
import sm.intersection.BaseRSU;

/**
 * @author sm
 *
 */
public class RandStrategyTest {
	
	private static final int ITER = 1000;
	private RandStrategy strat;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.strat = new RandStrategy();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		this.strat = null;
	}

	/**
	 * Test method for {@link sm.intersection.sim.RandStrategy#configJunction(sm.intersection.SmartJunction)}.
	 */
	@Test
	public final void testConfigJunction() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link sm.intersection.sim.RandStrategy#newCar()}.
	 */
	@Test
	public final void testNewCar() {
		FourWaysJunctionConfig config = new FourWaysJunctionConfig("junction1", new NumArgsPolicy("numArgsPolicy1"),
				new DistanceRSU(new BaseRSU("distance1", 1), 100));
		this.strat.configJunction(config.getJunction(), 1);
		CrossingCar car;
		List<CrossingCar> cars = new ArrayList<>();
		for (int i = 0; i < RandStrategyTest.ITER; i++) {
			car = this.strat.newCar();
			System.out.println(car);
			assertTrue(this.strat.getJunction().getRoads().containsKey(car.getWay()));
			assertTrue(this.strat.getJunction().getRoads().get(car.getWay()) != null);
			for (CrossingCar otherCar : cars) {
				assertNotEquals(car, otherCar);
			}
			cars.add(car);
			assertEquals(this.strat.getnCars(), cars.size());
		}
	}

}

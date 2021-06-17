/**
 * 
 */
package sm.intersection.sim;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sm.arg.intersection.DistanceRSU;
import sm.arg.intersection.FourWaysJunctionConfig;
import sm.arg.intersection.NumArgsPolicy;
import sm.intersection.BaseRSU;
import sm.intersection.Car;
import sm.intersection.UrgentCar;
import sm.intersection.WAY;

/**
 * @author sm
 *
 */
public class SimulationTest {

	private Simulation simulation;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		FourWaysJunctionConfig config = new FourWaysJunctionConfig("junction1", new NumArgsPolicy("numArgsPolicy1"),
				new DistanceRSU(new BaseRSU("distance1", 1), 100));
		config.addCar(new UrgentCar(new Car("car1", 50), 0), WAY.NORTH.toString());
		this.simulation = new Simulation(config.getJunction(), config.getCars());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		this.simulation = null;
	}

	/**
	 * Test method for {@link sm.intersection.sim.Simulation#step()}.
	 */
	@Test
	public final void testStep() {
		
	}

	/**
	 * Test method for {@link sm.intersection.sim.Simulation#go()}.
	 */
	@Test
	public final void testGo() {
		
	}

	/**
	 * Test method for {@link sm.intersection.sim.Simulation#pause()}.
	 */
	@Test
	public final void testPause() {
		
	}

	/**
	 * Test method for {@link sm.intersection.sim.Simulation#logSituation()}.
	 */
	@Test
	public final void testLogSituation() {
		this.simulation.logSituation();
	}

}

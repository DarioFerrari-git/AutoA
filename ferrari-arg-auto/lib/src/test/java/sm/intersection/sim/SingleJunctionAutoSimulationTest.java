/**
 * 
 */
package sm.intersection.sim;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sm.arg.intersection.DistanceRSU;
import sm.arg.intersection.FourWaysJunctionConfig;
import sm.arg.intersection.NumArgsPolicy;
import sm.intersection.BaseRSU;

/**
 * @author sm
 *
 */
public class SingleJunctionAutoSimulationTest {
	
	private SingleJunctionAutoSimulation sim;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		FourWaysJunctionConfig config = new FourWaysJunctionConfig("junction1", new NumArgsPolicy("numArgsPolicy1"),
				new DistanceRSU(new BaseRSU("distance1", 1), 100));
		VehiclesGenStrategy strat = new RandStrategy();
		strat.configJunction(config.getJunction());
		this.sim = new SingleJunctionAutoSimulation(config.getJunction(), 1, 10, strat);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		this.sim = null;
	}

	/**F
	 * Test method for {@link sm.intersection.sim.SingleJunctionAutoSimulation#step(java.lang.Boolean)}.
	 */
	@Test
	public final void testStep() {
		this.sim.step(true);
	}

	/**
	 * Test method for {@link sm.intersection.sim.SingleJunctionAutoSimulation#go(java.lang.Boolean)}.
	 */
	@Test
	public final void testGo() {
		this.sim.go(true);
	}

}

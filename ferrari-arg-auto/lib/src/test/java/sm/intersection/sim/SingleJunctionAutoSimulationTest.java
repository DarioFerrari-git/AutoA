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
	
	private SingleJunctionAutoSimulation sim1;
	private SingleJunctionAutoSimulation sim2;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		FourWaysJunctionConfig config = new FourWaysJunctionConfig("junction1", new NumArgsPolicy("numArgsPolicy1"),
				new DistanceRSU(new BaseRSU("distance1", 1), 100));
		VehiclesGenStrategy strat = new RandStrategy();
		strat.configJunction(config.getJunction());
		this.sim1 = new SingleJunctionAutoSimulation(config.getJunction(), 1, 10, 10, strat);
		this.sim2 = new SingleJunctionAutoSimulation(config.getJunction(), 3, 3, 10, strat);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		this.sim1 = null;
		this.sim2 = null;
	}

	/**F
	 * Test method for {@link sm.intersection.sim.SingleJunctionAutoSimulation#step(java.lang.Boolean)}.
	 */
	@Test
	public final void testStep() {
		this.sim1.step(true);
		this.sim2.step(true);
	}

	/**
	 * Test method for {@link sm.intersection.sim.SingleJunctionAutoSimulation#go(java.lang.Boolean)}.
	 */
	@Test
	public final void testGo() {
		this.sim1.go(true);
		this.sim2.go(true);
	}

}

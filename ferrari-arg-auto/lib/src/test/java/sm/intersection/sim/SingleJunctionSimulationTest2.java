/**
 * 
 */
package sm.intersection.sim;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sm.arg.intersection.DistanceRSU;
import sm.arg.intersection.FourWaysJunctionConfig;
import sm.arg.intersection.NumArgsPolicy;
import sm.intersection.BaseRSU;
import sm.intersection.Car;
import sm.intersection.DIRECTION;
import sm.intersection.UrgentCar;
import sm.intersection.WAY;

/**
 * @author sm
 *
 */
public class SingleJunctionSimulationTest2 {

	private SingleJunctionSimulation simulation;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		FourWaysJunctionConfig config = new FourWaysJunctionConfig("junction1", new NumArgsPolicy("numArgsPolicy1"),
				new DistanceRSU(new BaseRSU("distance1", 1), 100));
		UrgentCar car = new UrgentCar(new Car("car1", 50), 0);
		car.getCar().addRoute(Collections.singletonList(DIRECTION.STRAIGHT));
		config.addCar(car, WAY.NORTH.toString());
		this.simulation = new SingleJunctionSimulation(config.getJunction(), config.getCars(), 1);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		this.simulation = null;
	}

	/**
	 * Test method for {@link sm.intersection.sim.SingleJunctionSimulation#step()}.
	 */
	@Test
	public final void testStep() {
		this.simulation.step(true/*, 1*/);
	}

	/**
	 * Test method for {@link sm.intersection.sim.SingleJunctionSimulation#go()}.
	 */
	@Test
	public final void testGo() {
		this.simulation.go(true);
	}

	/**
	 * Test method for {@link sm.intersection.sim.SingleJunctionSimulation#pause()}.
	 */
	@Test
	public final void testPause() {
		assertFalse(this.simulation.isGoing());
		this.simulation.pause();
		assertFalse(this.simulation.isGoing());
		this.simulation.step(true/*, 1*/);
		assertFalse(this.simulation.isGoing());
		this.simulation.pause();
		assertFalse(this.simulation.isGoing());
		this.simulation.step(true/*, 2*/);
		assertFalse(this.simulation.isGoing());
		this.simulation.pause();
		assertFalse(this.simulation.isGoing());
		this.simulation.go(true);
		assertTrue(this.simulation.isGoing());
		this.simulation.go(true);
		assertTrue(this.simulation.isGoing());
		this.simulation.step(true/*, 1*/);
		assertTrue(this.simulation.isGoing());
		this.simulation.pause();
		assertFalse(this.simulation.isGoing());
		this.simulation.step(true/*, 1*/);
		assertFalse(this.simulation.isGoing());
		this.simulation.step(true/*, 0*/);
	}

	/**
	 * Test method for {@link sm.intersection.sim.SingleJunctionSimulation#logSituation()}.
	 */
	@Test
	public final void testLogSituation() {
		this.simulation.logSituation();
	}

}

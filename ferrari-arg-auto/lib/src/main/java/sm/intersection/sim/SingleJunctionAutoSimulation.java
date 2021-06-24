/**
 * 
 */
package sm.intersection.sim;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sm.intersection.SmartJunction;

/**
 * @author sm
 *
 */
public final class SingleJunctionAutoSimulation extends SingleJunctionSimulation {
	
	private final Logger log = LoggerFactory.getLogger(SingleJunctionAutoSimulation.class);
//	private final SingleJunctionSimulation sim;
	private final double vXs;
	private final long maxSteps;
	private final VehiclesGenStrategy gen;

	public SingleJunctionAutoSimulation(final SmartJunction junction, double vehiclesPerSecond, long maxSteps,
			final VehiclesGenStrategy strategy) {
		super(junction, Collections.emptyList());
		this.vXs = vehiclesPerSecond;
		this.maxSteps = maxSteps;
		this.gen = strategy;
	}

	@Override
	public void step(Boolean log) {
		for (int i = 0; i < this.vXs; i++) {
			this.addCar(gen.newCar());
		}
		super.step(log);
	}

	@Override
	public void go(Boolean log) {
		if (super.getSteps() < this.maxSteps) {
			super.go(log); // TODO check which go() is called: this, or superclass
		} else {
			this.log.warn("MAXIMUM STEPS REACHED: {}", this.maxSteps);
			super.pause();
		}
	}

}

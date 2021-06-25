/**
 * 
 */
package sm.intersection.sim;

import java.util.ArrayList;

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
	private final long genSteps;
	private final long maxSteps;
	private final VehiclesGenStrategy gen;

	public SingleJunctionAutoSimulation(final SmartJunction junction, int vehiclesPerSecond, long genSteps,
			long maxSteps, final VehiclesGenStrategy strategy) {
		super(junction, new ArrayList<>());
		this.vXs = vehiclesPerSecond;
		this.genSteps = genSteps;
		this.maxSteps = maxSteps;
		this.gen = strategy;
	}

	@Override
	public void step(Boolean log) {
		if (super.getSteps() < this.genSteps) {
			for (int i = 0; i < this.vXs; i++) {
				this.addCar(gen.newCar());
			}
		}
		super.step(log);
	}

	@Override
	public void go(Boolean log) {
		this.step(log); // to avoid premature termination of super.go() due to cars being empty
		while (super.getSteps() < this.maxSteps && !super.getCars().isEmpty()) {
			this.step(log);
//			super.go(log); // check which step() is called therein: this, or superclass
		}
		if (super.getSteps() >= this.maxSteps) {
			this.log.warn("MAXIMUM STEPS REACHED: {}", this.maxSteps);
		} else {
			this.log.warn("ALL CARS SERVED");
		}
	}

}

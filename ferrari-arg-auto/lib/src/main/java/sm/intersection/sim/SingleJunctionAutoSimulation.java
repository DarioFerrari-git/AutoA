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
    private final double gXs;
    private final long genSteps;
    private final long maxSteps;
    private final VehiclesGenStrategy gen;

    /**
     * 
     * @param junction
     * @param genPerSecond
     * @param genSteps
     * @param maxSteps
     * @param strategy
     * @param simStep
     */
    public SingleJunctionAutoSimulation(final SmartJunction junction, final int genPerSecond, final long genSteps,
            final long maxSteps, final VehiclesGenStrategy strategy, double simStep) {
        super(junction, new ArrayList<>(), simStep);
        this.gXs = genPerSecond;
        this.genSteps = genSteps;
        this.maxSteps = maxSteps;
        this.gen = strategy;
    }

    @Override
    public void step(final Boolean log) {
        if (!this.going) {
            if (super.getSteps() >= this.maxSteps) {
                this.log.warn("MAXIMUM STEPS REACHED: {}", this.maxSteps);
            } else {
                if (super.getSteps() < this.genSteps) {
                    for (int i = 0; i < this.gXs; i++) {
                        this.addCars(this.gen.newCars());
                    }
                }
                super.step(log);
            }
        } else {
            this.log.warn("SIMULATION GOING, PAUSE IT FIRST");
        }
    }

    @Override
    public void go(final Boolean log) {
        if (!this.going) {
            this.step(log); // to avoid premature termination of super.go() due to cars being empty
            while (super.getSteps() < this.maxSteps && !super.getCars().isEmpty()) {
                this.going = false;
                this.step(log);
                this.going = true;
                //			super.go(log); // check which step() is called therein: this, or superclass
            }
            if (super.getSteps() >= this.maxSteps) {
                this.log.warn("MAXIMUM STEPS REACHED: {}", this.maxSteps);
            } else {
                this.log.warn("ALL CARS SERVED");
            }
        } else {
            this.log.warn("SIMULATION ALREADY GOING");
        }
    }

}

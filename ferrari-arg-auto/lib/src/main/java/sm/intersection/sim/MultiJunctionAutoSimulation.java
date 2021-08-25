/**
 * 
 */
package sm.intersection.sim;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sm.arg.intersection.CrossingCar;
import sm.intersection.JunctionMatrix;

/**
 * @author sm
 *
 */
public final class MultiJunctionAutoSimulation extends MultiJunctionSimulation {

    private final Logger log = LoggerFactory.getLogger(MultiJunctionAutoSimulation.class);
    private final long maxSteps;
    
    /**
     * 
     * @param network
     * @param simulations MUST HAVE SAME STEP, SAME MAX STEPS
     */
    public MultiJunctionAutoSimulation(JunctionMatrix network, List<Simulation> simulations) {
        super(network, simulations);
        this.maxSteps = simulations.get(0).getMaxSteps();
    }
    
    @Override
    public List<CrossingCar> step(Boolean log) {
        if (!this.going) {
            if (super.getSteps() >= this.maxSteps) {
                this.log.warn("MAXIMUM STEPS REACHED: {}", this.maxSteps);
            } else {
                /*if (super.getSteps() < this.genSteps) {
                    for (int i = 0; i < this.gXs; i++) {
                        this.addCars(this.gen.newCars());
                    }
                }*/
                return super.step(log);
            }
        } else {
            this.log.warn("SIMULATION GOING, PAUSE IT FIRST");
        }
        return null;
    }
    
    @Override
    public void go(Boolean log) {
        if (!this.going) {
            this.step(log); // to avoid premature termination of super.go() due to cars being empty
            while (super.getSteps() < this.maxSteps && !super.getCars().isEmpty()) {
                this.going = false;
                this.step(log);
                this.going = true;
                //          super.go(log); // check which step() is called therein: this, or superclass
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

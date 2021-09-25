/**
 * 
 */
package sm.intersection.sim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sm.arg.intersection.CrossingCar;
import sm.intersection.JunctionMatrix;
import sm.intersection.SmartJunction;

/**
 * @author sm
 *
 */
public class MultiJunctionSimulation implements Simulation {

    private final Logger log = LoggerFactory.getLogger(MultiJunctionSimulation.class);
    protected final JunctionMatrix network;
    protected final Map<String, Simulation> simXnames;
    private final double step;
    private long steps;
    protected boolean going;
    protected int nLeftNet=0;
    protected int nArrived=0;
    protected Set<CrossingCar> generated;
	protected long start;
    /**
     * @param network
     * @param simulations MUST HAVE SAME STEP
     */
    public MultiJunctionSimulation(JunctionMatrix network, List<Simulation> simulations) {
        this.network = network;
        this.simXnames = new HashMap<>();
        for (Simulation ssim : simulations) {
            this.simXnames.put(ssim.getJunctions().get(0).getName(), ssim);
        }
        this.step = simulations.get(0).getStep();
        this.steps = 0;
        this.going = false;
        this.generated = new HashSet<>();
    }

    public List<CrossingCar> step(final Boolean log) {
        List<CrossingCar> outOfSim = new ArrayList<>();
        if(this.steps==0) start= System.currentTimeMillis();
        
        if (!this.going) {
            this.steps++;
            Map<Simulation, List<CrossingCar>> leaving = new HashMap<>();
            Optional<SmartJunction> next;
            /*
             * For each simulation (junction), get served cars (removed from junction)...
             */
            for (Simulation ssim : simXnames.values()) {
                leaving.put(ssim, ssim.step(log));
            }
            /*
             * ...for each of those cars, put it in next junction (simulation)
             */
            String curJname;
            for (Simulation ssim : leaving.keySet()) {
                for (CrossingCar car : leaving.get(ssim)) {
                    this.generated.add(car);
                    if (car.getCurrentRoutePath().size() > 1) { // TODO adapt to breadth (# alternatives) and depth (# of directions) of routes...HOW??
                        curJname = ssim.getJunctions().get(0).getName();
                        next = this.network.next(this.network.getJunction(curJname).get(), car.getWay(),
                                car.getCurrentRoutePath().get(0)); // TODO adapt to breadth (# alternatives) and depth (# of directions) of routes...HOW??
                        if (next.isPresent()) {
                            this.simXnames.get(next.get().getName())
                                    .addCars(Collections.singletonList(car.updateAfterCrossing(next.get())));
                            this.log.info("<{}> ===<{}≥===> <{}> ({}, {}, {}, {})", curJname, car.getName(),
                                    next.get().getName(), car.getState(), car.getWay(), car.getLane(),
                                    car.getDistance());
                        } else {
                            this.log.info("<{}> leaving network ({}, {})", car.getName(), car.getState(),
                                    car.getDistance());
                            nLeftNet++;
                            outOfSim.add(car);
                        }
                    } else {
                        this.log.info("<{}> finished its route ({}, {})", car.getName(), car.getState(),
                                car.getDistance());
                        nArrived++;
                        outOfSim.add(car);
                    }
                }
            }
        } else {
            this.log.warn("SIMULATION GOING, PAUSE IT FIRST");
        }
        this.log.info("simulation time after Step {}: {} millis",this.steps,System.currentTimeMillis()-this.start);
        
        return outOfSim;
    }

    @Override
    public void go(Boolean log) {
        boolean notEmpty = false;
        if (!this.going) {
            for (Simulation sim : this.simXnames.values()) {
                if (!sim.getCars().isEmpty()) {
                    notEmpty = true;
                    break;
                }
            }
            while (notEmpty) {
                this.step(log, /* 1, */ true);
            }
        } else {
            this.log.warn("SIMULATION ALREADY GOING");
        }
    }
    
    private void step(final Boolean log, /* final long steps, */ final Boolean bypass) {
        if (bypass) {
            this.going = false;
            this.step(log/* , steps */);
            this.going = true;
        }
    }

    @Override
    public void pause() {
        if (this.going) {
            this.going = false;
        } else {
            this.log.warn("SIMULATION ALREADY PAUSED");
        }
    }

    @Override
    public void logSituation() {
        this.log.warn("Logging situation of a MultiJunctionSimulation relies on logging provided by each SingleJunctionSimulation");
        throw new UnsupportedOperationException("Logging situation of a MultiJunctionSimulation relies on logging provided by each SingleJunctionSimulation");
    }

    @Override
    public boolean isGoing() {
        return this.going;
    }

    @Override
    public List<SmartJunction> getJunctions() {
        List<SmartJunction> junctions = new ArrayList<>();
        for (int i = 0; i < this.network.nRows(); i++) {
            for (int j = 0; j < this.network.nCols(); j++) {
                junctions.add(this.network.getJunction(i, j));
            }
        }
        return junctions;
    }

    @Override
    public List<CrossingCar> getCars() {
        List<CrossingCar> cars = new ArrayList<>();
        for (Simulation ssim : simXnames.values()) {
            cars.addAll(ssim.getCars());
        }
        return cars;
    }

    @Override
    public Simulation addCars(List<CrossingCar> cars) {
        this.log.warn("Adding cars to a MultiJunctionSimulation relies on addition provided by each SingleJunctionSimulation");
        throw new UnsupportedOperationException("Adding cars to a MultiJunctionSimulation relies on addition provided by each SingleJunctionSimulation");
    }

    @Override
    public double getStep() {
        return this.step;
    }

    @Override
    public long getSteps() {
        return this.steps;
    }

    @Override
    public long getMaxSteps() {
        this.log.warn("A MultiJunctionSimulation does not have a maximum number of steps (its Auto- version does)");
        throw new UnsupportedOperationException("A MultiJunctionSimulation does not have a maximum number of steps (its Auto- version does)");
    }

}

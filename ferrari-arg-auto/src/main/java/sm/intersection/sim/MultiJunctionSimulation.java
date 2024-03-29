/**
 *
 */
package sm.intersection.sim;

import java.io.FileWriter;
import java.io.IOException;
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

import com.opencsv.CSVWriter;

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
    protected int nLeftNet = 0;
    protected int nArrived = 0;
    protected Set<CrossingCar> generated;
    protected long start;
    protected CSVWriter writer;
    private long nWaiting = 0;

    /**
     * @param network
     * @param simulations MUST HAVE SAME STEP
     */
    public MultiJunctionSimulation(final JunctionMatrix network, final List<Simulation> simulations,
            final String performancePath) {
        this.network = network;
        this.simXnames = new HashMap<>();
        for (final Simulation ssim : simulations) {
            this.simXnames.put(ssim.getJunctions().get(0).getName(), ssim);
        }
        this.step = simulations.get(0).getStep();
        this.steps = 0;
        this.going = false;
        this.generated = new HashSet<>();
        try {
            this.writer = new CSVWriter(new FileWriter(performancePath));
            writer.writeNext(new String[] { "simulation_step", "simulation_time", "vehicles", "crossings", "waitings", 
                    "alternative_routes_used" });
        } catch (IOException e) {
            this.log.warn("<CSVWriter> NOT READY");
            e.printStackTrace();
        }
    }

    @Override
    public List<CrossingCar> step(final Boolean log) {
        final List<CrossingCar> outOfSim = new ArrayList<>();
        if (this.steps == 0) {
            this.start = System.currentTimeMillis();
        }

        if (!this.going) {
            this.steps++;
            final Map<Simulation, List<CrossingCar>> leaving = new HashMap<>();
            Optional<SmartJunction> next;
            /*
             * For each simulation (junction), get served cars (removed from junction)...
             */
            for (final Simulation ssim : this.simXnames.values()) {
                leaving.put(ssim, ssim.step(log));
            }
            /*
             * ...for each of those cars, put it in next junction (simulation)
             */
            String curJname;
            for (final Simulation ssim : leaving.keySet()) {
                for (final CrossingCar car : leaving.get(ssim)) {
                    this.generated.add(car);
                    if (car.getCurrentRoutePath().size() > 1) {
                        curJname = ssim.getJunctions().get(0).getName();
                        next = this.network.next(this.network.getJunction(curJname).get(), car.getWay(),
                                car.getCurrentRoutePath().get(0));
                        if (next.isPresent()) {
                            this.simXnames.get(next.get().getName())
                                    .addCars(Collections.singletonList(car.updateAfterCrossing(next.get())));
                            if (log) {
                                this.log.info("<{}> ===<{}≥===> <{}> ({}, {}, {}, {})", curJname, car.getName(),
                                        next.get().getName(), car.getState(), car.getWay(), car.getLane(),
                                        car.getDistance());
                            }
                        } else {
                            if (log) {
                                this.log.info("<{}> leaving network ({}, {})", car.getName(), car.getState(),
                                        car.getDistance());
                            }
                            this.nLeftNet++;
                            outOfSim.add(car);
                        }
                    } else {
                        if (log) {
                            this.log.info("<{}> finished its route ({}, {})", car.getName(), car.getState(),
                                    car.getDistance());
                        }
                        this.nArrived++;
                        outOfSim.add(car);
                    }
                }
            }
        } else {
            this.log.warn("SIMULATION GOING, PAUSE IT FIRST");
        }
        int nServed = 0;
        int nArgProc = 0;
        int nAltRoutesUsed = 0;
        for (int i = 0; i < this.network.nRows(); i++) {
            for (int j = 0; j < this.network.nCols(); j++) {
                nServed += this.network.getJunction(i, j).getServed();
                nArgProc += this.network.getJunction(i, j).getArgProc();
                nAltRoutesUsed += this.network.getJunction(i, j).getAltRoutesUsed();
                this.nWaiting += this.simXnames.get(this.network.getJunction(i, j).getName()).getNWaiting();;
            }
        }
        if (log) {
            this.log.info("##### PERFORMANCE (step {}) #####", this.steps);
            this.log.info("{} crossings happened", nServed);
            this.log.info("{} argumentation processes done", nArgProc);
            this.log.info("Simulation time: {} millis", System.currentTimeMillis() - this.start);
            this.log.info("{} argumentation processes per second",
                    (double) nArgProc * 1000 / (System.currentTimeMillis() - this.start));
            this.log.info("{} alternative routes adopted", nAltRoutesUsed);
            this.log.info("##### #####");
        }
        if (this.writer != null) {
            writer.writeNext(new String[] { String.valueOf(this.steps),
                    String.valueOf(System.currentTimeMillis() - this.start), String.valueOf(this.generated.size()),
                    String.valueOf(nServed), String.valueOf(this.nWaiting), String.valueOf(nAltRoutesUsed) });
        }
        return outOfSim;
    }

    @Override
    public void go(final Boolean log) {
        boolean notEmpty = false;
        if (!this.going) {
            for (final Simulation sim : this.simXnames.values()) {
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
        if (this.writer != null) {
            try {
                this.writer.close();
            } catch (IOException e) {
                this.log.warn("<CSVWriter> NOT CLOSED");
                e.printStackTrace();
            }
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
        this.log.warn(
                "Logging situation of a MultiJunctionSimulation relies on logging provided by each SingleJunctionSimulation");
        throw new UnsupportedOperationException(
                "Logging situation of a MultiJunctionSimulation relies on logging provided by each SingleJunctionSimulation");
    }

    @Override
    public boolean isGoing() {
        return this.going;
    }

    @Override
    public List<SmartJunction> getJunctions() {
        final List<SmartJunction> junctions = new ArrayList<>();
        for (int i = 0; i < this.network.nRows(); i++) {
            for (int j = 0; j < this.network.nCols(); j++) {
                junctions.add(this.network.getJunction(i, j));
            }
        }
        return junctions;
    }

    @Override
    public List<CrossingCar> getCars() {
        final List<CrossingCar> cars = new ArrayList<>();
        for (final Simulation ssim : this.simXnames.values()) {
            cars.addAll(ssim.getCars());
        }
        return cars;
    }

    @Override
    public Simulation addCars(final List<CrossingCar> cars) {
        this.log.warn(
                "Adding cars to a MultiJunctionSimulation relies on addition provided by each SingleJunctionSimulation");
        throw new UnsupportedOperationException(
                "Adding cars to a MultiJunctionSimulation relies on addition provided by each SingleJunctionSimulation");
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
        throw new UnsupportedOperationException(
                "A MultiJunctionSimulation does not have a maximum number of steps (its Auto- version does)");
    }
    
    @Override
    public long getNWaiting() {
        return this.nWaiting ;
    }

}

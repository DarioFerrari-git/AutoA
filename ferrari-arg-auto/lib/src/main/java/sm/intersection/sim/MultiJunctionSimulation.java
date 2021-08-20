/**
 * 
 */
package sm.intersection.sim;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sm.arg.intersection.CrossingCar;
import sm.intersection.JunctionMatrix;
import sm.intersection.SmartJunction;

/**
 * @author sm
 *
 */
public final class MultiJunctionSimulation {

    private final Logger log = LoggerFactory.getLogger(MultiJunctionSimulation.class);
    private final JunctionMatrix network;
    private final Map<String, SingleJunctionSimulation> simXnames;

    /**
     * @param network
     * @param simulations
     */
    public MultiJunctionSimulation(JunctionMatrix network, List<SingleJunctionSimulation> simulations) {
        this.network = network;
        this.simXnames = new HashMap<>();
        for (SingleJunctionSimulation ssim : simulations) {
            this.simXnames.put(ssim.getJunction().getName(), ssim);
        }
    }

    public void step(final Boolean log) {
        Map<SingleJunctionSimulation, List<CrossingCar>> leaving = new HashMap<>();
        Optional<SmartJunction> next;
        /*
         * For each simulation (junction), get served cars (removed from junction)...
         */
        for (SingleJunctionSimulation ssim : simXnames.values()) {
            leaving.put(ssim, ssim.step(log));
        }
        /*
         * ...for each of those cars, put it in next junction (simulation)
         */
        String curJname;
        for (SingleJunctionSimulation ssim : leaving.keySet()) {
            for (CrossingCar car : leaving.get(ssim)) {
                if (car.getRoutes().get(0).size() > 1) { // TODO adapt to breadth (# alternatives) and depth (# of directions) of routes...HOW??
                    curJname = ssim.getJunction().getName();
                    next = this.network.next(this.network.getJunction(curJname).get(), car.getWay(),
                            car.getRoutes().get(0).get(0)); // TODO adapt to breadth (# alternatives) and depth (# of directions) of routes...HOW??
                    if (next.isPresent()) {
                        this.simXnames.get(next.get().getName())
                                .addCars(Collections.singletonList(car.updateAfterCrossing(next.get())));
                        this.log.info("<{}> ===<{}≥===> <{}> ({}, {}, {}, {})", curJname, car.getName(), next.get().getName(), car.getState(), car.getWay(), car.getLane(), car.getDistance());
                    } else {
                        this.log.info("<{}> leaving network ({}, {})", car.getName(), car.getState(), car.getDistance());
                    }
                } else {
                    this.log.info("<{}> finished its route ({}, {})", car.getName(), car.getState(), car.getDistance());
                }
            }
        }
    }

}

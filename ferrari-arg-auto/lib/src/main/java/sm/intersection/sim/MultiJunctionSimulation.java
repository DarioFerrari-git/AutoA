/**
 * 
 */
package sm.intersection.sim;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import sm.arg.intersection.CrossingCar;
import sm.intersection.JunctionMatrix;
import sm.intersection.SmartJunction;

/**
 * @author sm
 *
 */
public final class MultiJunctionSimulation {

    //    private final Logger log = LoggerFactory.getLogger(MultiJunctionSimulation.class);
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
        for (SingleJunctionSimulation ssim : leaving.keySet()) {
            for (CrossingCar car : leaving.get(ssim)) {
                if (car.getRoutes().get(0).size() > 0) {
                    next = this.network.next(this.network.getJunction(ssim.getJunction().getName()).get(), car.getWay(),
                            car.getRoutes().get(0).get(0)); // TODO adapt to breadth (# aternatives) and depth (# of directions) of routes...HOW??
                    if (next.isPresent()) {
                        this.simXnames.get(next.get().getName())
                                .addCars(Collections.singletonList(car.updateAfterCrossing(next.get())));
                    }
                }
            }
        }
    }

}

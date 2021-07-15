/**
 *
 */
package sm.intersection.sim;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sm.arg.intersection.CrossingCar;
import sm.arg.intersection.DistanceRSU;
import sm.intersection.Car;
import sm.intersection.DIRECTION;
import sm.intersection.RSU;
import sm.intersection.STATUS;
import sm.intersection.SmartJunction;
import sm.intersection.UrgentCar;
import sm.intersection.WAY;

/**
 * Random ways and routes, fixed speed and urgency (MAX), single 1-step route. -
 * WAY is random - speed is 50 - urgency is 1 - 1 route only, with 1 random
 * DIRECTION only
 * 
 * @author sm
 *
 */
public class FlatRouteMaxStrategy implements VehiclesGenStrategy {

    private final Logger log = LoggerFactory.getLogger(FlatRouteMaxStrategy.class);
    private SmartJunction junction;
    private boolean setup = false;

    private List<WAY> values;
    private int size;
    private Random random;
    private long seed;
    private boolean seedSet;

    private long nCars;

    @Override
    public List<CrossingCar> newCars() {
        if (this.setup) {
            if (!this.seedSet) {
                this.log.warn("SEED NOT SET, USING NON-REPRODUCIBLE STRATEGY");
            }
            final WAY way = this.values.get(this.random.nextInt(this.size));
            Double d = null;
            for (final RSU<?> rsu : this.junction.getRoads().get(way).getRsus()) {
                if (rsu instanceof DistanceRSU && rsu.getType().isAssignableFrom(Double.class)) {
                    d = rsu.getMeasurement();
                } else {
                    this.log.warn("No RSU instanceof DistanceRSU and assignable from Double found: {}",
                            this.junction.getRoads().get(way).getRsus());
                    d = Double.NaN;
                }
            }
            this.nCars++;
            final UrgentCar car = new UrgentCar(new Car(String.format("%s_%d", way, this.nCars), 50), 1);
            car.getCar().addRoute(Collections.singletonList(DIRECTION.random())); // TODO randomize depth and number of routes
            return Collections.singletonList(new CrossingCar(car, way, STATUS.APPROACHING, d));
        } else {
            this.log.warn("REFERENCE JUNCTION NOT YET CONFIGURED");
            return null;
        }
    }

    @Override
    public VehiclesGenStrategy configJunction(final SmartJunction junction) {
        this.junction = junction;
        this.values = List.copyOf(this.junction.getRoads().keySet());
        this.size = this.values.size();
        this.nCars = 0;
        this.random = new Random();
        this.seedSet = false;
        this.setup = true;
        return this;
    }

    @Override
    public VehiclesGenStrategy setSeed(final long seed) {
        this.seed = seed;
        this.seedSet = true;
        this.random = new Random(this.seed);
        DIRECTION.setSeed(this.seed);
        return this;
    }

    /**
     * @return the junction
     */
    public SmartJunction getJunction() {
        return this.junction;
    }

    /**
     * @return the setup
     */
    public boolean isSetup() {
        return this.setup;
    }

    /**
     * @return the nCars
     */
    public long getnCars() {
        return this.nCars;
    }

}

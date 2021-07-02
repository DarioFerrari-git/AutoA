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
 * @author sm
 *
 */
public final class RandStrategy implements VehiclesGenStrategy {

    private final Logger log = LoggerFactory.getLogger(RandStrategy.class);
    private SmartJunction junction;
    private boolean setup = false;

    private List<WAY> values;
    private int size;
    private final Random random = new Random();

    private long nCars;

    @Override
    public VehiclesGenStrategy configJunction(final SmartJunction junction) {
        this.junction = junction;
        this.values = List.copyOf(this.junction.getRoads().keySet());
        this.size = this.values.size();
        this.nCars = 0;
        this.setup = true;
        return this;
    }

    @Override
    public CrossingCar newCar() {
        if (this.setup) {
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
            final UrgentCar car = new UrgentCar(
                    new Car(String.format("%s_%d", way, this.nCars), this.random.nextDouble() * 50), // TODO make speed range configurable
                    this.random.nextDouble()); // TODO make priority range configurable
            car.getCar().addRoute(Collections.singletonList(DIRECTION.random())); // TODO randomize depth and number of routes
            return new CrossingCar(car, way, STATUS.APPROACHING, d);
        } else {
            this.log.warn("REFERENCE JUNCTION NOT YET CONFIGURED");
            return null;
        }
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

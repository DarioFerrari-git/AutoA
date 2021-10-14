package sm.intersection.sim;

import java.util.ArrayList;
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

public class DeepAltRouteConfStrategy implements VehiclesGenStrategy {
    private final Logger log = LoggerFactory.getLogger(DeepAltRouteConfStrategy.class);
    private SmartJunction junction;
    private boolean setup = false;

    private List<WAY> values;
    private int size;
    private Random random;
    private long seed;
    private boolean seedSet = false;

    private long nCars;
    private int minSpeed;
    private int maxSpeed;
    private int minUrgency;
    private int maxUrgency;

    @Override
    public VehiclesGenStrategy configJunction(final SmartJunction junction) {
        this.junction = junction;
        this.values = List.copyOf(this.junction.getRoads().keySet());
        this.size = this.values.size();
        this.nCars = 0;
        if (!this.seedSet) {
            this.random = new Random();
        }
        this.minSpeed = Defaults.MIN_SPEED;
        this.maxSpeed = Defaults.MAX_SPEED;
        this.minUrgency = Defaults.MIN_URGENCY;
        this.maxUrgency = Defaults.MAX_URGENCY;
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

    @Override
    public List<CrossingCar> newCars() {
        if (this.setup) {
            if (!this.seedSet) {
                this.log.warn("SEED NOT SET, USING NON-REPRODUCIBLE STRATEGY");
            }

            final List<CrossingCar> lc = new ArrayList<>();

            final WAY way1 = this.values.get(this.random.nextInt(this.size));
            WAY way2 = null;
            Double d = null;

            for (final RSU<?> rsu : this.junction.getRoads().get(way1).getRsus()) {
                if (rsu instanceof DistanceRSU && rsu.getType().isAssignableFrom(Double.class)) {
                    d = rsu.getMeasurement();
                } else {
                    this.log.warn("No RSU instanceof DistanceRSU and assignable from Double found: {}",
                            this.junction.getRoads().get(way1).getRsus());
                    d = Double.NaN;
                }
            }
            this.nCars++;
            final List<DIRECTION> route1 = new ArrayList<>();
            final List<DIRECTION> route2 = new ArrayList<>();
            final UrgentCar car1 = new UrgentCar(
                    new Car(String.format("%s_%d", way1, this.nCars),
                            this.random.nextDouble() * (this.maxSpeed - this.minSpeed) + this.minSpeed),
                    this.random.nextDouble() * (this.maxUrgency - this.minUrgency) + this.minUrgency);
            route1.add(DIRECTION.random());
            int i = 0;
            while (i < 1) {
                if (route1.get(0).equals(DIRECTION.RIGHT)) {
                    route1.clear();
                    route1.add(DIRECTION.random());
                } else {
                    i++;
                }

            }
            this.nCars++;
            final UrgentCar car2 = new UrgentCar(
                    new Car(String.format("%s_%d", way2, this.nCars),
                            this.random.nextDouble() * (this.maxSpeed - this.minSpeed) + this.minSpeed),
                    this.random.nextDouble() * (this.maxUrgency - this.minUrgency) + this.minUrgency);
            final double alpha = this.random.nextDouble();
            car1.getCar().addRoute(this.deepRoute(route1));
            if (car1.getCar().getRoutes().get(car1.getCar().getCurrentRoute()).get(0).equals(DIRECTION.STRAIGHT)) {

                if (alpha <= 0.25) {
                    if (way1.intValue() + 1 <= 3) {
                        way2 = WAY.VALUES.get(way1.intValue() + 1);
                        route2.add(DIRECTION.STRAIGHT);
                        car2.getCar().addRoute(this.deepRoute(route2));
                    } else {
                        final int v = way1.intValue() + 1 - 4;

                        way2 = WAY.VALUES.get(v);
                        route2.add(DIRECTION.STRAIGHT);
                        car2.getCar().addRoute(this.deepRoute(route2));
                    }
                }

                if (alpha <= 0.50 && alpha > 0.25) {
                    if (way1.intValue() + 2 <= 3) {
                        way2 = WAY.VALUES.get(way1.intValue() + 2);
                        route2.add(DIRECTION.LEFT);
                        car2.getCar().addRoute(this.deepRoute(route2));

                    } else {
                        final int v = way1.intValue() + 2 - 4;

                        way2 = WAY.VALUES.get(v);
                        route2.add(DIRECTION.LEFT);
                        car2.getCar().addRoute(this.deepRoute(route2));

                    }

                }

                if (alpha <= 0.75 && alpha > 0.50) {
                    if (way1.intValue() + 3 <= 3) {
                        way2 = WAY.VALUES.get(way1.intValue() + 3);
                        route2.add(DIRECTION.LEFT);
                        car2.getCar().addRoute(this.deepRoute(route2));
                    } else {
                        final int v = way1.intValue() + 3 - 4;

                        way2 = WAY.VALUES.get(v);
                        route2.add(DIRECTION.LEFT);
                        car2.getCar().addRoute(this.deepRoute(route2));
                    }
                }
                if (alpha > 0.75) {
                    if (way1.intValue() + 3 <= 3) {
                        way2 = WAY.VALUES.get(way1.intValue() + 3);
                        route2.add(DIRECTION.STRAIGHT);
                        car2.getCar().addRoute(this.deepRoute(route2));
                    } else {
                        final int v = way1.intValue() + 3 - 4;

                        way2 = WAY.VALUES.get(v);
                        route2.add(DIRECTION.STRAIGHT);
                        car2.getCar().addRoute(this.deepRoute(route2));
                    }
                }
            }

            if (car1.getCar().getRoutes().get(car1.getCar().getCurrentRoute()).get(0).equals(DIRECTION.LEFT)) {

                if (alpha <= 0.33) {

                    if (way1.intValue() + 1 <= 3) {
                        way2 = WAY.VALUES.get(way1.intValue() + 1);
                        route2.add(DIRECTION.STRAIGHT);
                        car2.getCar().addRoute(this.deepRoute(route2));
                    } else {
                        final int v = way1.intValue() + 1 - 4;
                        way2 = WAY.VALUES.get(v);
                        route2.add(DIRECTION.STRAIGHT);
                        car2.getCar().addRoute(this.deepRoute(route2));
                    }
                }
                if (alpha <= 0.66 && alpha > 0.33) {

                    if (way1.intValue() + 1 <= 3) {
                        way2 = WAY.VALUES.get(way1.intValue() + 1);
                        route2.add(DIRECTION.LEFT);
                        car2.getCar().addRoute(this.deepRoute(route2));
                    } else {
                        final int v = way1.intValue() + 1 - 4;
                        way2 = WAY.VALUES.get(v);
                        route2.add(DIRECTION.LEFT);
                        car2.getCar().addRoute(this.deepRoute(route2));
                    }
                }
                if (alpha > 0.66) {

                    if (way1.intValue() + 2 <= 3) {
                        way2 = WAY.VALUES.get(way1.intValue() + 2);
                        route2.add(DIRECTION.STRAIGHT);
                        car2.getCar().addRoute(this.deepRoute(route2));
                    } else {
                        final int v = way1.intValue() + 2 - 4;
                        way2 = WAY.VALUES.get(v);
                        route2.add(DIRECTION.STRAIGHT);
                        car2.getCar().addRoute(this.deepRoute(route2));
                    }
                }

            }
            for (int j = 1; j < Defaults.MAX_ROUTES - 1; j++) {
                final List<DIRECTION> route = new ArrayList<>();
                if (this.random.nextDouble() < Defaults.P_ADD_NEW_ROUTE) { // randomly generate alternative routes for some vehicles
                    route.add(DIRECTION.random());
                    for (i = 1; i < Defaults.MAX_ROUTE_DEPTH; i++) {
                        if (this.random.nextDouble() < Defaults.P_ADD_DEPTH) { // randomly generate second direction for some route
                            route.add(DIRECTION.random());
                        }
                    }
                    car2.getCar().addRoute(route);
                }
            }
            car2.getCar().setName(String.format("%s_%d", way2, this.nCars));
            lc.add(new CrossingCar(car1, way1, STATUS.APPROACHING, d));
            lc.add(new CrossingCar(car2, way2, STATUS.APPROACHING, d));
            return lc;
        } else {
            this.log.warn("REFERENCE JUNCTION NOT YET CONFIGURED");
            return null;
        }

    }

    /**
     * @return a deep route
     */
    public List<DIRECTION> deepRoute(final List<DIRECTION> route) {
        for (int i = 1; i < Defaults.MAX_ROUTE_DEPTH; i++) {
            if (this.random.nextDouble() < Defaults.P_ADD_DEPTH) { // randomly generate second direction for some route
                route.add(DIRECTION.random());
            }
        }
        return route;
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

    @Override
    public VehiclesGenStrategy setSpeedRange(final int min, final int max) {
        this.minSpeed = min;
        this.maxSpeed = max;
        return this;
    }

    @Override
    public VehiclesGenStrategy setUrgencyRange(final int min, final int max) {
        this.minUrgency = min;
        this.maxUrgency = max;
        return this;
    }
}

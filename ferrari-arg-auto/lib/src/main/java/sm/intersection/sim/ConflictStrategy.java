package sm.intersection.sim;

import java.util.ArrayList;
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

public class ConflictStrategy implements VehiclesGenStrategy {
    private final Logger log = LoggerFactory.getLogger(FlatRouteRandomStrategy.class);
    private SmartJunction junction;
    private boolean setup = false;

    private List<WAY> values;
    private int size;
    private Random random;
    private long seed;
    private boolean seedSet;

    private long nCars;

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

    @Override
    public List<CrossingCar> newCars() {
        // TODO Auto-generated method stub
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
            final UrgentCar car1 = new UrgentCar(
                    new Car(String.format("%s_%d", way1, this.nCars), this.random.nextDouble() * 50), // TODO make speed range configurable
                    this.random.nextDouble()); // TODO make priority range configurable
            car1.getCar().addRoute(Collections.singletonList(DIRECTION.random())); // TODO randomize depth and number of routes
            this.nCars++;
            final UrgentCar car2 = new UrgentCar(
                    new Car(String.format("%s_%d", way2, this.nCars), this.random.nextDouble() * 50),
                    this.random.nextDouble());
            final double alpha = Math.random();
            if (car1.getCar().getRoutes().get(0).get(0).toString().equals(DIRECTION.STRAIGHT.toString())) {

                if (alpha <= 0.16) {
                    if (way1.intValue() + 1 <= 3) {
                        way2 = WAY.VALUES.get(way1.intValue() + 1);
                        car2.getCar().addRoute(Collections.singletonList(DIRECTION.STRAIGHT));
                    } else {
                        final int v = way1.intValue() + 1 - 4;

                        way2 = WAY.VALUES.get(v);
                        car2.getCar().addRoute(Collections.singletonList(DIRECTION.STRAIGHT));

                    }
                }
                if (alpha <= 0.32 && alpha > 0.16) {
                    if (way1.intValue() + 1 <= 3) {
                        way2 = WAY.VALUES.get(way1.intValue() + 1);
                        car2.getCar().addRoute(Collections.singletonList(DIRECTION.LEFT));
                    } else {
                        final int v = way1.intValue() + 1 - 4;

                        way2 = WAY.VALUES.get(v);
                        car2.getCar().addRoute(Collections.singletonList(DIRECTION.LEFT));

                    }
                }
                if (alpha <= 0.48 && alpha > 0.32) {
                    if (way1.intValue() + 2 <= 3) {
                        way2 = WAY.VALUES.get(way1.intValue() + 2);
                        car2.getCar().addRoute(Collections.singletonList(DIRECTION.LEFT));
                    } else {
                        final int v = way1.intValue() + 2 - 4;

                        way2 = WAY.VALUES.get(v);
                        car2.getCar().addRoute(Collections.singletonList(DIRECTION.LEFT));
                    }
                }
                if (alpha <= 0.64 && alpha > 0.48) {
                    if (way1.intValue() + 3 <= 3) {
                        way2 = WAY.VALUES.get(way1.intValue() + 3);
                        car2.getCar().addRoute(Collections.singletonList(DIRECTION.LEFT));
                    } else {
                        final int v = way1.intValue() + 3 - 4;

                        way2 = WAY.VALUES.get(v);
                        car2.getCar().addRoute(Collections.singletonList(DIRECTION.LEFT));
                    }
                }
                if (alpha <= 0.8 && alpha > 0.64) {
                    if (way1.intValue() + 3 <= 3) {
                        way2 = WAY.VALUES.get(way1.intValue() + 3);
                        car2.getCar().addRoute(Collections.singletonList(DIRECTION.STRAIGHT));
                    } else {
                        final int v = way1.intValue() + 3 - 4;

                        way2 = WAY.VALUES.get(v);
                        car2.getCar().addRoute(Collections.singletonList(DIRECTION.STRAIGHT));
                    }
                }
                if (alpha > 0.8) {
                    if (way1.intValue() + 3 <= 3) {
                        way2 = WAY.VALUES.get(way1.intValue() + 3);
                        car2.getCar().addRoute(Collections.singletonList(DIRECTION.RIGHT));
                    } else {
                        final int v = way1.intValue() + 3 - 4;

                        way2 = WAY.VALUES.get(v);
                        car2.getCar().addRoute(Collections.singletonList(DIRECTION.RIGHT));
                    }
                }
            }
            if (car1.getCar().getRoutes().get(0).get(0).toString().equals(DIRECTION.RIGHT.toString())) {
                if (alpha < 0.5) {
                    if (way1.intValue() + 1 <= 3) {
                        way2 = WAY.VALUES.get(way1.intValue() + 1);
                        car2.getCar().addRoute(Collections.singletonList(DIRECTION.STRAIGHT));
                    } else {
                        final int v = way1.intValue() + 1 - 4;
                        way2 = WAY.VALUES.get(v);
                        car2.getCar().addRoute(Collections.singletonList(DIRECTION.STRAIGHT));
                    }
                }
                if (alpha > 0.5) {
                    if (way1.intValue() + 2 <= 3) {
                        way2 = WAY.VALUES.get(way1.intValue() + 2);
                        car2.getCar().addRoute(Collections.singletonList(DIRECTION.LEFT));
                    } else {
                        final int v = way1.intValue() + 2 - 4;
                        way2 = WAY.VALUES.get(v);
                        car2.getCar().addRoute(Collections.singletonList(DIRECTION.LEFT));
                    }
                }
            }
            if (car1.getCar().getRoutes().get(0).get(0).toString().equals(DIRECTION.LEFT.toString())) {
                if (alpha <= 0.2) {

                    if (way1.intValue() + 1 <= 3) {
                        way2 = WAY.VALUES.get(way1.intValue() + 1);
                        car2.getCar().addRoute(Collections.singletonList(DIRECTION.STRAIGHT));
                    } else {
                        final int v = way1.intValue() + 1 - 4;
                        way2 = WAY.VALUES.get(v);
                        car2.getCar().addRoute(Collections.singletonList(DIRECTION.STRAIGHT));
                    }
                }
                if (alpha <= 0.4 && alpha > 0.2) {

                    if (way1.intValue() + 1 <= 3) {
                        way2 = WAY.VALUES.get(way1.intValue() + 1);
                        car2.getCar().addRoute(Collections.singletonList(DIRECTION.LEFT));
                    } else {
                        final int v = way1.intValue() + 1 - 4;
                        way2 = WAY.VALUES.get(v);
                        car2.getCar().addRoute(Collections.singletonList(DIRECTION.LEFT));
                    }
                }
                if (alpha <= 0.6 && alpha > 0.4) {

                    if (way1.intValue() + 2 <= 3) {
                        way2 = WAY.VALUES.get(way1.intValue() + 2);
                        car2.getCar().addRoute(Collections.singletonList(DIRECTION.STRAIGHT));
                    } else {
                        final int v = way1.intValue() + 2 - 4;
                        way2 = WAY.VALUES.get(v);
                        car2.getCar().addRoute(Collections.singletonList(DIRECTION.STRAIGHT));
                    }
                }
                if (alpha <= 0.8 && alpha > 0.6) {

                    if (way1.intValue() + 2 <= 3) {
                        way2 = WAY.VALUES.get(way1.intValue() + 2);
                        car2.getCar().addRoute(Collections.singletonList(DIRECTION.RIGHT));
                    } else {
                        final int v = way1.intValue() + 2 - 4;
                        way2 = WAY.VALUES.get(v);
                        car2.getCar().addRoute(Collections.singletonList(DIRECTION.RIGHT));
                    }
                }
                if (alpha > 0.8) {

                    if (way1.intValue() + 3 <= 3) {
                        way2 = WAY.VALUES.get(way1.intValue() + 3);
                        car2.getCar().addRoute(Collections.singletonList(DIRECTION.STRAIGHT));
                    } else {
                        final int v = way1.intValue() + 3 - 4;
                        way2 = WAY.VALUES.get(v);
                        car2.getCar().addRoute(Collections.singletonList(DIRECTION.STRAIGHT));
                    }
                }
            }
            car2.getCar().setName(String.format("%s_%d", way2, this.nCars));
            lc.add(new CrossingCar(car1, way1, STATUS.APPROACHING, d));
            lc.add(new CrossingCar(car2, way2, STATUS.APPROACHING, d));
            //    System.out.println(lc.get(0).getWay().toString()+" "+lc.get(0).getWay().intWay(way1));
            //    System.out.println(lc.get(1).getWay().toString()+" "+lc.get(1).getWay().intWay(way2));
            //    System.out.println(lc);
            return lc;
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
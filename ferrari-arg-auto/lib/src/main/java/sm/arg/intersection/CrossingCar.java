/**
 *
 */
package sm.arg.intersection;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tweetyproject.arg.aspic.syntax.AspicArgumentationTheory;
import org.tweetyproject.logics.pl.syntax.PlFormula;
import org.tweetyproject.logics.pl.syntax.Proposition;

import sm.arg.general.Debatable;
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
public class CrossingCar implements Debatable {

    private final Logger log = LoggerFactory.getLogger(CrossingCar.class);
    private final UrgentCar car;
    private WAY way;
    private STATUS state;
    private double distance;
    private DIRECTION lane;
    private boolean frozen;

    /**
     * @param car
     * @param way
     * @param state
     * @param distance in m
     */
    public CrossingCar(final UrgentCar car, final WAY way, final STATUS state, final double distance) {
        this.car = car;
        this.way = way;
        this.state = state;
        this.distance = distance;
        this.lane = car.getCar().getRoutes().get(car.getCar().getCurrentRoute()).get(0);
        this.frozen = false;
    }

    /**
     * @return the way
     */
    public WAY getWay() {
        return this.way;
    }

    /**
     * @param way the way to set
     */
    public CrossingCar setWay(final WAY way) {
        this.way = way;
        return this;
    }

    /**
     * @return the state
     */
    public STATUS getState() {
        return this.state;
    }

    /**
     * @param state the state to set
     */
    public CrossingCar setState(final STATUS state) {
        this.state = state;
        return this;
    }

    public double getDistance() {
        return this.distance;
    }

    public CrossingCar setDistance(final double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * @return the car
     */
    public UrgentCar getCar() {
        return this.car;
    }

    /**
     *
     * @return time to cross in seconds
     */
    public double getTimeToCross() {
        return this.distance / (this.car.getCar().getSpeed() / 3.6); // distance in m, speed in km/h => / 3.6 in m/s
    }

    public String getName() {
        return this.car.getCar().getName();
    }

    public Map<Integer, List<DIRECTION>> getRoutes() {
        return this.car.getCar().getRoutes();
    }

    @Override
    public List<Proposition> addAsArgTheory(final AspicArgumentationTheory<PlFormula> t) {
        final Proposition a = new Proposition(this.car.getCar().getName());
        t.addAxiom(a);
        return Collections.singletonList(a);
    }

    @Override
    public String toString() {
        return String.format("CrossingCar [car=%s, way=%s,lane=%s, state=%s, distance=%s]", this.car, this.way,
                this.lane, this.state, this.distance);
    }

    /**
     *
     * @param smartJunction the next junction to be placed into
     * @return
     */
    public CrossingCar updateAfterCrossing(final SmartJunction nextJunction) {
        final DIRECTION nextD = this.getCurrentRoutePath().remove(0); // where car was going before this crossing // TODO could be useful to have both the original routes as well as the current routes
        this.frozen = false;
        if (this.getCurrentRoutePath().size() <= 0) { // trip concluded
            this.way = null;
            this.state = STATUS.SERVED;
            this.distance = -1;
            this.lane = null;
            return this;
        }
        this.state = STATUS.APPROACHING;
        this.lane = this.getCurrentRoutePath().get(0);
        WAY nextW = null;
        switch (this.way) {
            case WEST:
                switch (nextD) {
                    case STRAIGHT:
                        nextW = this.way;
                        break;
                    case RIGHT:
                        nextW = WAY.NORTH;
                        break;
                    case LEFT:
                        nextW = WAY.SOUTH;
                        break;
                }
                break;
            case NORTH:
                switch (nextD) {
                    case STRAIGHT:
                        nextW = this.way;
                        break;
                    case RIGHT:
                        nextW = WAY.EAST;
                        break;
                    case LEFT:
                        nextW = WAY.WEST;
                        break;
                }
                break;
            case EAST:
                switch (nextD) {
                    case STRAIGHT:
                        nextW = this.way;
                        break;
                    case RIGHT:
                        nextW = WAY.SOUTH;
                        break;
                    case LEFT:
                        nextW = WAY.NORTH;
                        break;
                }
                break;
            case SOUTH:
                switch (nextD) {
                    case STRAIGHT:
                        nextW = this.way;
                        break;
                    case RIGHT:
                        nextW = WAY.WEST;
                        break;
                    case LEFT:
                        nextW = WAY.EAST;
                        break;
                }
                break;
        }
        this.way = nextW;
        for (final RSU<?> rsu : nextJunction.getRoads().get(nextW).getRsus()) {
            if (rsu instanceof DistanceRSU && rsu.getType().isAssignableFrom(Double.class)) {
                this.distance = (double) rsu.getMeasurement();
            } else {
                this.log.warn("No RSU instanceof DistanceRSU and assignable from Double found: {}",
                        nextJunction.getRoads().get(this.way).getRsus());
                this.distance = Double.NaN;
                //                      throw new NoSuitableRSUException("No RSU instanceof DistanceRSU and assignable from Double found", this.junction.getRoads().get(way).getRsus());
            }
        }
        return this;
    }

    public DIRECTION getLane() {
        return this.lane;
    }

    /**
     *
     * @param route
     * @return
     */
    public CrossingCar setCurrentRoute(final int route) {
        if (!this.frozen) {
            this.car.getCar().setCurrentRoute(route);
        } else {
            //            this.log.warn("I AM FROZEN, I CAN'T CHANGE ROUTE");
        }
        return this;
    }

    /**
     *
     * @return
     */
    public int getCurrentRouteRank() {
        return this.car.getCar().getCurrentRoute();
    }

    /**
     *
     * @return
     */
    public List<DIRECTION> getCurrentRoutePath() {
        return this.car.getCar().getRoute(this.getCurrentRouteRank());
    }

    /**
     * @return the frozen
     */
    public boolean isFrozen() {
        return this.frozen;
    }

    /**
     * set frozen to true
     */
    public void freeze() {
        this.frozen = true;
    }

    /**
     * set frozen to true
     */
    public void unfreeze() {
        this.frozen = false;
    }

}

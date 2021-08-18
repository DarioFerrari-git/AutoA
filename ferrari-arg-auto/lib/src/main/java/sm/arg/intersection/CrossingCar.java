/**
 *
 */
package sm.arg.intersection;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.tweetyproject.arg.aspic.syntax.AspicArgumentationTheory;
import org.tweetyproject.logics.pl.syntax.PlFormula;
import org.tweetyproject.logics.pl.syntax.Proposition;

import sm.arg.general.Debatable;
import sm.intersection.DIRECTION;
import sm.intersection.STATUS;
import sm.intersection.UrgentCar;
import sm.intersection.WAY;

/**
 * @author sm
 *
 */
public class CrossingCar implements Debatable {

    private final UrgentCar car;
    private WAY way;
    private STATUS state;
    private double distance;
    private DIRECTION lane;

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
        this.lane = car.getCar().getRoutes().get(0).get(0);
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

    public void updateAfterCrossing() {
        this.state = STATUS.APPROACHING;
        this.way = ; // TODO
        this.lane = this.car.getCar().getRoutes().get(0).get(0);
    }

}

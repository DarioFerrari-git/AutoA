/**
 *
 */
package sm.intersection;

/**
 * @author sm
 *
 */
public final class UrgentCar {

    private final Car car;
    private double urgency;

    /**
     * @param car
     * @param urgency
     */
    public UrgentCar(final Car car, final double urgency) {
        this.car = car;
        this.urgency = urgency;
    }

    /**
     * @return the urgency
     */
    public double getUrgency() {
        return this.urgency;
    }

    public UrgentCar setUrgency(final double urgency) {
        this.urgency = urgency;
        return this;
    }

    /**
     * @return the car
     */
    public Car getCar() {
        return this.car;
    }

    public boolean hasOtherRoutes() {
        return this.car.getRoutes().size() > 1;
    }

    @Override
    public String toString() {
        return String.format("UrgentCar [car=%s, urgency=%s]", this.car, this.urgency);
    }

}

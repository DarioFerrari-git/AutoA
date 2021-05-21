/**
 *
 */
package sm.arg.intersection;

/**
 * @author sm
 *
 */
public final class ArguingCar {

	private final CrossingCar car;
	private double urgency;

	/**
	 * @param car
	 * @param urgency
	 */
	public ArguingCar(final CrossingCar car, final double urgency) {
		this.car = car;
		this.urgency = urgency;
	}

	/**
	 * @return the urgency
	 */
	public double getUrgency() {
		return this.urgency;
	}

	public ArguingCar setUrgency(final double urgency) {
		this.urgency = urgency;
		return this;
	}

	/**
	 * @return the car
	 */
	public CrossingCar getCar() {
		return this.car;
	}

	public boolean hasOtherRoutes() {
		return this.car.getCar().getRoutes().size() > 1;
	}

	@Override
	public String toString() {
		return String.format("ArguingCar [car=%s, urgency=%s]", this.car, this.urgency);
	}

}

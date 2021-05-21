/**
 *
 */
package sm.arg.intersection;

/**
 * @author sm
 *
 */
public class CrossingCar {

	private final Car car;
	private WAY way;
	private STATUS state;
	private double distance;

	/**
	 * @param car
	 * @param way
	 * @param state
	 * @param distance in m
	 */
	public CrossingCar(final Car car, final WAY way, final STATUS state, final double distance) {
		this.car = car;
		this.way = way;
		this.state = state;
		this.distance = distance;
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
	public Car getCar() {
		return this.car;
	}
	
	/**
	 * 
	 * @return time to cross in seconds
	 */
	public double getTimeToCross() {
		return (this.distance / (this.car.getSpeed() / 3.6)); // distance in m, speed in km/h => / 3.6 in m/s
	}

	@Override
	public String toString() {
		return String.format("CrossingCar [car=%s, way=%s, state=%s, distance=%s]", this.car, this.way,
				this.state, this.distance);
	}

}

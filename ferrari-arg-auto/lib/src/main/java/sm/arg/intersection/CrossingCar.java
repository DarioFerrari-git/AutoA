/**
 *
 */
package sm.arg.intersection;

/**
 * @author sm
 *
 */
public class CrossingCar {

	private final ArguingCar car;
	private WAY position;
	private STATE state;
	private double distance;

	/**
	 * @param car
	 * @param position
	 * @param state
	 * @param distance
	 */
	public CrossingCar(final ArguingCar car, final WAY position, final STATE state, final double distance) {
		this.car = car;
		this.position = position;
		this.state = state;
		this.distance = distance;
	}

	/**
	 * @return the position
	 */
	public WAY getPosition() {
		return this.position;
	}

	/**
	 * @param position the position to set
	 */
	public CrossingCar setPosition(final WAY position) {
		this.position = position;
		return this;
	}

	/**
	 * @return the state
	 */
	public STATE getState() {
		return this.state;
	}

	/**
	 * @param state the state to set
	 */
	public CrossingCar setState(final STATE state) {
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
	public ArguingCar getCar() {
		return this.car;
	}
	
	public double getTimeToCross() {
		
	}

	@Override
	public String toString() {
		return String.format("CrossingCar [car=%s, position=%s, state=%s, distance=%s]", this.car, this.position,
				this.state, this.distance);
	}

}

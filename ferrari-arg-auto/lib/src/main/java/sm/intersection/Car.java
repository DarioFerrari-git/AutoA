/**
 *
 */
package sm.intersection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sm
 *
 */
public final class Car {

	private final String name;
	private final Map<Integer, List<DIRECTION>> routes;
	private double speed;
	private int next;

	/**
	 * @param name
	 * @param speed in km/h
	 */
	public Car(final String name, final double speed) {
		this.name = name;
		this.routes = new HashMap<>();
		this.speed = speed;
		this.next = 0;
	}

	/**
	 * @param name
	 * @param routes
	 * @param speed
	 */
	public Car(final String name, final Map<Integer, List<DIRECTION>> routes, final double speed) {
		this.name = name;
		this.routes = routes;
		this.speed = speed;
		this.next = 0;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the routes
	 */
	public Map<Integer, List<DIRECTION>> getRoutes() {
		return this.routes;
	}

	public Car addRoute(final List<DIRECTION> route) {
		this.routes.put(this.next, route);
		this.next++;
		return this;
	}

	/**
	 * @return the speed
	 */
	public double getSpeed() {
		return this.speed;
	}

	public Car setSpeed(final double speed) {
		this.speed = speed;
		return this;
	}

	@Override
	public String toString() {
		return String.format("Car [name=%s, routes=%s, speed=%s]", this.name, this.routes, this.speed);
	}

}

/**
 * 
 */
package sm.intersection.sim;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sm.arg.intersection.CrossingCar;
import sm.intersection.STATUS;
import sm.intersection.SmartJunction;
import sm.intersection.WAY;

/**
 * @author sm
 *
 */
public final class Simulation {

	private final Logger log = LoggerFactory.getLogger(Simulation.class);
	private final SmartJunction junction;
	private final List<CrossingCar> cars;
	private double step;
	private long steps;

	public Simulation(final SmartJunction junction, List<CrossingCar> cars) {
		this.junction = junction;
		this.cars = cars;
		this.step = 1; // TODO now assumed to be 1 s, but make it configurable (X seconds/minutes/...)
		this.steps = 0;
	}

	public void step() {
		for (CrossingCar car : cars) {
			if (car.getState().equals(STATUS.APPROACHING)) {
				car.setDistance(car.getDistance() - (car.getCar().getCar().getSpeed() / 3.6 * this.step));
			}
		}
		this.steps++;
	}

	public void go() {
		log.warn("NOT YET IMPLEMENTED");
	}

	public void pause() {
		log.warn("NOT YET IMPLEMENTED");
	}

	public void logSituation() {
		int nCars;
		log.info("Logging simulation situation -----START [step {}]----->", this.steps);
		System.out.printf("%s ways junction <%s> managed by policy <%s> \n", this.junction.nRoads(),
				this.junction.getName(), this.junction.getPolicy().getName());
		for (WAY way : this.junction.getRoads().keySet()) {
			nCars = 0;
			System.out.printf("\t %s: %d lane(s) \n", way, this.junction.getRoads().get(way).getRoad().nLanes(),
					this.junction.getRoads().get(way).getRoad().getLanes());
			for (CrossingCar car : this.cars) {
				if (car.getWay().equals(way)) {
					nCars++;
					System.out.printf("\t\t <%s> %s in %.2f s (urgency: %.2f) at %.2f km/h (distance: %.2f m) \n",
							car.getName(), car.getState(), car.getTimeToCross(), car.getCar().getUrgency(),
							car.getCar().getCar().getSpeed(), car.getDistance());
				}
			}
			System.out.printf("\t\t ----- %d car(s) \n", nCars);
		}
		log.info("<-----[step {}] END----- Logging simulation situation", this.steps);
	}

}

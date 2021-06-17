/**
 * 
 */
package sm.intersection.sim;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sm.arg.intersection.CrossingCar;
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
	private long step;

	public Simulation(final SmartJunction junction, List<CrossingCar> cars) {
		this.junction = junction;
		this.cars = cars;
		this.step = 0;
	}

	public void step() {
		log.warn("NOT YET IMPLEMENTED");
	}

	public void go() {
		log.warn("NOT YET IMPLEMENTED");
	}

	public void pause() {
		log.warn("NOT YET IMPLEMENTED");
	}

	public void logSituation() {
		log.info("Logging simulation situation -----START----->");
		System.out.printf("%s ways junction <%s> managed by policy <%s> \n", this.junction.nRoads(), this.junction.getName(),
				this.junction.getPolicy().getName());
		for (WAY way : this.junction.getRoads().keySet()) {
			System.out.printf("\t %s: %d lanes \n", way, this.junction.getRoads().get(way).getRoad().nLanes(),
					this.junction.getRoads().get(way).getRoad().getLanes());
			for (CrossingCar car : this.cars) {
				if (car.getWay().equals(way)) {
					System.out.printf("\t\t Car <%s> %s in %f s (urgency: %f)\n", car.getName(), car.getState(),
							car.getTimeToCross(), car.getCar().getUrgency());
				}
			}
		}
		log.info("<-----END----- Logging simulation situation");
	}

}

/**
 *
 */
package sm.intersection.sim;

import java.util.ArrayList;
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
public class SingleJunctionSimulation {

	private final Logger log = LoggerFactory.getLogger(SingleJunctionSimulation.class);
	private final SmartJunction junction;
	private final List<CrossingCar> cars;
	private final double step;
	private long steps;
	private boolean going;

	public SingleJunctionSimulation(final SmartJunction junction, final List<CrossingCar> cars) {
		this.junction = junction;
		this.cars = cars;
		this.step = 1; // TODO now assumed to be 1 s, but make it configurable (X seconds/minutes/...)
		this.steps = 0;
		this.going = false;
	}

	public void step(final Boolean log /*, final long steps*/) {
		if (!this.going) {
//			for (int s = 0; s < steps; s++) {
				this.steps++;
				final List<CrossingCar> toRemove = new ArrayList<>();
				for (final CrossingCar car : this.cars) {
					switch (car.getState()) {
					case APPROACHING:
						car.setDistance(car.getDistance() - car.getCar().getCar().getSpeed() / 3.6 * this.step);
						break;
					case SERVED:
						this.log.warn("SHOULDN'T HAPPEN");
						break;
					default:
						throw new IllegalArgumentException("Unexpected value: " + car.getState());
					}
					if (car.getDistance() <= 0) { // TODO junction approximated as point in space
						car.setState(STATUS.SERVED);
						this.log.info("<{}> {}, removing from simulation", car.getName(), car.getState());
						toRemove.add(car);
					}
				}
				this.cars.removeAll(toRemove);
				if (log) {
					this.logSituation();
				}
//			}
		} else {
			this.log.warn("SIMULATION GOING, PAUSE IT FIRST");
		}
	}

	public void go(final Boolean log) {
		if (!going) {
			while (!this.cars.isEmpty()) {
				this.step(log, /*1,*/ true);
			}
		} else {
			this.log.warn("SIMULATION ALREADY GOING");
		}
	}

	public void pause() {
		if (this.going) {
			this.going = false;
		} else {
			this.log.warn("SIMULATION ALREADY PAUSED");
		}
	}

	public void logSituation() {
		int nCars;
		this.log.info("Logging simulation situation -----START [step {}]----->", this.steps);
		System.out.printf("%s ways junction <%s> managed by policy <%s> \n", this.junction.nRoads(),
				this.junction.getName(), this.junction.getPolicy().getName());
		for (final WAY way : this.junction.getRoads().keySet()) {
			nCars = 0;
			System.out.printf("\t %s: %d lane(s) \n", way, this.junction.getRoads().get(way).getRoad().nLanes(),
					this.junction.getRoads().get(way).getRoad().getLanes());
			for (final CrossingCar car : this.cars) {
				if (car.getWay().equals(way)) {
					nCars++;
					System.out.printf(
							"\t\t <%s> %s going %s in %.2f s (urgency: %.2f) at %.2f km/h (distance: %.2f m) \n",
							car.getName(), car.getState(), car.getRoutes().get(0), car.getTimeToCross(),
							car.getCar().getUrgency(), car.getCar().getCar().getSpeed(), car.getDistance()); // TODO
																												// only
																												// fetches
																												// first
																												// route
				}
			}
			System.out.printf("\t\t ----- %d car(s) \n", nCars);
		}
		this.log.info("<-----[step {}] END----- Logging simulation situation", this.steps);
	}

	public boolean isGoing() {
		return this.going;
	}

	/**
	 * @return the junction
	 */
	public SmartJunction getJunction() {
		return junction;
	}

	/**
	 * @return the cars
	 */
	public List<CrossingCar> getCars() {
		return cars;
	}
	
	public SingleJunctionSimulation addCar(CrossingCar car) {
		this.cars.add(car);
		return this;
	}

	/**
	 * @return the step
	 */
	public double getStep() {
		return step;
	}

	/**
	 * @return the steps
	 */
	public long getSteps() {
		return steps;
	}

	private void step(final Boolean log, /*final long steps,*/ final Boolean bypass) {
		if (bypass) {
			this.going = false;
			this.step(log/*, steps*/);
			this.going = true;
		}
	}

}
/**
 *
 */
package sm.intersection.sim;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tweetyproject.arg.aspic.reasoner.SimpleAspicReasoner;
import org.tweetyproject.arg.aspic.ruleformulagenerator.PlFormulaGenerator;
import org.tweetyproject.arg.aspic.syntax.AspicArgumentationTheory;
import org.tweetyproject.arg.dung.reasoner.AbstractExtensionReasoner;
import org.tweetyproject.arg.dung.semantics.Semantics;
import org.tweetyproject.commons.ParserException;
import org.tweetyproject.logics.pl.parser.PlParser;
import org.tweetyproject.logics.pl.syntax.PlFormula;
import org.tweetyproject.logics.pl.syntax.Proposition;

import sm.arg.general.Debatable;
import sm.arg.intersection.CrossingCar;
import sm.arg.intersection.FourWaysJunctionConfig;
import sm.intersection.RSU;
import sm.intersection.STATUS;
import sm.intersection.SmartJunction;
import sm.intersection.SmartRoad;
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
	protected boolean going;

	public SingleJunctionSimulation(final SmartJunction junction, final List<CrossingCar> cars) {
		this.junction = junction;
		this.cars = cars;
		this.step = 1; // TODO now assumed to be 1 s, but make it configurable (X seconds/minutes/...)
		this.steps = 0;
		this.going = false;
	}

	public void step(final Boolean log /* , final long steps */) {
		if (!this.going) {
//			for (int s = 0; s < steps; s++) {
			this.steps++;
			final List<CrossingCar> toRemove = new ArrayList<>();
			for (final CrossingCar car : this.cars) {
				switch (car.getState()) {
				case APPROACHING:
					car.setDistance(car.getDistance() - car.getCar().getCar().getSpeed() / 3.6 * this.step);

					try {
						assignRightOfWay(); // TODO complete and test
					} catch (ParserException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

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

	private void assignRightOfWay() throws ParserException, IOException {
		/*
		 * create ASPIC+ theory
		 */
		final List<Proposition> p = new ArrayList<>(); // TODO p non viene mai letto però....a cosa serve dunque?
		final AspicArgumentationTheory<PlFormula> t = new AspicArgumentationTheory<>(new PlFormulaGenerator());
		t.setRuleFormulaGenerator(new PlFormulaGenerator());
		((Debatable) this.junction.getPolicy()).addAsArgTheory(t); // TODO check if redesign can avoid casts
		for (SmartRoad road : this.junction.getRoads().values()) {
			for (RSU<?> rsu : road.getRsus()) {
				if (rsu.getType().isAssignableFrom(Debatable.class)) {
					final Proposition b = ((Debatable) rsu).addAsArgTheory(t).get(0);
					p.add(b);
				}
			}
		}
		Proposition a = null;
		for (final CrossingCar element : this.cars) {
			a = element.addAsArgTheory(t).get(0);
			p.add(a);
		}
		final FourWaysJunctionConfig config = new FourWaysJunctionConfig(this.junction, this.cars); // TODO valido solo
																									// per una junction
																									// di quel tipo
		config.addAsArgTheory(t);
		/*
		 * query ASPIC+ theory
		 */
		final PlParser plparser = new PlParser();
		final SimpleAspicReasoner<PlFormula> ar = new SimpleAspicReasoner<>(
				AbstractExtensionReasoner.getSimpleReasonerForSemantics(Semantics.GROUNDED_SEMANTICS));
		final PlFormula pf = plparser.parseFormula("Incident");
		/*
		 * TODO cambiare stato veicoli (vedi classe {@link sm.intersection.STATUS}) a
		 * seconda della situazione
		 */
	}

	public void go(final Boolean log) {
		if (!going) {
			while (!this.cars.isEmpty()) {
				this.step(log, /* 1, */ true);
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

	private void step(final Boolean log, /* final long steps, */ final Boolean bypass) {
		if (bypass) {
			this.going = false;
			this.step(log/* , steps */);
			this.going = true;
		}
	}

}

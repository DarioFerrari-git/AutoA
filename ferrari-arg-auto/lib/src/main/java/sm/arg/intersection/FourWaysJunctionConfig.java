/**
 *
 */
package sm.arg.intersection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tweetyproject.arg.aspic.syntax.AspicArgumentationTheory;
import org.tweetyproject.arg.aspic.syntax.DefeasibleInferenceRule;
import org.tweetyproject.arg.aspic.syntax.StrictInferenceRule;
import org.tweetyproject.logics.pl.syntax.Negation;
import org.tweetyproject.logics.pl.syntax.PlFormula;
import org.tweetyproject.logics.pl.syntax.Proposition;

import sm.arg.general.Debatable;
import sm.intersection.CrossingPolicy;
import sm.intersection.DIRECTION;
import sm.intersection.NoSuitableRSUException;
import sm.intersection.RSU;
import sm.intersection.Road;
import sm.intersection.STATUS;
import sm.intersection.SmartJunction;
import sm.intersection.SmartRoad;
import sm.intersection.UrgentCar;
import sm.intersection.WAY;

/**
 * @author sm
 *
 */
public final class FourWaysJunctionConfig implements Debatable {

	private final Logger log = LoggerFactory.getLogger(FourWaysJunctionConfig.class);
	private final SmartJunction junction;
	private final List<CrossingCar> cars;

	/**
	 * Given RSUs are replicated on each road
	 *
	 * @param junctionName
	 * @param policy
	 * @param rsus
	 * @return
	 */
	public FourWaysJunctionConfig(final String junctionName, final CrossingPolicy policy, final RSU<?>... rsu) {
		final Map<WAY, SmartRoad> roads = new HashMap<>();
		final List<DIRECTION> lanes = Arrays.asList(DIRECTION.values()); // each road has all 3 lanes
		final List<RSU<?>> rsus = Arrays.asList(rsu); // each road has all given RSUs TODO RSU name no longer unique!
		for (final WAY way : WAY.values()) {
			roads.put(way, new SmartRoad(new Road(way.name(), lanes), rsus));
		}
		this.junction = new SmartJunction(junctionName, roads, policy);
		this.cars = new ArrayList<>();
	}

	/**
	 * roadname must exist in junction
	 *
	 * @param car
	 * @param roadName
	 * @return
	 * @throws NoSuitableRSUException
	 */
	public FourWaysJunctionConfig addCar(final UrgentCar car, final String roadName) {
		Double d = null;
		for (final WAY way : this.junction.getRoads().keySet()) {
			if (this.junction.getRoads().get(way) != null
					&& this.junction.getRoads().get(way).getRoad().getName().equals(roadName)) {
				for (final RSU<?> rsu : this.junction.getRoads().get(way).getRsus()) {
					if (rsu instanceof DistanceRSU && rsu.getType().isAssignableFrom(Double.class)) {
						d = rsu.getMeasurement();
					} else {
						this.log.warn("No RSU instanceof DistanceRSU and assignable from Double found: {}",
								this.junction.getRoads().get(way).getRsus());
						d = Double.NaN;
//						throw new NoSuitableRSUException("No RSU instanceof DistanceRSU and assignable from Double found", this.junction.getRoads().get(way).getRsus());
					}
				}
				this.cars.add(new CrossingCar(car, way, STATUS.APPROACHING, d));
			}
		}
		return this;
	}

	/**
	 * @return the junction
	 */
	public SmartJunction getJunction() {
		return this.junction;
	}

	/**
	 * @return the cars
	 */
	public List<CrossingCar> getCars() {
		return this.cars;
	}

	@Override
	public List<Proposition> addAsArgTheory(final AspicArgumentationTheory<PlFormula> t) {
		Proposition a = null;
		Proposition b = null;
		Proposition c = null;
		Proposition d = null;
		Proposition f = null;
		final ArrayList<String> alreadyConsidered = new ArrayList<>();
		boolean Problems = false;
//		boolean GlobalIncident = false;
		final ArrayList<String> CarsInvolvedinCrush = new ArrayList<>();
		DefeasibleInferenceRule<PlFormula> r1 = new DefeasibleInferenceRule<>();
		StrictInferenceRule<PlFormula> r2 = new StrictInferenceRule<>();
		for (int i = 0; i < this.cars.size(); i++) {
			a = new Proposition(this.cars.get(i).getName());
			c = new Proposition(a + "_CorrectlyDetected");
			d = new Proposition(a + "_WronglyDetected");
			r1 = new DefeasibleInferenceRule<>();
			// TODO non riusciamo a utilizzare l'auto-traduzione dentro DistanceRSU? lì
			// dentro c'è già il controllo della soglia... ->Non è possibile in quanto è
			// necessaria una dipendenza con le CrossingCars
			if (this.junction.getRoads().get(this.cars.get(i).getWay()).getRsus().get(0).getConfidence() < 0.5) {
				b = new Proposition("RSU_untrustworthy");
				r1.setConclusion(d);
				r1.addPremise(b);
				r1.addPremise(a);
				t.addRule(r1);
				Problems = true;
			} else {
				b = new Proposition("RSU_trustworthy");
				r1.setConclusion(c);
				r1.addPremise(b);
				r1.addPremise(a);
				t.addRule(r1);
			}
			for (int j = 0; j < this.cars.size(); j++) {
				b = new Proposition("PossibleIncident_" + this.cars.get(i).getName() + this.cars.get(j).getName());
				f = new Proposition(this.cars.get(j).getName());

				if (!this.cars.get(i).equals(this.cars.get(j))
						&& !alreadyConsidered.contains(this.cars.get(i).getName() + "0" + this.cars.get(j).getName())) {
					if (this.noConflicts(i, j)) {
						r2 = new StrictInferenceRule<>();
						r2.setConclusion(new Negation(b));
						r2.addPremise(a);
						r2.addPremise(f);
						t.addRule(r2);
						alreadyConsidered.add(this.cars.get(j).getName() + "0" + this.cars.get(i).getName());
						this.log.debug(alreadyConsidered.toString());
					} else {
						c = new Proposition(this.junction.getPolicy().getName());
						r1 = new DefeasibleInferenceRule<>();
						r2 = new StrictInferenceRule<>();

						if (!alreadyConsidered.contains(this.cars.get(j).getName() + "0" + this.cars.get(i).getName())
								&& !alreadyConsidered
										.contains(this.cars.get(i).getName() + "0" + this.cars.get(j).getName())
								&& !this.cars.get(i).equals(this.cars.get(j)) && !alreadyConsidered
										.contains(this.cars.get(i).getName() + "x" + this.cars.get(j).getName())) {
							if (Problems) {
								r1.setConclusion(b);
								r1.addPremise(a);
								r1.addPremise(f);
								t.addRule(r1);
								alreadyConsidered.add(this.cars.get(j).getName() + "x" + this.cars.get(i).getName());
//								GlobalIncident = true;
								CarsInvolvedinCrush.add(this.cars.get(i).getName() + this.cars.get(j).getName());
							}
							if (!Problems && this.cars.get(j)
									.equals(this.junction.getPolicy().rightOfWay(this.cars.get(i), this.cars.get(j)))) {
								d = new Proposition(f + "_passesFirst");
								r1.setConclusion(d);
								r1.addPremise(c);
								r1.addPremise(f);
								t.add(r1);
								r2.setConclusion(new Negation(b));
								r2.addPremise(d);
								r2.addPremise(a);
								t.add(r2);
								alreadyConsidered.add(this.cars.get(j).getName() + "x" + this.cars.get(i).getName());
							}
						}
					}
				}
			}
		}
		a = new Proposition("Incident");
		if (CarsInvolvedinCrush.size() > 0) {
			r2 = new StrictInferenceRule<>();
			r2.setConclusion(a);
			for (final String element : CarsInvolvedinCrush) {
				b = new Proposition("PossibleIncident_" + element);
				r2.addPremise(b);
			}
			t.add(r2);
		}

		// TODO perchè un'altra coppia di iterazioni? non potevamo fare tutto in una?
		// Commentata la seconda coppia di iterazioni
		/*
		 * for (final CrossingCar element : this.cars) { a = new
		 * Proposition(element.getName()); b = new
		 * Proposition(this.junction.getPolicy().getName()); for (final CrossingCar
		 * element2 : this.cars) { final Proposition e = new
		 * Proposition("PossibleIncident_" + element.getName() + element2.getName()); r1
		 * = new DefeasibleInferenceRule<>(); r2 = new StrictInferenceRule<>(); f = new
		 * Proposition(element2.getName()); if
		 * (!alreadyConsidered.contains(element2.getName() + "0" + element.getName()) &&
		 * !alreadyConsidered.contains(element.getName() + "0" + element2.getName()) &&
		 * !element.equals(element2) && !alreadyConsidered.contains(element.getName() +
		 * "x" + element2.getName())) { if (Problems) { r1.setConclusion(e);
		 * r1.addPremise(a); r1.addPremise(f); t.addRule(r1);
		 * alreadyConsidered.add(element2.getName() + "x" + element.getName()); } if
		 * (!Problems && element2.equals(this.junction.getPolicy().rightOfWay(element,
		 * element2))) { c = new Proposition(f + "_passesFirst"); r1.setConclusion(c);
		 * r1.addPremise(b); r1.addPremise(f); t.add(r1); r2.setConclusion(new
		 * Negation(e)); r2.addPremise(c); r2.addPremise(a); t.add(r2);
		 * alreadyConsidered.add(element2.getName() + "x" + element.getName()); } } } }
		 */
		return Arrays.asList(a, b, c, d, f);
	}

	private boolean noConflicts2(final int i, final int j) { // TODO può essere semplificato? se controlliamo prima
															// DIRECTION e poi WAY?
		return this.cars.get(i).getWay().equals(WAY.SOUTH) && this.cars.get(j).getWay().equals(WAY.NORTH)
				&& !this.cars.get(i).getRoutes().get(0).contains(DIRECTION.LEFT)
				&& !this.cars.get(j).getRoutes().get(0).contains(DIRECTION.LEFT)
				|| this.cars.get(i).getWay().equals(WAY.EAST) && this.cars.get(j).getWay().equals(WAY.WEST)
						&& !this.cars.get(i).getRoutes().get(0).contains(DIRECTION.LEFT)
						&& !this.cars.get(j).getRoutes().get(0).contains(DIRECTION.LEFT)
				|| this.cars.get(i).getWay().equals(WAY.NORTH) && this.cars.get(j).getWay().equals(WAY.SOUTH)
						&& this.cars.get(i).getRoutes().get(0).contains(DIRECTION.LEFT)
						&& this.cars.get(j).getRoutes().get(0).contains(DIRECTION.LEFT)
				|| this.cars.get(i).getWay().equals(WAY.EAST) && this.cars.get(j).getWay().equals(WAY.WEST)
						&& this.cars.get(i).getRoutes().get(0).contains(DIRECTION.LEFT)
						&& this.cars.get(j).getRoutes().get(0).contains(DIRECTION.LEFT)
				|| this.cars.get(i).getWay().equals(WAY.NORTH) && this.cars.get(j).getWay().equals(WAY.EAST)
						&& this.cars.get(i).getRoutes().get(0).contains(DIRECTION.RIGHT)
				|| this.cars.get(i).getWay().equals(WAY.NORTH) && this.cars.get(j).getWay().equals(WAY.EAST)
						&& this.cars.get(j).getRoutes().get(0).contains(DIRECTION.RIGHT)
						&& this.cars.get(i).getRoutes().get(0).contains(DIRECTION.LEFT)
				|| this.cars.get(i).getWay().equals(WAY.WEST) && this.cars.get(j).getWay().equals(WAY.NORTH)
						&& this.cars.get(i).getRoutes().get(0).contains(DIRECTION.RIGHT)
				|| this.cars.get(i).getWay().equals(WAY.WEST) && this.cars.get(j).getWay().equals(WAY.NORTH)
						&& this.cars.get(i).getRoutes().get(0).contains(DIRECTION.RIGHT)
						&& this.cars.get(i).getRoutes().get(0).contains(DIRECTION.LEFT)
				|| this.cars.get(i).getWay().equals(WAY.SOUTH) && this.cars.get(j).getWay().equals(WAY.WEST)
						&& this.cars.get(i).getRoutes().get(0).contains(DIRECTION.RIGHT)
				|| this.cars.get(i).getWay().equals(WAY.SOUTH) && this.cars.get(j).getWay().equals(WAY.WEST)
						&& this.cars.get(i).getRoutes().get(0).contains(DIRECTION.RIGHT)
						&& this.cars.get(i).getRoutes().get(0).contains(DIRECTION.LEFT)
				|| this.cars.get(i).getWay().equals(WAY.EAST) && this.cars.get(j).getWay().equals(WAY.SOUTH)
						&& this.cars.get(i).getRoutes().get(0).contains(DIRECTION.RIGHT)
				|| this.cars.get(i).getWay().equals(WAY.EAST) && this.cars.get(j).getWay().equals(WAY.SOUTH)
						&& this.cars.get(i).getRoutes().get(0).contains(DIRECTION.RIGHT)
						&& this.cars.get(i).getRoutes().get(0).contains(DIRECTION.LEFT)
				|| this.cars.get(i).getWay().equals(this.cars.get(j).getWay());
	}

	private boolean noConflicts(final int i, final int j) { // TODO può essere semplificato? se controlliamo prima
																// DIRECTION e poi WAY?
		return this.cars.get(i).getRoutes().get(0).contains(DIRECTION.RIGHT)
				&& this.cars.get(j).getRoutes().get(0).contains(DIRECTION.RIGHT)
				|| this.cars.get(i).getRoutes().get(0).contains(DIRECTION.RIGHT)
						&& this.cars.get(j).getRoutes().get(0).contains(DIRECTION.STRAIGHT)
						&& this.cars.get(i).getWay().equals(WAY.WEST) && !this.cars.get(j).getWay().equals(WAY.NORTH)
				|| this.cars.get(i).getRoutes().get(0).contains(DIRECTION.RIGHT)
						&& this.cars.get(j).getRoutes().get(0).contains(DIRECTION.STRAIGHT)
						&& this.cars.get(i).getWay().equals(WAY.NORTH) && !this.cars.get(j).getWay().equals(WAY.EAST)
				|| this.cars.get(i).getRoutes().get(0).contains(DIRECTION.RIGHT)
						&& this.cars.get(j).getRoutes().get(0).contains(DIRECTION.STRAIGHT)
						&& this.cars.get(i).getWay().equals(WAY.EAST) && !this.cars.get(j).getWay().equals(WAY.SOUTH)
				|| this.cars.get(i).getRoutes().get(0).contains(DIRECTION.RIGHT)
						&& this.cars.get(j).getRoutes().get(0).contains(DIRECTION.STRAIGHT)
						&& this.cars.get(i).getWay().equals(WAY.SOUTH) && !this.cars.get(j).getWay().equals(WAY.WEST)
				|| this.cars.get(i).getRoutes().get(0).contains(DIRECTION.RIGHT)
						&& this.cars.get(j).getRoutes().get(0).contains(DIRECTION.LEFT)
						&& this.cars.get(i).getWay().equals(WAY.SOUTH) && !this.cars.get(j).getWay().equals(WAY.NORTH)
				|| this.cars.get(i).getRoutes().get(0).contains(DIRECTION.RIGHT)
						&& this.cars.get(j).getRoutes().get(0).contains(DIRECTION.LEFT)
						&& this.cars.get(i).getWay().equals(WAY.NORTH) && !this.cars.get(j).getWay().equals(WAY.SOUTH)
				|| this.cars.get(i).getRoutes().get(0).contains(DIRECTION.RIGHT)
						&& this.cars.get(j).getRoutes().get(0).contains(DIRECTION.LEFT)
						&& this.cars.get(i).getWay().equals(WAY.WEST) && !this.cars.get(j).getWay().equals(WAY.EAST)
				|| this.cars.get(i).getRoutes().get(0).contains(DIRECTION.RIGHT)
						&& this.cars.get(j).getRoutes().get(0).contains(DIRECTION.LEFT)
						&& this.cars.get(i).getWay().equals(WAY.EAST) && !this.cars.get(j).getWay().equals(WAY.WEST)
				|| this.cars.get(i).getRoutes().get(0).contains(DIRECTION.LEFT)
						&& this.cars.get(j).getRoutes().get(0).contains(DIRECTION.LEFT)
						&& this.cars.get(i).getWay().equals(WAY.EAST) && this.cars.get(j).getWay().equals(WAY.WEST)
				|| this.cars.get(i).getRoutes().get(0).contains(DIRECTION.LEFT)
						&& this.cars.get(j).getRoutes().get(0).contains(DIRECTION.LEFT)
						&& this.cars.get(i).getWay().equals(WAY.NORTH) && this.cars.get(j).getWay().equals(WAY.SOUTH)
				|| this.cars.get(i).getRoutes().get(0).contains(DIRECTION.STRAIGHT)
						&& this.cars.get(j).getRoutes().get(0).contains(DIRECTION.STRAIGHT)
						&& this.cars.get(i).getWay().equals(WAY.EAST) && this.cars.get(j).getWay().equals(WAY.WEST)
				|| this.cars.get(i).getRoutes().get(0).contains(DIRECTION.STRAIGHT)
						&& this.cars.get(j).getRoutes().get(0).contains(DIRECTION.STRAIGHT)
						&& this.cars.get(i).getWay().equals(WAY.NORTH) && this.cars.get(j).getWay().equals(WAY.SOUTH);
	}

	@Override
	public String toString() {
		return String.format("FourWaysJunctionConfig [junction=%s, cars=%s]", this.junction, this.cars);
	}

}

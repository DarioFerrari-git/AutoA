/**
 * 
 */
package sm.arg.intersection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sm
 *
 */
public final class OneJunctionSimulation {

	private final SmartJunction junction;
	private final List<CrossingCar> cars;

	/**
	 * To be used before {@link sm.arg.intersection.OneJunctionSimulation#addCar()}
	 * Given RSUs are replicated on each road
	 * 
	 * @param junctionName
	 * @param policy
	 * @param rsus
	 * @return
	 */
	public OneJunctionSimulation(String junctionName, CrossingPolicy policy, RSU<?>... rsu) {
		Map<WAY, SmartRoad> roads = new HashMap<>();
		List<DIRECTION> lanes = Arrays.asList(DIRECTION.values()); // each road has all 3 lanes
		List<RSU<?>> rsus = Arrays.asList(rsu); // each road has all given RSUs
		for (WAY way : WAY.values()) {
			roads.put(way, new SmartRoad(new Road(way.name(), lanes), rsus));
		}
		this.junction = new SmartJunction(junctionName, roads, policy);
		this.cars = new ArrayList<>();
	}

	/**
	 * To be used after
	 * {@link sm.arg.intersection.OneJunctionSimulation#setup4ways()} roadname must
	 * exist in junction
	 * 
	 * @param car
	 * @param roadName
	 * @return
	 * @throws NoSuitableRSUException 
	 */
	public OneJunctionSimulation addCar(UrgentCar car, String roadName) throws NoSuitableRSUException {
		Double d = null;
		for (WAY way : this.junction.getRoads().keySet()) {
			if (this.junction.getRoads().get(way) != null
					&& this.junction.getRoads().get(way).getRoad().getName().equals(roadName)) {
				for (RSU<?> rsu : this.junction.getRoads().get(way).getRsus()) {
					if (rsu.getType().isAssignableFrom(DistanceRSU.class)) {
						d = rsu.getMeasurement();
					} else {
						throw new NoSuitableRSUException("No RSU assignable from DistanceRSU found", this.junction.getRoads().get(way).getRsus());
					}
				}
				this.cars.add(new CrossingCar(
						car, 
						way, 
						STATUS.APPROACHING, 
						d));
			}
		}
		return this;
	}

}

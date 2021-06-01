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

/**
 * @author sm
 *
 */
public final class FourWaysJunctionConfig {

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
	public FourWaysJunctionConfig(String junctionName, CrossingPolicy policy, RSU<?>... rsu) {
		Map<WAY, SmartRoad> roads = new HashMap<>();
		List<DIRECTION> lanes = Arrays.asList(DIRECTION.values()); // each road has all 3 lanes
		List<RSU<?>> rsus = Arrays.asList(rsu); // each road has all given RSUs TODO RSU name no longer unique!
		for (WAY way : WAY.values()) {
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
	public FourWaysJunctionConfig addCar(UrgentCar car, String roadName) {
		Double d = null;
		for (WAY way : this.junction.getRoads().keySet()) {
			if (this.junction.getRoads().get(way) != null
					&& this.junction.getRoads().get(way).getRoad().getName().equals(roadName)) {
				for (RSU<?> rsu : this.junction.getRoads().get(way).getRsus()) {
					if (rsu instanceof DistanceRSU && rsu.getType().isAssignableFrom(Double.class)) {
						d = rsu.getMeasurement();
					} else {
						log.warn("No RSU instanceof DistanceRSU and assignable from Double found: %s", this.junction.getRoads().get(way).getRsus());
						d = Double.NaN;
//						throw new NoSuitableRSUException("No RSU instanceof DistanceRSU and assignable from Double found", this.junction.getRoads().get(way).getRsus());
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

	@Override
	public String toString() {
		return String.format("FourWaysJunctionConfig [junction=%s, cars=%s]", junction, cars);
	}
	
}

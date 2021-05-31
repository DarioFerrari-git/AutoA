/**
 * 
 */
package sm.arg.intersection;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sm
 *
 */
public final class SmartRoad {

	private final Road road;
	/**
	 * given {@link sm.arg.intersection.RSU#getMeasurement()} implementation, this
	 * can be anything extending Object
	 */
	private final List<RSU<?>> rsus;

	/**
	 * @param road
	 * @param rsus
	 */
	public SmartRoad(Road road, List<RSU<?>> rsus) {
		this.road = road;
		this.rsus = rsus;
	}

	/**
	 * @return the rsus
	 */
	public List<RSU<?>> getRsus() {
		return rsus;
	}

	public SmartRoad addRsu(RSU<?> rsu) {
		this.rsus.add(rsu);
		return this;
	}

	public int nRsus() {
		return this.rsus.size();
	}

	public List<Class<?>> rsusTypes() {
		return this.rsus.stream().map(rsu -> rsu.getType()).collect(Collectors.toList());
	}

	/**
	 * @return the road
	 */
	public Road getRoad() {
		return road;
	}

	@Override
	public String toString() {
		return String.format("SmartRoad [road=%s, rsus=%s]", road, rsus);
	}

}

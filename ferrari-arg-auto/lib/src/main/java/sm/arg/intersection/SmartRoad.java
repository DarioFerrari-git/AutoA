/**
 * 
 */
package sm.arg.intersection;

import java.util.List;

/**
 * @author sm
 *
 */
public final class SmartRoad<T extends RSU<?>> {

	private final Road road;
	private List<T> rsus;

	/**
	 * @param road
	 * @param rsus
	 */
	public SmartRoad(Road road, List<T> rsus) {
		this.road = road;
		this.rsus = rsus;
	}

	/**
	 * @return the rsus
	 */
	public List<T> getRsus() {
		return rsus;
	}

	/**
	 * @param rsus the rsus to set
	 */
	public void setRsus(List<T> rsus) {
		this.rsus = rsus;
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

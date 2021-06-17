/**
 * 
 */
package sm.intersection;

import java.util.Map;
import java.util.Set;

/**
 * @author sm
 *
 */
public final class SmartJunction {

	private final String name;
	private final Map<WAY, SmartRoad> roads;
	private CrossingPolicy policy;

	/**
	 * @param name
	 * @param roads
	 */
	public SmartJunction(String name, Map<WAY, SmartRoad> roads, CrossingPolicy policy) {
		this.name = name;
		this.roads = roads;
		this.policy = policy;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the roads
	 */
	public Map<WAY, SmartRoad> getRoads() {
		return roads;
	}

	public int nRoads() {
		return this.roads.size();
	}

	public Set<WAY> ways() {
		return this.roads.keySet();
	}

	public CrossingPolicy getPolicy() {
		return this.policy;
	}

	@Override
	public String toString() {
		return String.format("Junction [name=%s, roads=%s, policy=%s]", name, roads, policy);
	}

}

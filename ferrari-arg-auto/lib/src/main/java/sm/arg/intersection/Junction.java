/**
 * 
 */
package sm.arg.intersection;

import java.util.Map;

/**
 * @author sm
 *
 */
public final class Junction {

	private final String name;
	private final Map<WAY, Road> roads;

	/**
	 * @param name
	 * @param roads
	 */
	public Junction(String name, Map<WAY, Road> roads) {
		this.name = name;
		this.roads = roads;
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
	public Map<WAY, Road> getRoads() {
		return roads;
	}

	@Override
	public String toString() {
		return String.format("Junction [name=%s, roads=%s]", name, roads);
	}

}

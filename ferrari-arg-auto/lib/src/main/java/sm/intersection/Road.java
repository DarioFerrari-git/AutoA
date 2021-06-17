/**
 * 
 */
package sm.intersection;

import java.util.List;

/**
 * @author sm
 *
 */
public final class Road {

	private final String name;
	private final List<DIRECTION> lanes;

	/**
	 * @param name
	 * @param lanes
	 */
	public Road(String name, List<DIRECTION> lanes) {
		this.name = name;
		this.lanes = lanes;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the lanes
	 */
	public List<DIRECTION> getLanes() {
		return lanes;
	}
	
	public int nLanes() {
		return lanes.size();
	}

	@Override
	public String toString() {
		return String.format("Road [name=%s, lanes=%s]", name, lanes);
	}

}

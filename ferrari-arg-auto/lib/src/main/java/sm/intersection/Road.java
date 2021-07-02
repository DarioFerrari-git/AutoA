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
    public Road(final String name, final List<DIRECTION> lanes) {
        this.name = name;
        this.lanes = lanes;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the lanes
     */
    public List<DIRECTION> getLanes() {
        return this.lanes;
    }

    public int nLanes() {
        return this.lanes.size();
    }

    @Override
    public String toString() {
        return String.format("Road [name=%s, lanes=%s]", this.name, this.lanes);
    }

}

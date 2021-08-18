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
    private final CrossingPolicy policy;

    /**
     * @param name
     * @param roads
     */
    public SmartJunction(final String name, final Map<WAY, SmartRoad> roads, final CrossingPolicy policy) {
        this.name = name; // MUST BE UNIQUE
        this.roads = roads;
        this.policy = policy;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the roads
     */
    public Map<WAY, SmartRoad> getRoads() {
        return this.roads;
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
        return String.format("Junction [name=%s, roads=%s, policy=%s]", this.name, this.roads, this.policy);
    }

}

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
    int nServed;
    int nArgProc;
    int nARused;

    /**
     * @param name  MUST BE UNIQUE
     * @param roads
     */
    public SmartJunction(final String name, final Map<WAY, SmartRoad> roads, final CrossingPolicy policy) {
        this.name = name; // MUST BE UNIQUE
        this.roads = roads;
        this.policy = policy;
        this.nServed = 0;
        this.nArgProc = 0;
        this.nARused = 0;
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

    public int incServed() {
        this.nServed = this.nServed + 1;
        return this.nServed;
    }

    public int incArgProc() {
        this.nArgProc = this.nArgProc + 1;
        return this.nArgProc;
    }
    
    public int incARused(int m) {
        this.nARused = this.nARused + m;
        return this.nARused;
    }

    public int getServed() {
        return this.nServed;
    }
    public int getARused() {
        return this.nServed;
    }

    public int getArgProc() {
        return this.nArgProc;
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

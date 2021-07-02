/**
 *
 */
package sm.intersection;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sm
 *
 */
public final class SmartRoad {

    private final Road road;
    /**
     * given {@link sm.intersection.RSU#getMeasurement()} implementation, this can
     * be anything extending Object
     */
    private final List<RSU<?>> rsus;

    /**
     * @param road
     * @param rsus
     */
    public SmartRoad(final Road road, final List<RSU<?>> rsus) {
        this.road = road;
        this.rsus = rsus;
    }

    /**
     * @return the rsus
     */
    public List<RSU<?>> getRsus() {
        return this.rsus;
    }

    public SmartRoad addRsu(final RSU<?> rsu) {
        this.rsus.add(rsu);
        return this;
    }

    public int nRsus() {
        return this.rsus.size();
    }

    public List<Class<?>> rsusTypes() {
        return this.rsus.stream().map(RSU::getType).collect(Collectors.toList());
    }

    /**
     * @return the road
     */
    public Road getRoad() {
        return this.road;
    }

    @Override
    public String toString() {
        return String.format("SmartRoad [road=%s, rsus=%s]", this.road, this.rsus);
    }

}

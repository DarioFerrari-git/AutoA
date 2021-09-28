/**
 *
 */
package sm.intersection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sm
 *
 */
public final class Car {

    private String name;
    private final Map<Integer, List<DIRECTION>> routes;
    private int currentRoute;
    private double speed;
    private int next;

    /**
     * @param name
     * @param speed in km/h
     */
    public Car(final String name, final double speed) {
        this.name = name;
        this.routes = new HashMap<>();
        this.currentRoute = -1;
        this.speed = speed;
        this.next = 0;
    }

    /**
     * @param name
     * @param routes
     * @param speed
     */
    public Car(final String name, final Map<Integer, List<DIRECTION>> routes, final double speed) {
        this.name = name;
        this.routes = routes;
        this.currentRoute = -1;
        this.speed = speed;
        this.next = 0;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the routes
     */
    public Map<Integer, List<DIRECTION>> getRoutes() {
        return this.routes;
    }

    public Car addRoute(final List<DIRECTION> route) {
        this.routes.put(this.next, route);
        if (this.next == 0) {
            this.currentRoute = 0;
        }
        this.next++;
        return this;
    }

    /**
     * @return the speed
     */
    public double getSpeed() {
        return this.speed;
    }

    public Car setSpeed(final double speed) {
        this.speed = speed;
        return this;
    }

    public Car setR(final List<DIRECTION> r) {
        this.routes.put(this.currentRoute, r);
        return this;
    }

    public Car setName(final String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return String.format("Car [name=%s, routes=%s, currentRoute=%d, speed=%s]", this.name, this.routes,
                this.currentRoute, this.speed);
    }

    /**
     *
     * @param route
     */
    public void setCurrentRoute(final int route) {
        this.currentRoute = route;
    }

    /**
     * @return the currentRoute
     */
    public int getCurrentRoute() {
        return this.currentRoute;
    }

    /**
     *
     * @param rank
     * @return
     */
    public List<DIRECTION> getRoute(final int rank) {
        return this.routes.get(rank);
    }

}

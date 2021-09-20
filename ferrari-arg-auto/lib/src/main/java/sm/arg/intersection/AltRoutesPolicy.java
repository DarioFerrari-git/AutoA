/**
 * 
 */
package sm.arg.intersection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tweetyproject.arg.aspic.syntax.AspicArgumentationTheory;
import org.tweetyproject.logics.pl.syntax.PlFormula;
import org.tweetyproject.logics.pl.syntax.Proposition;

import sm.arg.general.Debatable;
import sm.intersection.BasePolicy;
import sm.intersection.CrossingPolicy;

/**
 * @author sm
 *
 */
public final class AltRoutesPolicy implements CrossingPolicy, Debatable {

    private final BasePolicy policy;

    public AltRoutesPolicy(final String name) {
        this.policy = new BasePolicy(name);
    }

    @Override
    public List<CrossingCar> rightOfWay(CrossingCar car1, CrossingCar car2) {
        List<CrossingCar> cars = new ArrayList<>();
        final List<Integer> routes = new ArrayList<>();
        routes.addAll(car2.getRoutes().keySet());
        int ref = car2.getCurrentRouteRank();
        Collections.sort(routes);
        boolean altFound = false;
        altFound = loopRoutes(car1, car2, cars, routes, altFound);
        if (!altFound) {
            car2.setCurrentRoute(ref);
            routes.addAll(car1.getRoutes().keySet());
            ref = car1.getCurrentRouteRank();
            Collections.sort(routes);
            altFound = loopRoutes(car2, car1, cars, routes, altFound);
        }
        if (!altFound) {
            car1.setCurrentRoute(ref);
            final List<Integer> routes2 = new ArrayList<>();
            routes2.addAll(car2.getRoutes().keySet());
            int ref2 = car2.getCurrentRouteRank();
            Collections.sort(routes2);
            for (int p1 : routes) {
                car1.setCurrentRoute(p1);
                for (int p2 : routes2) {
                    car2.setCurrentRoute(p2);
                    if (Conflicts.noConflicts(car1, car2)) {
                        cars.add(car1);
                        cars.add(car2);
                        altFound = true;
                        break;
                    }
                }
            }
            if (!altFound) {
                car1.setCurrentRoute(ref);
                car2.setCurrentRoute(ref2);
                cars.add(car2);
            }
        }
        return cars;
    }

    private boolean loopRoutes(CrossingCar refCar, CrossingCar routingCar, List<CrossingCar> cars, final List<Integer> routes,
            boolean altFound) {
        for (int p : routes) {
            routingCar.setCurrentRoute(p);
            if (Conflicts.noConflicts(refCar, routingCar)) {
                cars.add(refCar);
                cars.add(routingCar);
                altFound = true;
                break;
            }
        }
        return altFound;
    }

    @Override
    public String getName() {
        return this.policy.getName();
    }

    @Override
    public List<Proposition> addAsArgTheory(AspicArgumentationTheory<PlFormula> t) {
        final Proposition a = new Proposition(this.getName());
        t.addAxiom(a);
        return Collections.singletonList(a);
    }

    @Override
    public String toString() {
        return String.format("AltRoutesPolicy [policy=%s]", policy);
    }

}

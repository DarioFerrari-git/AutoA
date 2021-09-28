/**
 *
 */
package sm.arg.intersection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger log = LoggerFactory.getLogger(AltRoutesPolicy.class);
    private final BasePolicy policy;

    public AltRoutesPolicy(final String name) {
        this.policy = new BasePolicy(name);
    }

    @Override
    public List<CrossingCar> rightOfWay(final CrossingCar car1, final CrossingCar car2) {

        final List<CrossingCar> cars = new ArrayList<>();
        final List<Integer> routes = new ArrayList<>();
        routes.addAll(car2.getRoutes().keySet());
        int ref = car2.getCurrentRouteRank();
        Collections.sort(routes);
        //        this.log.debug("{} routes: {}", car2.getName(), routes);
        boolean altFound = false;
        altFound = this.loopRoutes(car1, car2, cars, routes, altFound);
        if (!altFound) {
            car2.setCurrentRoute(ref);
            routes.clear();
            routes.addAll(car1.getRoutes().keySet());
            ref = car1.getCurrentRouteRank();
            Collections.sort(routes);
            //            this.log.debug("{} routes: {}", car1.getName(), routes);
            altFound = this.loopRoutes(car2, car1, cars, routes, altFound);
        }
        if (!altFound) {
            car1.setCurrentRoute(ref);
            final List<Integer> routes2 = new ArrayList<>();
            routes2.addAll(car2.getRoutes().keySet());
            final int ref2 = car2.getCurrentRouteRank();
            Collections.sort(routes2);
            for (final int p1 : routes) {
                car1.setCurrentRoute(p1);
                for (final int p2 : routes2) {
                    car2.setCurrentRoute(p2);
                    //                    this.log.debug("last effort, trying {} {} with {} {}", car1.getName(), car1.getCurrentRoutePath(),
                    //                            car2.getName(), car2.getCurrentRoutePath());
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
        for (final CrossingCar car : cars) {
            //            this.log.debug("<{}> is pursuing route {}: {}", car.getName(), car.getCurrentRouteRank(), car.getCurrentRoutePath());
        }
        return cars;
    }

    private boolean loopRoutes(final CrossingCar refCar, final CrossingCar routingCar, final List<CrossingCar> cars,
            final List<Integer> routes, boolean altFound) {
        if (!routingCar.isFrozen()) {
            for (final int p : routes) {
                routingCar.setCurrentRoute(p);
                //            this.log.debug("{} current route: {} (against {})", routingCar.getName(), routingCar.getCurrentRoutePath(), refCar.getCurrentRoutePath());
                if (Conflicts.noConflicts(refCar, routingCar) || Conflicts.noConflicts(routingCar, refCar)) {
                    //                this.log.debug("YEEE");
                    cars.add(refCar);
                    cars.add(routingCar);
                    altFound = true;

                    break;
                }
            }
        }
        return altFound;
    }

    @Override
    public String getName() {
        return this.policy.getName();
    }

    @Override
    public List<Proposition> addAsArgTheory(final AspicArgumentationTheory<PlFormula> t) {
        final Proposition a = new Proposition(this.getName());
        t.addAxiom(a);
        return Collections.singletonList(a);
    }

    @Override
    public String toString() {
        return String.format("AltRoutesPolicy [policy=%s]", this.policy);
    }

}

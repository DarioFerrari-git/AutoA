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
        final List<Integer> routes1 = new ArrayList<>();
        routes1.addAll(car1.getRoutes().keySet());
        final List<Integer> routes2 = new ArrayList<>();
        routes2.addAll(car2.getRoutes().keySet());
        int ref1 = car1.getCurrentRouteRank();
        int ref2 = car2.getCurrentRouteRank();
        Collections.sort(routes1);
        for (int p1 : routes1) {
            for (int p2 : routes2) {
                if (Conflicts.noConflicts(car1, car2)) {
                    cars.add(car1);
                    cars.add(car2);
                }
            }
        }
        return cars;
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

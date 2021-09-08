/**
 *
 */
package sm.arg.intersection;

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
public final class UrgencyPolicy implements CrossingPolicy, Debatable {

    private final BasePolicy policy;

    public UrgencyPolicy(final String name) {
        this.policy = new BasePolicy(name);
    }

    /**
     * In case of a tie, car2 wins
     */
    @Override
    public List<CrossingCar> rightOfWay(final CrossingCar car1, final CrossingCar car2) {
        return car1.getCar().getUrgency() > car2.getCar().getUrgency() ? Collections.singletonList(car1)
                : Collections.singletonList(car2);
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
        return String.format("UrgencyPolicy [policy=%s]", this.policy);
    }
}

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
public final class NumArgsPolicy implements CrossingPolicy, Debatable {

	private final BasePolicy policy;

	public NumArgsPolicy(final String name) {
		this.policy = new BasePolicy(name);
	}

	/**
	 * In case of a tie car2 wins
	 */
	@Override
	public CrossingCar rightOfWay(CrossingCar car1, CrossingCar car2) {
		int args1 = 0;
		int args2 = 0;
		if (car1.getTimeToCross() < car2.getTimeToCross()) {
			args1++;
		} else {
			args2++;
		}
		if (car1.getCar().getCar().getRoutes().size() > car2.getCar().getCar().getRoutes().size()) {
			args2++;
		} else {
			args1++;
		}
		if (car1.getCar().getCar().getSpeed() > car2.getCar().getCar().getSpeed()) {
			args1++;
		} else {
			args2++;
		}
		return args1 > args2 ? car1 : car2;
	}

	@Override
	public String getName() {
		return this.policy.getName();
	}

	@Override
	public List<Proposition> addAsArgTheory(final AspicArgumentationTheory<PlFormula> t) {
		Proposition a = new Proposition(getName());
		t.addAxiom(a);
		return Collections.singletonList(a);
	}

	@Override
	public String toString() {
		return String.format("NumArgsPolicy [policy=%s]", policy);
	}

}

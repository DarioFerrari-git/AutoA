/**
 * 
 */
package sm.arg.intersection;

import org.tweetyproject.arg.aspic.syntax.AspicArgumentationTheory;
import org.tweetyproject.logics.pl.syntax.PlFormula;
import org.tweetyproject.logics.pl.syntax.Proposition;

/**
 * @author sm
 *
 */
public final class NumArgsPolicy implements CrossingPolicy {

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

	public String getName() {
		return "NumArgsPolicy";
	}

	public Proposition addAsPropAxiom(final AspicArgumentationTheory<PlFormula> t) {
		Proposition a = new Proposition(getName());
		t.addAxiom(a);
		return a;
	}
}

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
import sm.intersection.CrossingPolicy;

/**
 * @author sm
 *
 */
public final class UrgencyPolicy implements CrossingPolicy, Debatable {

	@Override
	public CrossingCar rightOfWay(CrossingCar car1, CrossingCar car2) {
		return car1.getCar().getUrgency() > car2.getCar().getUrgency() ? car1 : car2;
	}

	public String getName() {
		return "UrgencyPolicy";
	}

	@Override
	public List<Proposition> addAsArgTheory(final AspicArgumentationTheory<PlFormula> t) {
		Proposition a = new Proposition(getName());
		t.addAxiom(a);
		return Collections.singletonList(a);
	}
}

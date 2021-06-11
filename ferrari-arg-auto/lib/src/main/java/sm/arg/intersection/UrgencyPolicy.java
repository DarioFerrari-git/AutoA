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
public final class UrgencyPolicy implements CrossingPolicy {

	@Override
	public CrossingCar rightOfWay(CrossingCar car1, CrossingCar car2) {
		return car1.getCar().getUrgency() > car2.getCar().getUrgency() ? car1 : car2;
	}
	public String getName(){
		return "UrgencyPolicy";
	}
	public Proposition ArgumentPolicy(final AspicArgumentationTheory<PlFormula> t ) {
		Proposition a = new Proposition(getName());
		t.addAxiom(a);
		return a;
	}
}

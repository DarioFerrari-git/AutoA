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
public final class DistanceRSU implements RSU<Double> {

	private final BaseRSU rsu;
	private final double distance;

	/**
	 * @param rsu
	 * @param distance
	 */
	public DistanceRSU(BaseRSU rsu, double distance) {
		this.rsu = rsu;
		this.distance = distance;
	}

	/**
	 * @return the rsu
	 */
	public BaseRSU getRsu() {
		return rsu;
	}

	@SuppressWarnings("unchecked") // there will be nothing but Double here
	@Override
	public Double getMeasurement() {
		return distance;
	}

	@Override
	public String toString() {
		return String.format("PositionRSU [rsu=%s, distance=%s]", rsu, distance);
	}

	public Proposition addAsPropAxiom(final AspicArgumentationTheory<PlFormula> t) { // TODO threshold as param
		Proposition a = null;
		if (rsu.getConfidence() > 0.5) {
			a = new Proposition("RSU_trustworthy");
			t.addAxiom(a);
		} else {
			a = new Proposition("RSU_untrustworthy");
			t.addAxiom(a);
		}
		return a;
	}

	@Override
	public Class<Double> getType() {
		return Double.class;
	}

}

/**
 *
 */
package sm.arg.intersection;

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

	@Override
	public Class<Double> getType() {
		return Double.class;
	}

}

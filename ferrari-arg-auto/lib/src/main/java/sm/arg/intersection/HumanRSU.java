/**
 * 
 */
package sm.arg.intersection;

/**
 * @author sm
 *
 */
public final class HumanRSU implements RSU<Boolean> {

	private final BaseRSU rsu;
	private final boolean human;

	/**
	 * @param rsu
	 * @param human
	 */
	public HumanRSU(BaseRSU rsu, boolean human) {
		this.rsu = rsu;
		this.human = human;
	}

	/**
	 * @return the rsu
	 */
	public BaseRSU getRsu() {
		return rsu;
	}

	@SuppressWarnings("unchecked") // there will be nothing but Boolean here
	@Override
	public Boolean getMeasurement() {
		return human;
	}

	@Override
	public String toString() {
		return String.format("HumanRSU [rsu=%s, human=%s]", rsu, human);
	}

	@Override
	public Class<Boolean> getType() {
		return Boolean.class;
	}

}

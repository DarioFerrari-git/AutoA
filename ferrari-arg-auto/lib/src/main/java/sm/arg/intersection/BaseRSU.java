/**
 * 
 */
package sm.arg.intersection;

/**
 * @author sm
 *
 */
public final class BaseRSU {

	private final String name;
//	private final WAY position;
	private final double confidence;

	/**
	 * @param name
	 * @param position
	 * @param confidence
	 */
	public BaseRSU(String name, double confidence) {
		this.name = name;
//		this.position = position;
		this.confidence = confidence;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the position
	 */
//	public WAY getPosition() {
//		return position;
//	}

	/**
	 * @return the confidence
	 */
	public double getConfidence() {
		return confidence;
	}

	@Override
	public String toString() {
		return String.format("RSU [name=%s, confidence=%s]", name, confidence);
	}

}

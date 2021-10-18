/**
 *
 */
package sm.intersection;

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
    public BaseRSU(final String name, final double confidence) {
        this.name = name;
        //		this.position = position;
        this.confidence = confidence;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
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
        return this.confidence;
    }

    @Override
    public String toString() {
        return String.format("RSU [name=%s, confidence=%s]", this.name, this.confidence);
    }

}

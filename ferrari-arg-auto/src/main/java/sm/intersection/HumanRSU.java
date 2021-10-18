/**
 *
 */
package sm.intersection;

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
    public HumanRSU(final BaseRSU rsu, final boolean human) {
        this.rsu = rsu;
        this.human = human;
    }

    /**
     * @return the rsu
     */
    @Override
    public BaseRSU getRsu() {
        return this.rsu;
    }

    @SuppressWarnings("unchecked") // there will be nothing but Boolean here
    @Override
    public Boolean getMeasurement() {
        return this.human;
    }

    @Override
    public String toString() {
        return String.format("HumanRSU [rsu=%s, human=%s]", this.rsu, this.human);
    }

    @Override
    public Class<Boolean> getType() {
        return Boolean.class;
    }

    @Override
    public double getConfidence() {
        return this.rsu.getConfidence();
    }

}

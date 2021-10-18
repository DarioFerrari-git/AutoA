/**
 *
 */
package sm.intersection;

/**
 * @author sm
 *
 */
public final class BasePolicy {

    private final String name;

    public BasePolicy(final String name) {
        this.name = name.toLowerCase();
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return String.format("BasePolicy [name=%s]", this.name);
    }

}

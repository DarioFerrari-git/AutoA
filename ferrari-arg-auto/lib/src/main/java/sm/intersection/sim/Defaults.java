/**
 *
 */
package sm.intersection.sim;

/**
 * @author sm
 *
 */
public final class Defaults {

    public static final int MIN_SPEED = 10;
    public static final int MAX_SPEED = 50;
    public static final int MIN_URGENCY = 0;
    public static final int MAX_URGENCY = 1;
    public static final int SAFETY_DISTANCE_SOFT = 25; // RSU NORMALLY PLACED 2 * this
    public static final int SAFETY_DISTANCE_HARD = 10;
    public static final double ACCELERATION = 1.25;
    public static final double DECELERATION_SOFT = 0.75;
    public static final double DECELERATION_HARD = 0.5;
    public static final int MAX_ROUTE_DEPTH = 4; // MUST BE > 1
    public static final double P_ADD_DEPTH = 0.5;
    public static final int MAX_ROUTES = 4; // MUST BE > 1
    public static final double P_ADD_NEW_ROUTE = 0.5;

}

/**
 *
 */
package sm.intersection.sim;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Properties;

/**
 * @author sm
 *
 */
public final class Defaults {

    private static final String MIN_SPEED_P = "MIN_SPEED";
    private static final String MAX_SPEED_P = "MAX_SPEED";
    private static final String MIN_URGENCY_P = "MIN_URGENCY";
    private static final String MAX_URGENCY_P = "MAX_URGENCY";
    private static final String SAFETY_DISTANCE_SOFT_P = "SAFETY_DISTANCE_SOFT";
    private static final String SAFETY_DISTANCE_HARD_P = "SAFETY_DISTANCE_HARD";
    private static final String ACCELERATION_P = "ACCELERATION";
    private static final String DECELERATION_SOFT_P = "DECELERATION_SOFT";
    private static final String DECELERATION_HARD_P = "DECELERATION_HARD";
    private static final String MAX_ROUTE_DEPTH_P = "MAX_ROUTE_DEPTH";
    private static final String P_ADD_DEPTH_P = "P_ADD_DEPTH";
    private static final String MAX_ROUTES_P = "MAX_ROUTES";
    private static final String P_ADD_NEW_ROUTE_P = "P_ADD_NEW_ROUTE";
    
    public static int MIN_SPEED = 10;
    public static int MAX_SPEED = 50;
    public static int MIN_URGENCY = 0;
    public static int MAX_URGENCY = 1;
    public static int SAFETY_DISTANCE_SOFT = 25; // RSU NORMALLY PLACED 2 * this
    public static int SAFETY_DISTANCE_HARD = 10;
    public static double ACCELERATION = 1.25;
    public static double DECELERATION_SOFT = 0.75;
    public static double DECELERATION_HARD = 0.5;
    public static int MAX_ROUTE_DEPTH = 4; // MUST BE > 1
    public static double P_ADD_DEPTH = 0.5;
    public static int MAX_ROUTES = 4; // MUST BE > 1
    public static double P_ADD_NEW_ROUTE = 0.5;
    
    private static Defaults INSTANCE;
    
    private Defaults() {}
    
    public static Defaults getInstance(String path) throws FileNotFoundException, IOException {
        if (INSTANCE == null) {
            Properties simProps = new Properties();
            simProps.load(new FileInputStream(path));
            MIN_SPEED = Integer.parseInt(simProps.getProperty(MIN_SPEED_P));
            MAX_SPEED = Integer.parseInt(simProps.getProperty(MAX_SPEED_P));
            MIN_URGENCY = Integer.parseInt(simProps.getProperty(MIN_URGENCY_P));
            MAX_URGENCY = Integer.parseInt(simProps.getProperty(MAX_URGENCY_P));
            SAFETY_DISTANCE_SOFT = Integer.parseInt(simProps.getProperty(SAFETY_DISTANCE_SOFT_P));
            SAFETY_DISTANCE_HARD = Integer.parseInt(simProps.getProperty(SAFETY_DISTANCE_HARD_P));
            ACCELERATION = Double.parseDouble(simProps.getProperty(ACCELERATION_P));
            DECELERATION_SOFT = Double.parseDouble(simProps.getProperty(DECELERATION_SOFT_P));
            DECELERATION_HARD = Double.parseDouble(simProps.getProperty(DECELERATION_HARD_P));
            MAX_ROUTE_DEPTH = Integer.parseInt(simProps.getProperty(MAX_ROUTE_DEPTH_P));
            P_ADD_DEPTH = Double.parseDouble(simProps.getProperty(P_ADD_DEPTH_P));
            MAX_ROUTES = Integer.parseInt(simProps.getProperty(MAX_ROUTES_P));
            P_ADD_NEW_ROUTE = Double.parseDouble(simProps.getProperty(P_ADD_NEW_ROUTE_P));
            INSTANCE = new Defaults();
        }
        return INSTANCE;
    }
    
    @Override
    public String toString() {
        String res = "Defaults [ ";
        for (Field f: this.getClass().getFields()) {
            if (Modifier.isPublic(f.getModifiers())) {
                try {
                    res += String.format("%s=%s, ", f.getName(), f.get(this));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return res + "]";
    }

}

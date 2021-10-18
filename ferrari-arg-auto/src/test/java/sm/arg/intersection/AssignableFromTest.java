/**
 * 
 */
package sm.arg.intersection;

import java.util.ArrayList;
import java.util.List;

import sm.arg.general.Debatable;
import sm.intersection.BaseRSU;
import sm.intersection.RSU;

/**
 * @author sm
 *
 */
public final class AssignableFromTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        RSU<?> rsu = new DistanceRSU(new BaseRSU("rsu1", 0.1), 10);
        List<RSU<?>> rsus = new ArrayList<>();
        rsus.add(rsu);
        for (RSU<?> what : rsus) {
            if (what.getClass().isAssignableFrom(Debatable.class)) {
                System.out.println("DistanceRSU is AssignableFrom(Debatable.class)");
            } else if (Debatable.class.isAssignableFrom(what.getClass())) {
                System.out.println("Debatable is AssignableFrom(DistanceRSU.class)");
            } else {
                System.out.println("Non of the above");
            }
        }
    }

}

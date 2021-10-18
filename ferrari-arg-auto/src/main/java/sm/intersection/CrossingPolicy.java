/**
 *
 */
package sm.intersection;

import java.util.List;

import sm.arg.intersection.CrossingCar;

/**
 * @author sm
 *
 */
public interface CrossingPolicy {

    List<CrossingCar> rightOfWay(CrossingCar car1, CrossingCar car2);

    String getName();
}

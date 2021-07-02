/**
 *
 */
package sm.intersection;

import sm.arg.intersection.CrossingCar;

/**
 * @author sm
 *
 */
public interface CrossingPolicy {

    CrossingCar rightOfWay(CrossingCar car1, CrossingCar car2);

    String getName();
}

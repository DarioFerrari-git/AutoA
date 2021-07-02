/**
 *
 */
package sm.intersection.sim;

import sm.arg.intersection.CrossingCar;
import sm.intersection.SmartJunction;

/**
 * @author sm
 *
 */
public interface VehiclesGenStrategy {

    CrossingCar newCar();

    VehiclesGenStrategy configJunction(final SmartJunction junction);

}

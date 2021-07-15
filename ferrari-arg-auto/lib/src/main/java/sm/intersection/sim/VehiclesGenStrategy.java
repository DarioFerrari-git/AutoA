/**
 *
 */
package sm.intersection.sim;

import java.util.List;

import sm.arg.intersection.CrossingCar;
import sm.intersection.SmartJunction;

/**
 * @author sm
 *
 */
public interface VehiclesGenStrategy {

    List<CrossingCar> newCars();

    VehiclesGenStrategy configJunction(final SmartJunction junction);
    
    void setSeed(final long seed);

}

/**
 * 
 */
package sm.arg.intersection;

/**
 * @author sm
 *
 */
public interface CrossingPolicy {

	CrossingCar rightOfWay(CrossingCar car1, CrossingCar car2);
	
}

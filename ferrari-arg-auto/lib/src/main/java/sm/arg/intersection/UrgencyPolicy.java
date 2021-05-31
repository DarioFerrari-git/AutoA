/**
 * 
 */
package sm.arg.intersection;

/**
 * @author sm
 *
 */
public final class UrgencyPolicy implements CrossingPolicy {

	@Override
	public CrossingCar rightOfWay(CrossingCar car1, CrossingCar car2) {
		return car1.getCar().getUrgency() > car2.getCar().getUrgency() ? car1 : car2;
	}

}

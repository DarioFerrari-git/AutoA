/**
 * 
 */
package sm.arg.intersection;

/**
 * @author sm
 *
 */
public interface OneJunctionSimulation {
	
	/**
	 * To be used before {@link sm.arg.intersection.OneJunctionSimulation#addCar()}
	 * Same RSUS on each road
	 * 
	 * @param policy
	 * @param rsus
	 * @return
	 */
	OneJunctionSimulation setup4ways(CrossingPolicy policy, RSU<?>... rsus);
	
	/**
	 * To be used after {@link sm.arg.intersection.OneJunctionSimulation#setup4ways()}
	 * 
	 * @param car
	 * @return
	 */
	OneJunctionSimulation addCar(UrgentCar car);

}

/**
 * 
 */
package sm.arg.intersection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tweetyproject.arg.aspic.syntax.AspicArgumentationTheory;
import org.tweetyproject.arg.aspic.syntax.DefeasibleInferenceRule;
import org.tweetyproject.arg.aspic.syntax.StrictInferenceRule;
import org.tweetyproject.logics.pl.syntax.Negation;
import org.tweetyproject.logics.pl.syntax.PlFormula;
import org.tweetyproject.logics.pl.syntax.Proposition;

/**
 * @author sm
 *
 */
public final class FourWaysJunctionConfig {

	private final Logger log = LoggerFactory.getLogger(FourWaysJunctionConfig.class);
	private final SmartJunction junction;
	private final List<CrossingCar> cars;

	/**
	 * Given RSUs are replicated on each road
	 * 
	 * @param junctionName
	 * @param policy
	 * @param rsus
	 * @return
	 */
	public FourWaysJunctionConfig(String junctionName, CrossingPolicy policy, RSU<?>... rsu) {
		Map<WAY, SmartRoad> roads = new HashMap<>();
		List<DIRECTION> lanes = Arrays.asList(DIRECTION.values()); // each road has all 3 lanes
		List<RSU<?>> rsus = Arrays.asList(rsu); // each road has all given RSUs TODO RSU name no longer unique!
		for (WAY way : WAY.values()) {
			roads.put(way, new SmartRoad(new Road(way.name(), lanes), rsus));
		}
		this.junction = new SmartJunction(junctionName, roads, policy);
		this.cars = new ArrayList<>();
	}

	/**
	 * roadname must exist in junction
	 * 
	 * @param car
	 * @param roadName
	 * @return
	 * @throws NoSuitableRSUException 
	 */
	public FourWaysJunctionConfig addCar(UrgentCar car, String roadName) {
		Double d = null;
		for (WAY way : this.junction.getRoads().keySet()) {
			if (this.junction.getRoads().get(way) != null
					&& this.junction.getRoads().get(way).getRoad().getName().equals(roadName)) {
				for (RSU<?> rsu : this.junction.getRoads().get(way).getRsus()) {
					if (rsu instanceof DistanceRSU && rsu.getType().isAssignableFrom(Double.class)) {
						d = rsu.getMeasurement();
					} else {
						log.warn("No RSU instanceof DistanceRSU and assignable from Double found: %s", this.junction.getRoads().get(way).getRsus());
						d = Double.NaN;
//						throw new NoSuitableRSUException("No RSU instanceof DistanceRSU and assignable from Double found", this.junction.getRoads().get(way).getRsus());
					}
				}
				this.cars.add(new CrossingCar(
						car, 
						way, 
						STATUS.APPROACHING, 
						d));
			}
		}
		return this;
	}

	/**
	 * @return the junction
	 */
	public SmartJunction getJunction() {
		return junction;
	}

	/**
	 * @return the cars
	 */
	public List<CrossingCar> getCars() {
		return cars;
	}
	
	
	public void Rules(final AspicArgumentationTheory<PlFormula> t) {
		Proposition a=null;
		Proposition b=null;
		Proposition c=null;
		Proposition d=null;
		Proposition e=new Proposition("PossibleIncident");
		Proposition f=null;
		
		
		final ArrayList<String> alreadyConsidered = new ArrayList<>();
        boolean Incident=true;
        boolean Problems=false;
        DefeasibleInferenceRule<PlFormula> r1 = new DefeasibleInferenceRule<>();
        StrictInferenceRule<PlFormula> r2 = new StrictInferenceRule<>();
		
		for(int i=0; i<cars.size();i++) {
		a = new Proposition(cars.get(i).getCar().getCar().getName());
		c=new Proposition(a+"_CorrectlyDetected");
		d=new Proposition(a+"_WronglyDetected");
		r1 = new DefeasibleInferenceRule<>();
		r2 = new StrictInferenceRule<>();
		
		if(junction.getRoads().get(cars.get(i).getWay()).getRsus().get(0).getRsu().getConfidence()<0.5) {
		b = new Proposition("RSU_untrustworthy");
		r1.setConclusion(d);
		r1.addPremise(b);
		r1.addPremise(a);
		t.addRule(r1);
		Problems=true;
		}
		else {b= new Proposition("RSU_trustworthy");
		r1.setConclusion(c);
		r1.addPremise(b);
		r1.addPremise(a);
		t.addRule(r1);
		}
		for(int j=0;j<cars.size();j++) {
		f=new Proposition(cars.get(j).getCar().getCar().getName());
			
			if(!cars.get(i).equals(cars.get(j))&&!alreadyConsidered.contains(cars.get(i).getCar().getCar().getName()+"0"+cars.get(j).getCar().getCar().getName())) {
				if((cars.get(i).getWay().equals(WAY.SOUTH)&&cars.get(j).getWay().equals(WAY.NORTH)&&!cars.get(i).getCar().getCar().getRoutes().get(0).toString().contains("LEFT")&&!cars.get(j).getCar().getCar().getRoutes().get(0).toString().contains("LEFT"))||
				   (cars.get(i).getWay().equals(WAY.EAST)&&cars.get(j).getWay().equals(WAY.WEST)&&!cars.get(i).getCar().getCar().getRoutes().get(0).toString().contains("LEFT")&&!cars.get(j).getCar().getCar().getRoutes().get(0).toString().contains("LEFT"))||
				   (cars.get(i).getWay().equals(WAY.NORTH)&&cars.get(j).getWay().equals(WAY.SOUTH)&&cars.get(i).getCar().getCar().getRoutes().get(0).toString().contains("LEFT")&&cars.get(j).getCar().getCar().getRoutes().get(0).toString().contains("LEFT"))||
				   (cars.get(i).getWay().equals(WAY.EAST)&&cars.get(j).getWay().equals(WAY.WEST)&&cars.get(i).getCar().getCar().getRoutes().get(0).toString().contains("LEFT")&&cars.get(j).getCar().getCar().getRoutes().get(0).toString().contains("LEFT"))||
				   (cars.get(i).getWay().equals(WAY.NORTH)&&cars.get(j).getWay().equals(WAY.EAST)&&cars.get(j).getCar().getCar().getRoutes().get(0).toString().contains("RIGHT"))||
				   (cars.get(i).getWay().equals(WAY.NORTH)&&cars.get(j).getWay().equals(WAY.EAST)&&cars.get(i).getCar().getCar().getRoutes().get(0).toString().contains("RIGHT")&&cars.get(j).getCar().getCar().getRoutes().get(0).toString().contains("LEFT"))||
				   (cars.get(i).getWay().equals(WAY.WEST)&&cars.get(j).getWay().equals(WAY.NORTH)&&cars.get(j).getCar().getCar().getRoutes().get(0).toString().contains("RIGHT"))||
				   (cars.get(i).getWay().equals(WAY.WEST)&&cars.get(j).getWay().equals(WAY.NORTH)&&cars.get(i).getCar().getCar().getRoutes().get(0).toString().contains("RIGHT")&&cars.get(j).getCar().getCar().getRoutes().get(0).toString().contains("LEFT"))||
				   (cars.get(i).getWay().equals(WAY.SOUTH)&&cars.get(j).getWay().equals(WAY.WEST)&&cars.get(j).getCar().getCar().getRoutes().get(0).toString().contains("RIGHT"))||
				   (cars.get(i).getWay().equals(WAY.SOUTH)&&cars.get(j).getWay().equals(WAY.WEST)&&cars.get(i).getCar().getCar().getRoutes().get(0).toString().contains("RIGHT")&&cars.get(j).getCar().getCar().getRoutes().get(0).toString().contains("LEFT"))||
				   (cars.get(i).getWay().equals(WAY.EAST)&&cars.get(j).getWay().equals(WAY.SOUTH)&&cars.get(j).getCar().getCar().getRoutes().get(0).toString().contains("RIGHT"))||
				   (cars.get(i).getWay().equals(WAY.EAST)&&cars.get(j).getWay().equals(WAY.SOUTH)&&cars.get(i).getCar().getCar().getRoutes().get(0).toString().contains("RIGHT")&&cars.get(j).getCar().getCar().getRoutes().get(0).toString().contains("LEFT"))){
					System.out.println(cars.get(i).getWay()+"->"+cars.get(i).getCar().getCar().getRoutes().get(0));
					System.out.println(cars.get(j).getWay()+"->"+cars.get(j).getCar().getCar().getRoutes().get(0));
					r2.setConclusion(new Negation(e));
					r2.addPremise(a);
					r2.addPremise(f);
					t.addRule(r2);
					alreadyConsidered.add(cars.get(j).getCar().getCar().getName()+"0"+cars.get(i).getCar().getCar().getName());
	                Incident=false;
				}}}}
		
		
		
		for(int i=0; i<cars.size();i++) {
		a=  new Proposition(cars.get(i).getCar().getCar().getName());
		b=  new Proposition(junction.getPolicy().Policyname());
		for(int j=0;j<cars.size();j++) {
		r1 = new DefeasibleInferenceRule<>();
		r2 = new StrictInferenceRule<>();
		f=new Proposition(cars.get(j).getCar().getCar().getName());
		if(Incident&&!cars.get(i).equals(cars.get(j))&&!alreadyConsidered.contains(cars.get(i).getCar().getCar().getName()+"x"+cars.get(j).getCar().getCar().getName())) {
		   if(Problems) {
					r1.setConclusion(e);
					r1.addPremise(a);
					r1.addPremise(f);
					t.addRule(r1);
					alreadyConsidered.add(cars.get(j).getCar().getCar().getName()+"x"+cars.get(i).getCar().getCar().getName());
		   }
		   if(!Problems&&cars.get(i).equals(junction.getPolicy().rightOfWay(cars.get(i), cars.get(j)))){
			   c=new Proposition(a+"_passesFirst");
			   r1.setConclusion(c);
			   r1.addPremise(b);
			   r1.addPremise(a);
			   t.add(r1);
			   
			   r2.setConclusion(new Negation(e));
			   r2.addPremise(c);
			   r2.addPremise(f);
			   t.add(r2);
		   }
		}
		} 
	 }
	}
	@Override
	public String toString() {
		return String.format("FourWaysJunctionConfig [junction=%s, cars=%s]", junction, cars);
	}
	
}

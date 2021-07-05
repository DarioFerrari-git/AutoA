/**
 * 
 */
package sm.arg.general;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tweetyproject.arg.aspic.syntax.AspicArgumentationTheory;
import org.tweetyproject.logics.pl.syntax.PlFormula;

import sm.arg.intersection.ArgKeys;
import sm.arg.intersection.CrossingCar;
import sm.intersection.CrossingPolicy;
import sm.intersection.sim.SingleJunctionSimulation;

/**
 * @author sm
 *
 */
public final class ArgumentationGraph {
    
    private final AspicArgumentationTheory<PlFormula> argGraph;
    private final Logger log = LoggerFactory.getLogger(ArgumentationGraph.class);
    
    public ArgumentationGraph(AspicArgumentationTheory<PlFormula> argTheory) {
        this.argGraph = argTheory;
    }
    
    public void graph2text(List<CrossingCar> cars,CrossingPolicy cr) {
        // TODO 
    	int nCars=1;
    	for(final CrossingCar car: cars ) {
    	this.log.info("Argumentation Theory -----Conclusion [Car {}]----->",nCars);   
    	System.out.printf("\t Car %s  in  %s  state is set  %s  going  %s \n", car.getName(), car.getState(), car.getWay(), car.getRoutes().get(0));
    	
    	Object[] a =this.argGraph.getConclusions().toArray();
    	System.out.println("\t\t--------------------");
    	for(int i=0; i<a.length;i++) {
    		if(a[i].toString().contains(car.getName())&&a[i].toString().contains("_")&&!a[i].toString().contains("RSU")) {
    	System.out.println("\t\t"+a[i]);
    }}
    	System.out.println("\t\t--------------------");
    	nCars++;
    	}
    	Object[]b =this.argGraph.getConclusions().toArray();
    	
    for(int i=0; i<b.length;i++) {
    	if(b[i].toString().contains("RSU")) {
    	this.log.info("Argumentation Theory -----Axiom [RSU]----->");  
        System.out.println("\t\t--------------------");
    	System.out.println("\t\t"+b[i]);
    	System.out.println("\t\t--------------------");
        }
    	if(b[i].toString().contains(cr.getName())) {
        	this.log.info("Argumentation Theory -----Axiom [Policy]----->");  
            System.out.println("\t\t--------------------");
        	System.out.println("\t\t"+b[i]);
        	System.out.println("\t\t--------------------");
            }
    	}
}
    }

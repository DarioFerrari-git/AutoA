/**
 * 
 */
package sm.arg.general;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tweetyproject.arg.aspic.syntax.AspicArgumentationTheory;
import org.tweetyproject.logics.pl.syntax.PlFormula;

import sm.arg.intersection.CrossingCar;
import sm.intersection.CrossingPolicy;

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

    public void graph2text(List<CrossingCar> cars, CrossingPolicy cr) {
        this.log.info("Argumentation Theory -----[START]----->");
        Object[] b = this.argGraph.getConclusions().toArray();
        boolean theresRsu;
        for (int i = 0; i < b.length; i++) {
            theresRsu = false;
            if (b[i].toString().contains("RSU")) {
                theresRsu = true;
                System.out.print("\t RSUs: ");
                System.out.printf("%s ", b[i]);
            }
            if (theresRsu) {
                System.out.println();
            }
            if (b[i].toString().contains(cr.getName())) {
                System.out.print("\t Policies: ");
                System.out.printf("<%s> ", b[i]);
            }
        }
        System.out.println("\t Cars: ");
//        int nCars = 1;
        for (final CrossingCar car : cars) {
            System.out.printf("\t\t <%s>: ", car.getName());
//            System.out.printf("\t Car %s in  %s  state is set  %s  going  %s \n", car.getName(), car.getState(),
//                    car.getWay(), car.getRoutes().get(0));
            Object[] a = this.argGraph.getConclusions().toArray();
//            System.out.println("\t\t--------------------");
            for (int i = 0; i < a.length; i++) {
                if (a[i].toString().contains(car.getName()) && a[i].toString().contains("_")
                        && !a[i].toString().contains("RSU")) {
                    System.out.printf("%s ", a[i]);
                }
            }
            System.out.println();
//            System.out.println("\t\t--------------------");
//            nCars++;
        }
        this.log.info("<----[END]------ Argumentation Theory");
    }
}

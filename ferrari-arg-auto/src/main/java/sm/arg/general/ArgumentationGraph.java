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

/**
 * @author sm
 *
 */
public final class ArgumentationGraph {

    private final AspicArgumentationTheory<PlFormula> argGraph;
    private final Logger log = LoggerFactory.getLogger(ArgumentationGraph.class);

    public ArgumentationGraph(final AspicArgumentationTheory<PlFormula> argTheory) {
        this.argGraph = argTheory;
    }

    public void graph2text(final List<CrossingCar> cars, final CrossingPolicy cr) {
        this.log.info("Argumentation Theory -----[START]----->");
        final Object[] b = this.argGraph.getConclusions().toArray();
        boolean theresRsu;
        for (final Object element : b) {
            theresRsu = false;
            if (element.toString().contains("RSU")) {
                theresRsu = true;
                System.out.print("\t RSUs: ");
                System.out.printf("%s ", element);
            }
            if (theresRsu) {
                System.out.println();
            }
            if (element.toString().contains(cr.getName())) {
                System.out.print("\t Policies: ");
                System.out.printf("<%s> ", element);
            }
        }
        System.out.println("\t Cars: ");
        //        int nCars = 1;
        for (final CrossingCar car : cars) {
            System.out.printf("\t\t <%s>: ", car.getName());
            //            System.out.printf("\t Car %s in  %s  state is set  %s  going  %s \n", car.getName(), car.getState(),
            //                    car.getWay(), car.getRoutes().get(0));
            final Object[] a = this.argGraph.getConclusions().toArray();
            //            System.out.println("\t\t--------------------");
            for (final Object element : a) {
                if (element.toString().contains(car.getName()) && element.toString().contains("_")
                        && !element.toString().contains("RSU")
                        && !element.toString().contains(ArgKeys.WaitDueTo + car.getName())
                        && !element.toString().equals(car.getName())) {
                    System.out.printf("%s ", element);
                }
            }
            System.out.println();
            //            System.out.println("\t\t--------------------");
            //            nCars++;
        }
        this.log.info("<----[END]------ Argumentation Theory");
    }
}

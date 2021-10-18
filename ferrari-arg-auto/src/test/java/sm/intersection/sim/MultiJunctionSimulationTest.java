/**
 * 
 */
package sm.intersection.sim;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sm.arg.intersection.DistanceRSU;
import sm.arg.intersection.FourWaysJunctionConfig;
import sm.arg.intersection.NumArgsPolicy;
import sm.intersection.BaseRSU;
import sm.intersection.Car;
import sm.intersection.DIRECTION;
import sm.intersection.JunctionMatrix;
import sm.intersection.SmartJunction;
import sm.intersection.UrgentCar;
import sm.intersection.WAY;

/**
 * @author sm
 *
 */
public class MultiJunctionSimulationTest {

    private MultiJunctionSimulation ms;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        UrgentCar car;
        int nCars = 0;
        List<Simulation> sims = new ArrayList<>();
        FourWaysJunctionConfig fourWays;
        SmartJunction[][] junctions = new SmartJunction[2][2];
        List<DIRECTION> route;
        /*
         * For each junction in network...
         */
        for (int r = 0; r < junctions.length; r++) {
            for (int c = 0; c < junctions[r].length; c++) {
                /*
                 * ...create 4 ways junction config...
                 */
                fourWays = new FourWaysJunctionConfig(String.format("4ways %d,%d", r, c), new NumArgsPolicy("numArgs"),
                        new DistanceRSU(new BaseRSU("rsu", 1), 50));
                /*
                 * ...and add it to the network...
                 */
                junctions[r][c] = fourWays.getJunction();
                /*
                 * ...and ONLY FOR (0,0) JUNCTION, for each WAY - DIRECTION combination...
                 */
                if (r == 0 && c == 0) {
                    for (WAY w : WAY.values()) {
                        for (DIRECTION d : DIRECTION.values()) {
                            car = new UrgentCar(new Car("car" + nCars, 50), 0);
                            nCars++;
                            route = new ArrayList<>();
                            route.add(d);
                            if (Math.random() > 0.5) { // randomly generate second direction for some route
                                DIRECTION.setSeed(1);
                                route.add(DIRECTION.random());
                            }
                            car.getCar().addRoute(route);
                            /*
                             * ...add car to junction...
                             */
                            fourWays.addCar(car, w.toString());
                        }
                    }
                }
                /*
                 * ...finally create simulation!
                 */
                sims.add(new SingleJunctionSimulation(junctions[r][c], fourWays.getCars(), 1));
            }
        }
        this.ms = new MultiJunctionSimulation(new JunctionMatrix(junctions), sims);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        this.ms = null;
    }

    /**
     * Test method for
     * {@link sm.intersection.sim.MultiJunctionSimulation#step(java.lang.Boolean)}.
     */
    @Test
    public final void testStep() {
        for (int i = 0; i < 10; i++) {
            this.ms.step(true);
        }
    }

}

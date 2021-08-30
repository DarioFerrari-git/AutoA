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
import sm.intersection.JunctionMatrix;
import sm.intersection.SmartJunction;

/**
 * @author sm
 *
 */
public class MultiJunctionAutoSimulationTest {

    private MultiJunctionAutoSimulation ms;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        List<Simulation> sims = new ArrayList<>();
        FourWaysJunctionConfig fourWays;
        SmartJunction[][] junctions = new SmartJunction[2][2];
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
                //                if (r == 0 && c == 0) {
                VehiclesGenStrategy strat = new FlatRouteMaxStrategy();
                strat.configJunction(fourWays.getJunction());
                strat.setSeed(1); // same seed = same random numbers
                //                }
                /*
                 * ...finally create simulation!
                 */
                sims.add(new SingleJunctionAutoSimulation(fourWays.getJunction(), 1, 3, 20, strat, 1)); // vehicles generated during first 3 steps
            }
        }
        this.ms = new MultiJunctionAutoSimulation(new JunctionMatrix(junctions), sims);
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
     * {@link sm.intersection.sim.MultiJunctionAutoSimulation#step(java.lang.Boolean)}.
     */
    @Test
    public final void testStep() {
        this.ms.step(true);
        this.ms.step(true);
    }

    /**
     * Test method for
     * {@link sm.intersection.sim.MultiJunctionAutoSimulation#go(java.lang.Boolean)}.
     */
    @Test
    public final void testGo() {
        this.ms.go(true);
    }

}

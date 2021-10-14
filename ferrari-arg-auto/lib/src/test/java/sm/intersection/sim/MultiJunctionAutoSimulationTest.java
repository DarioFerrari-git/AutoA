package sm.intersection.sim;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sm.arg.intersection.AltRoutesPolicy;
import sm.arg.intersection.DistanceRSU;
import sm.arg.intersection.FourWaysJunctionConfig;
import sm.intersection.BaseRSU;
import sm.intersection.JunctionMatrix;
import sm.intersection.SmartJunction;

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
        VehiclesGenStrategy strat = new DeepRouteConfStrategy();
        /*
         * For each junction in network...
         */
        for (int r = 0; r < junctions.length; r++) {
            for (int c = 0; c < junctions[r].length; c++) {
                /*
                 * ...create 4 ways junction config...
                 */
                fourWays = new FourWaysJunctionConfig(String.format("4ways %d,%d", r, c),
                        new AltRoutesPolicy("altPolicy"), new DistanceRSU(new BaseRSU("rsu", 1), 50));
                /*
                 * ...and add it to the network...
                 */
                junctions[r][c] = fourWays.getJunction();

                strat.configJunction(junctions[r][c]);
                strat.setSeed(3); // same seed = same random numbers
                if (r == 0 && c == 0)
                    sims.add(new SingleJunctionAutoSimulation(junctions[r][c], 1, 3, 20, strat, 1)); // vehicles generated during first 3 steps only junction[0][0]   
                else
                    sims.add(new SingleJunctionAutoSimulation(junctions[r][c], 0, 0, 20, strat, 1)); // vehicles generated during first 3 steps only junction[0][0]   

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

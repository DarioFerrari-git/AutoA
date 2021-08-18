/**
 * 
 */
package sm.intersection;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sm.arg.intersection.DistanceRSU;
import sm.arg.intersection.FourWaysJunctionConfig;
import sm.arg.intersection.NumArgsPolicy;

/**
 * @author sm
 *
 */
public class JunctionMatrixTest {
    
    private JunctionMatrix jm;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        FourWaysJunctionConfig fourWays;
        SmartJunction[][] junctions = new SmartJunction[4][4];
        for (int r = 0; r < junctions.length; r++) {
            for (int c = 0; c < junctions[r].length; c++) {
                fourWays = new FourWaysJunctionConfig(
                        String.format("4ways %d,%d", r, c), 
                        new NumArgsPolicy("numArgs"), 
                        new DistanceRSU(
                                new BaseRSU(
                                        "rsu", 
                                        1), 
                                50));
                junctions[r][c] = fourWays.getJunction();
            }
        }
        this.jm = new JunctionMatrix(junctions);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        this.jm = null;
    }

    /**
     * Test method for {@link sm.intersection.JunctionMatrix#getJunction(int, int)}.
     */
    @Test
    public final void testGetJunction() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link sm.intersection.JunctionMatrix#setJunction(int, int, sm.intersection.SmartJunction)}.
     */
    @Test
    public final void testSetJunction() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link sm.intersection.JunctionMatrix#next(int[], sm.intersection.WAY, sm.intersection.DIRECTION)}.
     */
    @Test
    public final void testNext() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link sm.intersection.JunctionMatrix#toString()}.
     */
    @Test
    public final void testToString() {
        System.out.println(this.jm.toString());
    }

}

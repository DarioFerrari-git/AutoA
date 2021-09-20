/**
 * 
 */
package sm.arg.intersection;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sm.intersection.Car;
import sm.intersection.DIRECTION;
import sm.intersection.STATUS;
import sm.intersection.UrgentCar;
import sm.intersection.WAY;

/**
 * @author sm
 *
 */
public class AltRoutesPolicyTest {
    
    private AltRoutesPolicy policy;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        this.policy = new AltRoutesPolicy("altRoutes");
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        this.policy = null;
    }

    /**
     * Test method for {@link sm.arg.intersection.AltRoutesPolicy#rightOfWay(sm.arg.intersection.CrossingCar, sm.arg.intersection.CrossingCar)}.
     */
    @Test
    public final void testRightOfWay() {
        Car c1 = new Car("car1", 50);
        List<DIRECTION> r0 = new ArrayList<>();
        r0.add(DIRECTION.STRAIGHT);
        List<DIRECTION> r1 = new ArrayList<>();
        r1.add(DIRECTION.STRAIGHT);
        c1.addRoute(r0);
        c1.addRoute(r1);
        UrgentCar u1 = new UrgentCar(c1, 1);
        CrossingCar cc1 = new CrossingCar(u1, WAY.WEST, STATUS.APPROACHING, 50);
        System.out.printf("Route: %d -> %s\n", cc1.getCurrentRouteRank(), cc1.getCurrentRoutePath());
        
        Car c2 = new Car("car2", 50);
        r0 = new ArrayList<>();
        r0.add(DIRECTION.STRAIGHT);
        r1 = new ArrayList<>();
        r1.add(DIRECTION.RIGHT);
        c2.addRoute(r0);
        c2.addRoute(r1);
        UrgentCar u2 = new UrgentCar(c2, 1);
        CrossingCar cc2 = new CrossingCar(u2, WAY.SOUTH, STATUS.APPROACHING, 50);
        System.out.printf("Route: %d -> %s\n", cc2.getCurrentRouteRank(), cc2.getCurrentRoutePath());
        
        List<CrossingCar> crossing = this.policy.rightOfWay(cc1, cc2);
        System.out.println(crossing);
        assertEquals(crossing.size(), 2);
    }

}

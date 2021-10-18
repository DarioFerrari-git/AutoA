/**
 * 
 */
package sm.intersection.sim;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sm.arg.intersection.CrossingCar;
import sm.arg.intersection.DistanceRSU;
import sm.arg.intersection.FourWaysJunctionConfig;
import sm.arg.intersection.NumArgsPolicy;
import sm.intersection.BaseRSU;

/**
 * @author sm
 *
 */
public class DeepRouteMaxStrategyTest {
    
    private static final int ITER = 1000;
    private DeepRouteMaxStrategy strat;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        this.strat = new DeepRouteMaxStrategy();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        this.strat = null;
    }

    /**
     * Test method for {@link sm.intersection.sim.DeepRouteMaxStrategy#newCars()}.
     */
    @Test
    public final void testNewCars() {
        FourWaysJunctionConfig config = new FourWaysJunctionConfig("junction1", new NumArgsPolicy("numArgsPolicy1"),
                new DistanceRSU(new BaseRSU("distance1", 1), 100));
        this.strat.configJunction(config.getJunction());
        this.strat.setSeed(1); // if needed, otherwise can skip
        List<CrossingCar> newCars = new ArrayList<>();
        List<CrossingCar> cars = new ArrayList<>();
        for (int i = 0; i < DeepRouteMaxStrategyTest.ITER; i++) {
            newCars.addAll(this.strat.newCars());
            System.out.println(newCars);
            for (CrossingCar car : newCars) {
                assertEquals(car.getRoutes().size(), 1);
                assertTrue(car.getCurrentRoutePath().size() <= Defaults.MAX_ROUTE_DEPTH);
                assertEquals(car.getCar().getCar().getSpeed(), Defaults.MAX_SPEED, 0.0001);
                assertEquals(car.getCar().getUrgency(), Defaults.MAX_URGENCY, 0.0001);
                assertTrue(this.strat.getJunction().getRoads().containsKey(car.getWay()));
                assertTrue(this.strat.getJunction().getRoads().get(car.getWay()) != null);
                for (CrossingCar otherCar : cars) {
                    assertNotEquals(car, otherCar);
                }
                cars.add(car);
                assertEquals(this.strat.getnCars(), cars.size());
            }
            newCars = new ArrayList<>();
        }
    }

}

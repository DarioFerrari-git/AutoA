package sm.intersection.sim;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

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

public class DeepRouteConfStrategyTest {

    
    private static final int ITER = 10;
    private DeepRouteConfStrategy strat;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        this.strat = new DeepRouteConfStrategy();
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
        for (int i = 0; i < DeepRouteConfStrategyTest.ITER; i++) {
            newCars.addAll(this.strat.newCars());
           // System.out.println(newCars.size());
            
            for (CrossingCar car : newCars) {
                assertTrue(car.getCar().getCar().getSpeed() < Defaults.MAX_SPEED);
                assertTrue(car.getCar().getCar().getSpeed() > Defaults.MIN_SPEED);
                assertTrue(car.getCar().getUrgency() < Defaults.MAX_URGENCY);
                assertTrue(car.getCar().getUrgency() > Defaults.MIN_URGENCY);
                assertTrue(this.strat.getJunction().getRoads().containsKey(car.getWay()));
                assertTrue(this.strat.getJunction().getRoads().get(car.getWay()) != null);
                for (CrossingCar otherCar : cars) {
                    assertNotEquals(car, otherCar);
                }
                cars.add(car);
               
               
            }   
            System.out.println(cars);
            assertEquals(this.strat.getnCars(), cars.size());
            newCars = new ArrayList<>();
        }
    }

}

package sm.intersection.sim;

import java.util.List;

import sm.arg.intersection.CrossingCar;
import sm.intersection.SmartJunction;

public interface Simulation {

    List<CrossingCar> step(Boolean log /* , final long steps */);

    void go(Boolean log);

    void pause();

    void logSituation();

    boolean isGoing();

    /**
     * @return the junction
     */
    List<SmartJunction> getJunctions();

    /**
     * @return the cars
     */
    List<CrossingCar> getCars();

    Simulation addCars(List<CrossingCar> cars);

    /**
     * @return the step
     */
    double getStep();

    /**
     * @return the steps
     */
    long getSteps();

    long getMaxSteps();

}
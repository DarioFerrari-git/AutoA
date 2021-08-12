/**
 *
 */
package sm.intersection.sim;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tweetyproject.arg.aspic.reasoner.SimpleAspicReasoner;
import org.tweetyproject.arg.aspic.ruleformulagenerator.PlFormulaGenerator;
import org.tweetyproject.arg.aspic.syntax.AspicArgumentationTheory;
import org.tweetyproject.arg.dung.reasoner.AbstractExtensionReasoner;
import org.tweetyproject.arg.dung.semantics.Semantics;
import org.tweetyproject.commons.InferenceMode;
import org.tweetyproject.commons.ParserException;
import org.tweetyproject.logics.pl.parser.PlParser;
import org.tweetyproject.logics.pl.syntax.PlFormula;
import org.tweetyproject.logics.pl.syntax.Proposition;

import sm.arg.general.ArgumentationGraph;
import sm.arg.general.Debatable;
import sm.arg.intersection.ArgKeys;
import sm.arg.intersection.CrossingCar;
import sm.arg.intersection.FourWaysJunctionConfig;
import sm.intersection.RSU;
import sm.intersection.STATUS;
import sm.intersection.SmartJunction;
import sm.intersection.SmartRoad;
import sm.intersection.WAY;

/**
 * @author sm
 *
 */
public class SingleJunctionSimulation {

    private final Logger log = LoggerFactory.getLogger(SingleJunctionSimulation.class);
    private final SmartJunction junction;
    private final List<CrossingCar> cars;
    private final double step;
    private long steps;
    protected boolean going;

    public SingleJunctionSimulation(final SmartJunction junction, final List<CrossingCar> cars) {
        this.junction = junction;
        this.cars = cars;
        this.step = 1; // TODO now assumed to be 1 s, but make it configurable (X seconds/minutes/...)
        this.steps = 0;
        this.going = false;
    }

    public void step(final Boolean log /* , final long steps */) {
        if (!this.going) {
            //			for (int s = 0; s < steps; s++) {
            this.steps++;
            final List<CrossingCar> toRemove = new ArrayList<>();
            boolean first = true;
            double newSpeed;
            /*
             * (1) Argue first...
             */
            for (final CrossingCar car : this.cars) {
                if (!car.getState().equals(STATUS.SERVED)) {
                    try {
                        this.assignRightOfWay(car, first); // TODO does not consider timing
                        first = false;
                    } catch (ParserException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } // TODO does not consider timing
                }
            }
            /*
             * (2) ...update cars' status then
             */
            for (final CrossingCar car : this.cars) {
                switch (car.getState()) {
                    case APPROACHING: // in questo modo nel caso APPROACHING si esegue lo stesso codice del caso CROSSING ("fall-through"))
                    case CROSSING:
                        if (car.getCar().getCar().getSpeed() < 50) {
                            newSpeed = car.getCar().getCar().getSpeed() * 5 / 4;
                            newSpeed = newSpeed > 50 ? 50 : newSpeed;
                            car.getCar().getCar().setSpeed(newSpeed); // TODO make acceleration configurable
                        }
                        car.setDistance(car.getDistance() - car.getCar().getCar().getSpeed() / 3.6 * this.step);
                        break;
                    case WAITING: // TODO should be checked for right of away again sooner or later...
                        if (car.getCar().getCar().getSpeed() >= 50) { // TODO this way the car decelerates one single time
                            car.getCar().getCar().setSpeed(car.getCar().getCar().getSpeed() * 3 / 4); // TODO make deceleration configurable
                        }
                        // TODO what if cars surpass each other while decelerating?
                        car.setDistance(car.getDistance() - car.getCar().getCar().getSpeed() / 3.6 * this.step);
                        break;
                    /*case CROSSING: // TODO should be checked for right of way anyway as new cars are approaching
                        car.setDistance(car.getDistance() - car.getCar().getCar().getSpeed() / 3.6 * this.step);
                        break;*/
                    case SERVED:
                        this.log.warn("<{}> will be removed next step", car.getName());
                        break;
                    default:
                        throw new IllegalArgumentException("Unexpected value: " + car.getState());
                }
                if (car.getDistance() <= 0) { // junction approximated as point in space
                    car.setState(STATUS.SERVED);
                    this.log.info("<{}> {}, removing from simulation", car.getName(), car.getState());
                    toRemove.add(car);
                }
            }
            if (log) {
                this.logSituation();
            }
            this.cars.removeAll(toRemove);

            //			}
        } else {
            this.log.warn("SIMULATION GOING, PAUSE IT FIRST");
        }
    }

    private void assignRightOfWay(final CrossingCar Car, final boolean first) throws ParserException, IOException {
        /*
         * create ASPIC+ theory
         */
        final List<Proposition> p = new ArrayList<>(); // TODO p non viene mai letto però....a cosa serve dunque?
        final AspicArgumentationTheory<PlFormula> t = new AspicArgumentationTheory<>(new PlFormulaGenerator());
        t.setRuleFormulaGenerator(new PlFormulaGenerator());
        ((Debatable) this.junction.getPolicy()).addAsArgTheory(t); // TODO check if redesign can avoid casts
        for (final SmartRoad road : this.junction.getRoads().values()) {
            for (final RSU<?> rsu : road.getRsus()) {
                if (Debatable.class.isAssignableFrom(rsu.getClass())) {
                    final Proposition b = ((Debatable) rsu).addAsArgTheory(t).get(0);
                    p.add(b);
                }
            }
        }
        Proposition a = null;
        for (final CrossingCar element : this.cars) {
            if (!element.getState().equals(STATUS.SERVED)) {
                a = element.addAsArgTheory(t).get(0);
                p.add(a);
            }
        }

        // TODO valido solo per una junction di quel tipo
        final FourWaysJunctionConfig config = new FourWaysJunctionConfig(this.junction, this.cars);
        config.addAsArgTheory(t); // TODO controlliamo di non considerare auto SERVED
        /*
         * query ASPIC+ theory
         */
        final PlParser plparser = new PlParser();
        final SimpleAspicReasoner<PlFormula> ar = new SimpleAspicReasoner<>(
                AbstractExtensionReasoner.getSimpleReasonerForSemantics(Semantics.GROUNDED_SEMANTICS));
        final PlFormula pf = plparser.parseFormula(Car.getName() + "_" + ArgKeys.PassesFirst);
        //        final PlFormula pf = plparser.parseFormula(Car.getName()+"_"+ArgKeys.CorrectlyDetected);
        if (ar.query(t, pf, InferenceMode.CREDULOUS)) {
            Car.setState(STATUS.CROSSING);
            //    System.out.println(Car.getName());
        } else {
            Car.setState(STATUS.WAITING);
        }
        final ArgumentationGraph Agraph = new ArgumentationGraph(t);
        if (first) {
            Agraph.graph2text(this.cars, this.junction.getPolicy());
        }
        this.log.info("{}? {}", pf, ar.query(t, pf, InferenceMode.CREDULOUS));
    }

    public void go(final Boolean log) {
        if (!this.going) {
            while (!this.cars.isEmpty()) {
                this.step(log, /* 1, */ true);
            }
        } else {
            this.log.warn("SIMULATION ALREADY GOING");
        }

    }

    public void pause() {
        if (this.going) {
            this.going = false;
        } else {
            this.log.warn("SIMULATION ALREADY PAUSED");
        }
    }

    public void logSituation() {
        int nCars;
        this.log.info("Logging simulation situation -----START [step {}]----->", this.steps);
        System.out.printf("%s ways junction <%s> managed by policy <%s> \n", this.junction.nRoads(),
                this.junction.getName(), this.junction.getPolicy().getName());
        for (final WAY way : this.junction.getRoads().keySet()) {
            nCars = 0;
            System.out.printf("\t %s: %d lane(s) \n", way, this.junction.getRoads().get(way).getRoad().nLanes(),
                    this.junction.getRoads().get(way).getRoad().getLanes());
            for (final CrossingCar car : this.cars) {
                if (car.getWay().equals(way)&&!car.getState().equals(STATUS.SERVED)) {
                    nCars++;
                    System.out.printf(
                            "\t\t <%s> %s going %s in %.2f s (urgency: %.2f) at %.2f km/h (distance: %.2f m) \n",
                            car.getName(), car.getState(), car.getRoutes().get(0), car.getTimeToCross(),
                            // TODO code below only fetches first route
                            car.getCar().getUrgency(), car.getCar().getCar().getSpeed(), car.getDistance());
                }
            }
            System.out.printf("\t\t ----- %d car(s) \n", nCars);
        }
        this.log.info("<-----[step {}] END----- Logging simulation situation", this.steps);
    }

    public boolean isGoing() {
        return this.going;
    }

    /**
     * @return the junction
     */
    public SmartJunction getJunction() {
        return this.junction;
    }

    /**
     * @return the cars
     */
    public List<CrossingCar> getCars() {
        return this.cars;
    }

    public SingleJunctionSimulation addCars(final List<CrossingCar> cars) {
        this.cars.addAll(cars);
        return this;
    }

    /**
     * @return the step
     */
    public double getStep() {
        return this.step;
    }

    /**
     * @return the steps
     */
    public long getSteps() {
        return this.steps;
    }

    private void step(final Boolean log, /* final long steps, */ final Boolean bypass) {
        if (bypass) {
            this.going = false;
            this.step(log/* , steps */);
            this.going = true;
        }
    }

}


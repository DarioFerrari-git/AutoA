/**
 *
 */
package sm.arg.intersection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tweetyproject.arg.aspic.syntax.AspicArgumentationTheory;
import org.tweetyproject.arg.aspic.syntax.DefeasibleInferenceRule;
import org.tweetyproject.arg.aspic.syntax.StrictInferenceRule;
import org.tweetyproject.logics.pl.syntax.Negation;
import org.tweetyproject.logics.pl.syntax.PlFormula;
import org.tweetyproject.logics.pl.syntax.Proposition;

import sm.arg.general.Debatable;
import sm.intersection.CrossingPolicy;
import sm.intersection.DIRECTION;
import sm.intersection.NoSuitableRSUException;
import sm.intersection.RSU;
import sm.intersection.Road;
import sm.intersection.STATUS;
import sm.intersection.SmartJunction;
import sm.intersection.SmartRoad;
import sm.intersection.UrgentCar;
import sm.intersection.WAY;

/**
 * @author sm
 *
 */
public final class FourWaysJunctionConfig implements Debatable {

    private final Logger log = LoggerFactory.getLogger(FourWaysJunctionConfig.class);
    private final SmartJunction junction;
    private final List<CrossingCar> cars;

    /**
     *
     * @param junction
     * @param cars
     */
    public FourWaysJunctionConfig(final SmartJunction junction, final List<CrossingCar> cars) {
        this.junction = junction;
        this.cars = cars;
    }

    /**
     * Given RSUs are replicated on each road
     *
     * @param junctionName
     * @param policy
     * @param rsus
     * @return
     */
    public FourWaysJunctionConfig(final String junctionName, final CrossingPolicy policy, final RSU<?>... rsu) {
        final Map<WAY, SmartRoad> roads = new HashMap<>();
        final List<DIRECTION> lanes = Arrays.asList(DIRECTION.values()); // each road has all 3 lanes
        final List<RSU<?>> rsus = Arrays.asList(rsu); // each road has all given RSUs TODO RSU name no longer unique!
        for (final WAY way : WAY.values()) {
            roads.put(way, new SmartRoad(new Road(way.name(), lanes), rsus));
        }
        this.junction = new SmartJunction(junctionName, roads, policy);
        this.cars = new ArrayList<>();
    }

    /**
     * roadname must exist in junction
     *
     * @param car
     * @param roadName
     * @return
     * @throws NoSuitableRSUException
     */
    public FourWaysJunctionConfig addCar(final UrgentCar car, final String roadName) {
        Double d = null;
        for (final WAY way : this.junction.getRoads().keySet()) {
            if (this.junction.getRoads().get(way) != null
                    && this.junction.getRoads().get(way).getRoad().getName().equals(roadName)) {
                for (final RSU<?> rsu : this.junction.getRoads().get(way).getRsus()) {
                    if (rsu instanceof DistanceRSU && rsu.getType().isAssignableFrom(Double.class)) {
                        d = rsu.getMeasurement();
                    } else {
                        this.log.warn("No RSU instanceof DistanceRSU and assignable from Double found: {}",
                                this.junction.getRoads().get(way).getRsus());
                        d = Double.NaN;
                        //						throw new NoSuitableRSUException("No RSU instanceof DistanceRSU and assignable from Double found", this.junction.getRoads().get(way).getRsus());
                    }
                }
                this.cars.add(new CrossingCar(car, way, STATUS.APPROACHING, d));
            }
        }
        return this;
    }

    /**
     * way must exist in junction
     *
     * @param car
     * @param way
     * @return
     * @throws NoSuitableRSUException
     */
    public FourWaysJunctionConfig addCar(final UrgentCar car, final WAY way) {
        Double d = null;
        for (final WAY w : this.junction.getRoads().keySet()) {
            if (w.equals(way) && this.junction.getRoads().get(w) != null) {
                for (final RSU<?> rsu : this.junction.getRoads().get(w).getRsus()) {
                    if (rsu instanceof DistanceRSU && rsu.getType().isAssignableFrom(Double.class)) {
                        d = rsu.getMeasurement();
                    } else {
                        this.log.warn("No RSU instanceof DistanceRSU and assignable from Double found: {}",
                                this.junction.getRoads().get(w).getRsus());
                        d = Double.NaN;
                        //						throw new NoSuitableRSUException("No RSU instanceof DistanceRSU and assignable from Double found", this.junction.getRoads().get(way).getRsus());
                    }
                }
                this.cars.add(new CrossingCar(car, w, STATUS.APPROACHING, d));
            }
        }
        return this;
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

    /*
     * TODO per capire meglio cosa succede conviene spezzare il codice in metodi privati
     * che si occupano ognuno di un sotto-problema
     */
    @Override
    public List<Proposition> addAsArgTheory(final AspicArgumentationTheory<PlFormula> t) {
        Proposition a = null;
        Proposition b = null;
        Proposition c = null;
        Proposition d = null;
        Proposition f = null;
        final ArrayList<String> alreadyConsidered = new ArrayList<>();
        boolean Problems = false;
        final ArrayList<String> WaitList = new ArrayList<>();
        final ArrayList<String> WL = new ArrayList<>();
        final ArrayList<String> FirstPassList = new ArrayList<>();
        final ArrayList<String> WaitListEFF = new ArrayList<>();
        final ArrayList<String> CarsInvolvedinCrush = new ArrayList<>();
        DefeasibleInferenceRule<PlFormula> r1 = new DefeasibleInferenceRule<>();
        StrictInferenceRule<PlFormula> r2 = new StrictInferenceRule<>();
        for (int i = 0; i < this.cars.size(); i++) {
            a = new Proposition(this.cars.get(i).getName());
            c = new Proposition(a + "_" + ArgKeys.CorrectlyDetected);
            d = new Proposition(a + "_" + ArgKeys.WronglyDetected);
            r1 = new DefeasibleInferenceRule<>();
            // Non è possibile ri-utilizzare l'auto-traduzione dentro DistanceRSU in quanto è necessaria una dipendenza con le CrossingCars
            if (this.junction.getRoads().get(this.cars.get(i).getWay()).getRsus().get(0).getConfidence() < 0.5) {
                b = new Proposition("" + ArgKeys.RSU_untrustworthy);
                r1.setConclusion(d);
                r1.addPremise(b);
                r1.addPremise(a);
                t.addRule(r1);
                Problems = true;
            } else {
                b = new Proposition("" + ArgKeys.RSU_trustworthy);
                r1.setConclusion(c);
                r1.addPremise(b);
                r1.addPremise(a);
                t.addRule(r1);
            }
            if (!this.cars.get(i).getState().equals(STATUS.SERVED)) {
                for (int j = 0; j < this.cars.size(); j++) {
                    if (!this.cars.get(j).getState().equals(STATUS.SERVED)) {
                        b = new Proposition(ArgKeys.PossibleIncident + "_" + this.cars.get(i).getName()
                                + this.cars.get(j).getName());
                        f = new Proposition(this.cars.get(j).getName());

                        if (!this.cars.get(i).equals(this.cars.get(j)) && !alreadyConsidered
                                .contains(this.cars.get(i).getName() + "V" + this.cars.get(j).getName())) {
                            if (this.noConflicts(i, j) || this.noConflicts(j, i)) {

                                //	System.out.println(this.cars.get(i).getCar().getCar().getName()+" "+this.cars.get(j).getCar().getCar().getName());
                                c = new Proposition(ArgKeys.CanTransitSimultaneously + "_" + this.cars.get(i).getName()
                                        + this.cars.get(j).getName());
                                r1 = new DefeasibleInferenceRule<>();
                                r1.setConclusion(c);
                                r1.addPremise(a);
                                r1.addPremise(f);
                                t.addRule(r1);

                                r2 = new StrictInferenceRule<>();
                                r2.setConclusion(new Negation(b));
                                r2.addPremise(c);
                                alreadyConsidered.add(this.cars.get(j).getName() + "V" + this.cars.get(i).getName());
                                //						this.log.debug(alreadyConsidered.toString());

                                //   System.out.println (this.cars.get(i).getName()+"/"+this.cars.get(j).getName()); 
                            } else {
                                c = new Proposition(this.junction.getPolicy().getName());
                                r1 = new DefeasibleInferenceRule<>();
                                r2 = new StrictInferenceRule<>();

                                if (!alreadyConsidered
                                        .contains(this.cars.get(j).getName() + "V" + this.cars.get(i).getName())
                                        && !alreadyConsidered
                                                .contains(this.cars.get(i).getName() + "V" + this.cars.get(j).getName())
                                        && !this.cars.get(i).equals(this.cars.get(j)) && !alreadyConsidered.contains(
                                                this.cars.get(i).getName() + "x" + this.cars.get(j).getName())) {
                                    //	System.out.println(this.cars.get(i).getName()+"*"+this.cars.get(j).getName());

                                    List<CrossingCar> canCross = this.junction.getPolicy().rightOfWay(this.cars.get(i),
                                            this.cars.get(j));

                                    if (Problems) {
                                        a = new Proposition(this.cars.get(i).getName() + "_" + ArgKeys.WronglyDetected);
                                        f = new Proposition(this.cars.get(j).getName() + "_" + ArgKeys.WronglyDetected);
                                        r1.setConclusion(b);
                                        r1.addPremise(a);
                                        r1.addPremise(f);
                                        t.addRule(r1);
                                        alreadyConsidered
                                                .add(this.cars.get(j).getName() + "x" + this.cars.get(i).getName());
                                        //								GlobalIncident = true;
                                        CarsInvolvedinCrush
                                                .add(this.cars.get(i).getName() + this.cars.get(j).getName());
                                    } else if (canCross.size() == 1) { // CASO IN CUI UNA SOLA AUTO PUO PASSARE
                                        if (this.cars.get(j).equals(canCross.get(0))) {
                                            //  System.out.println(this.cars.get(i).getName()+" "+this.cars.get(j).getName());
                                            a = new Proposition(
                                                    this.cars.get(i).getName() + "_" + ArgKeys.CorrectlyDetected);
                                            f = new Proposition(
                                                    this.cars.get(j).getName() + "_" + ArgKeys.CorrectlyDetected);
                                            d = new Proposition(this.cars.get(i).getName() + "_" + ArgKeys.WaitDueTo
                                                    + this.cars.get(j).getName());
                                            r1.setConclusion(d);
                                            r1.addPremise(c);
                                            r1.addPremise(a);
                                            t.add(r1);
                                            if (!WaitList.contains(this.cars.get(i).getName() + "_" + ArgKeys.WaitDueTo
                                                    + this.cars.get(j).getName())) {
                                                WaitList.add(this.cars.get(i).getName() + "_" + ArgKeys.WaitDueTo
                                                        + this.cars.get(j).getName());
                                            }
                                            if (!WL.contains(this.cars.get(i).getName())) {
                                                WL.add(this.cars.get(i).getName());
                                            }
                                            //else {System.out.println(this.cars.get(i).getName()+" "+this.cars.get(j).getName());}
                                            /*
                                             * TODO in generale cerchiamo di eliminare le stampe "temporanee" usate per debug:
                                             * se la stampa ha un qualche valore generale, usiamo log.info(),
                                             * se serve solo per debug usiamo log.debug()
                                             * Al prosimo ricevimento ricordamelo che ne parliamo
                                             */
                                            // System.out.println(r1);
                                            r2.setConclusion(new Negation(b));
                                            r2.addPremise(d);
                                            r2.addPremise(f);
                                            t.add(r2);
                                            alreadyConsidered
                                                    .add(this.cars.get(j).getName() + "x" + this.cars.get(i).getName());
                                        } else if (this.cars.get(i).equals(canCross.get(0))) {
                                            a = new Proposition(
                                                    this.cars.get(i).getName() + "_" + ArgKeys.CorrectlyDetected);
                                            f = new Proposition(
                                                    this.cars.get(j).getName() + "_" + ArgKeys.CorrectlyDetected);
                                            d = new Proposition(this.cars.get(j).getName() + "_" + ArgKeys.WaitDueTo
                                                    + this.cars.get(i).getName());
                                            r1.setConclusion(d);
                                            r1.addPremise(c);
                                            r1.addPremise(a);
                                            t.add(r1);
                                            if (!WaitList.contains(this.cars.get(j).getName() + "_" + ArgKeys.WaitDueTo
                                                    + this.cars.get(i).getName())
                                                    && !WL.contains(this.cars.get(i).getName())) {

                                                WaitList.add(this.cars.get(j).getName() + "_" + ArgKeys.WaitDueTo
                                                        + this.cars.get(i).getName());
                                                WL.add(this.cars.get(j).getName());
                                            }
                                            r2.setConclusion(new Negation(b));
                                            r2.addPremise(d);
                                            r2.addPremise(f);
                                            t.add(r2);
                                            alreadyConsidered
                                                    .add(this.cars.get(j).getName() + "x" + this.cars.get(i).getName());
                                        }
                                    } else { // canCross.size() > 1 (that is, == 2)
                                        /*
                                         * la lista canCross contiene entrambe le auto, che possono attraversare inseme,
                                         * con già la route alternative corretta settata (per una delle due o per entrambe a seconda del caso)
                                         * 
                                         */
                                    	c = new Proposition(ArgKeys.CanTransitSimultaneously + "_" + this.cars.get(i).getName()
                                                + this.cars.get(j).getName());
                                        r1 = new DefeasibleInferenceRule<>();
                                        r1.setConclusion(c);
                                        r1.addPremise(a);
                                        r1.addPremise(f);
                                        t.addRule(r1);

                                        r2 = new StrictInferenceRule<>();
                                        r2.setConclusion(new Negation(b));
                                        r2.addPremise(c);
                                        alreadyConsidered.add(this.cars.get(j).getName() + "V" + this.cars.get(i).getName());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        /*
         * TODO in generale cerchiamo di eliminare le stampe "temporanee" usate per debug:
         * se la stampa ha un qualche valore generale, usiamo log.info(),
         * se serve solo per debug usiamo log.debug()
         * Al prosimo ricevimento ricordamelo che ne parliamo
         */
        //   System.out.println(WaitList);
        a = new Proposition("" + ArgKeys.Incident);
        if (CarsInvolvedinCrush.size() > 0) {
            r2 = new StrictInferenceRule<>();
            r2.setConclusion(a);
            for (final String element : CarsInvolvedinCrush) {
                b = new Proposition(ArgKeys.PossibleIncident + "_" + element);
                r2.addPremise(b);
            }
            t.add(r2);
        }
        // 	System.out.println(WaitList);
        //   	System.out.println(WL);

        for (final CrossingCar element2 : this.cars) {
            if (!Problems) {

                if (!element2.getState().equals(STATUS.SERVED) && !WL.contains(element2.getName())) {
                    a = new Proposition(element2.getName() + "_" + ArgKeys.PassesFirst);
                    b = new Proposition(element2.getName() + "_" + ArgKeys.CorrectlyDetected);
                    FirstPassList.add(element2.getName());
                    r1 = new DefeasibleInferenceRule<>();
                    r1.setConclusion(a);
                    r1.addPremise(b);
                    t.add(r1);
                    // System.out.println(1+element2.getName());
                } else if (element2.getState().equals(STATUS.SERVED)) {
                    a = new Proposition(element2.getName() + "_" + ArgKeys.CorrectlyDetected);
                    c = new Proposition(element2.getName() + "_" + ArgKeys.DeleteFromSystem);
                    b = new Proposition(element2.getName() + "_" + ArgKeys.Served);
                    d = new Proposition("" + ArgKeys.Served);
                    r1 = new DefeasibleInferenceRule<>();
                    r1.setConclusion(b);
                    r1.addPremise(a);
                    r1.addPremise(d);
                    t.add(r1);
                    r1 = new DefeasibleInferenceRule<>();
                    r1.setConclusion(c);
                    r1.addPremise(a);
                    r1.addPremise(b);
                    t.add(r1);
                }
            }
        }

        //	System.out.println(WaitList);
        for (final CrossingCar element3 : this.cars) {
            r1 = new DefeasibleInferenceRule<>();
            if (!FirstPassList.contains(element3.getName()) && !element3.getState().equals(STATUS.SERVED)) {
                for (final String element2 : WaitList) {
                    final String[] FirstC = element2.split("To");
                    //    System.out.println(FirstC[0]+" "+FirstC[1]);
                    if (element2.contains(element3.getName() + "_" + ArgKeys.WaitDueTo)
                            && FirstPassList.contains(FirstC[1]) && !Problems) {
                        //System.out.println(element3.getName()+" ok ");
                        a = new Proposition("!" + element3.getName() + "_" + ArgKeys.PassesFirst);
                        b = new Proposition(element3.getName() + "_" + ArgKeys.CorrectlyDetected);

                        r1.setConclusion(a);
                        r1.addPremise(b);
                        t.add(r1);
                        WaitListEFF.add(element3.getName());
                    }
                }
            }

        }
        //  System.out.println(FirstPassList);
        //    System.out.println(WaitListEFF);
        for (final CrossingCar element3 : this.cars) {
            r1 = new DefeasibleInferenceRule<>();
            if (!FirstPassList.contains(element3.getName()) && !WaitListEFF.contains(element3.getName()) && !Problems
                    && !element3.getState().equals(STATUS.SERVED)) {
                //	System.out.println(element3.getName());
                a = new Proposition(element3.getName() + "_" + ArgKeys.PassesFirst);
                b = new Proposition(element3.getName() + "_" + ArgKeys.CorrectlyDetected);
                FirstPassList.add(element3.getName());
                r1.setConclusion(a);
                r1.addPremise(b);
                t.add(r1);
                //     System.out.println(2+element3.getName());
            }

        }

        //   System.out.println(FirstPassList);

        return Arrays.asList(a, b, c, d, f);
    }

    private boolean noConflicts(final int i, final int j) {
        return Conflicts.noConflicts(this.cars.get(i), this.cars.get(j));

    }

    @Override
    public String toString() {
        return String.format("FourWaysJunctionConfig [junction=%s, cars=%s]", this.junction, this.cars);
    }

}

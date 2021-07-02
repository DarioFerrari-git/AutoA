
package sm.arg.intersection.examples;

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

import sm.arg.intersection.CrossingCar;
import sm.arg.intersection.DistanceRSU;
import sm.arg.intersection.FourWaysJunctionConfig;
import sm.arg.intersection.NumArgsPolicy;
import sm.intersection.BaseRSU;
import sm.intersection.Car;
import sm.intersection.DIRECTION;
import sm.intersection.UrgentCar;

public class ExampleCorrectlyDetection1 {

    private final static Logger log = LoggerFactory.getLogger(Example1.class);

    public static void main(final String[] args) throws ParserException, IOException {
        final List<Proposition> p = new ArrayList<>();

        List<DIRECTION> route = new ArrayList<>();
        route.add(DIRECTION.RIGHT);
        final Car A = new Car("A", 50);
        A.addRoute(route);
        final UrgentCar U_A = new UrgentCar(A, 0.7);

        route = new ArrayList<>();
        route.add(DIRECTION.STRAIGHT);
        final Car B = new Car("B", 55);
        B.addRoute(route);
        route = new ArrayList<>();
        route.add(DIRECTION.RIGHT);
        B.addRoute(route);
        final UrgentCar U_B = new UrgentCar(B, 0.5);

        route = new ArrayList<>();
        route.add(DIRECTION.LEFT);
        final Car C = new Car("C", 55);
        C.addRoute(route);
        final UrgentCar U_C = new UrgentCar(C, 0.75);

        final NumArgsPolicy nap = new NumArgsPolicy("numArgs");

        final BaseRSU rsu = new BaseRSU("RSU", 0.7);
        final DistanceRSU drsu = new DistanceRSU(rsu, 20);

        final FourWaysJunctionConfig fourWC = new FourWaysJunctionConfig("1", nap, drsu);
        ExampleCorrectlyDetection1.log.info(fourWC.toString());
        fourWC.addCar(U_A, "EAST");
        fourWC.addCar(U_B, "WEST");
        fourWC.addCar(U_C, "NORTH");
        ExampleCorrectlyDetection1.log.info(fourWC.toString());

        final AspicArgumentationTheory<PlFormula> t = new AspicArgumentationTheory<>(new PlFormulaGenerator());
        t.setRuleFormulaGenerator(new PlFormulaGenerator());
        nap.addAsArgTheory(t);
        final Proposition b = drsu.addAsArgTheory(t).get(0);
        p.add(b);
        Proposition a = null;
        for (final CrossingCar element : fourWC.getCars()) {
            a = element.addAsArgTheory(t).get(0);
            p.add(a);
        }
        fourWC.addAsArgTheory(t);
        ExampleCorrectlyDetection1.log.info(t.toString());

        final PlParser plparser = new PlParser();
        final SimpleAspicReasoner<PlFormula> ar = new SimpleAspicReasoner<>(
                AbstractExtensionReasoner.getSimpleReasonerForSemantics(Semantics.GROUNDED_SEMANTICS));
        final PlFormula pf = plparser.parseFormula("Incident");
        ExampleCorrectlyDetection1.log.info("{} --> {}", pf, ar.query(t, pf, InferenceMode.CREDULOUS));

    }

}

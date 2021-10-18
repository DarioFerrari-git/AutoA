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
import sm.arg.intersection.UrgencyPolicy;
import sm.intersection.BaseRSU;
import sm.intersection.Car;
import sm.intersection.DIRECTION;
import sm.intersection.UrgentCar;

/*
 *
 * An example where we can see a Four Ways Junction where two cars have to
 * cross. The RSU system is untrustworthy so these two cars are wrongly
 * detected. This scenario has an important critical issue: these cars are going
 * to move on two conflict paths. For this reason we can expect the RSU system
 * have to impose a crossing order but the RSU does not work. An Incident is
 * unavoidable.
 *
 *
 */
public class ExampleWronglyDetection1 {
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

        final UrgencyPolicy up = new UrgencyPolicy("ugency");

        final BaseRSU rsu = new BaseRSU("RSU", 0.35);
        final DistanceRSU drsu = new DistanceRSU(rsu, 20);

        final FourWaysJunctionConfig fourWC = new FourWaysJunctionConfig("1", up, drsu);
        ExampleWronglyDetection1.log.info(fourWC.toString());
        fourWC.addCar(U_A, "WEST");
        fourWC.addCar(U_B, "NORTH");
        ExampleWronglyDetection1.log.info(fourWC.toString());

        final AspicArgumentationTheory<PlFormula> t = new AspicArgumentationTheory<>(new PlFormulaGenerator());
        t.setRuleFormulaGenerator(new PlFormulaGenerator());
        up.addAsArgTheory(t);
        final Proposition b = drsu.addAsArgTheory(t).get(0);
        p.add(b);
        Proposition a = null;
        for (final CrossingCar element : fourWC.getCars()) {
            a = element.addAsArgTheory(t).get(0);
            p.add(a);
        }
        fourWC.addAsArgTheory(t);
        ExampleWronglyDetection1.log.info(t.toString());

        final PlParser plparser = new PlParser();
        final SimpleAspicReasoner<PlFormula> ar = new SimpleAspicReasoner<>(
                AbstractExtensionReasoner.getSimpleReasonerForSemantics(Semantics.GROUNDED_SEMANTICS));
        final PlFormula pf = plparser.parseFormula("Incident");
        ExampleWronglyDetection1.log.info("{} --> {}", pf, ar.query(t, pf, InferenceMode.CREDULOUS));

    }
}

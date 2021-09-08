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

public class ExampleConflictUrg {
	  private final static Logger log = LoggerFactory.getLogger(Example1.class);

	    public static void main(final String[] args) throws ParserException, IOException {
	        final List<Proposition> p = new ArrayList<>();

	        List<DIRECTION> route = new ArrayList<>();
	        route.add(DIRECTION.LEFT);
	        final Car A = new Car("A", 50);
	        A.addRoute(route);
	        final UrgentCar U_A = new UrgentCar(A, 0.7);

	        route = new ArrayList<>();
	        route.add(DIRECTION.STRAIGHT);
	        final Car B = new Car("B", 55);
	        B.addRoute(route);
	        final UrgentCar U_B = new UrgentCar(B, 0.5);

	        final UrgencyPolicy up = new UrgencyPolicy("urgP");

	        final BaseRSU rsu = new BaseRSU("RSU", 0.7);
	        final DistanceRSU drsu = new DistanceRSU(rsu, 20);

	        final FourWaysJunctionConfig fourWC = new FourWaysJunctionConfig("1", up, drsu);
	        ExampleConflictUrg.log.info(fourWC.toString());
	        fourWC.addCar(U_A, "WEST");
	        fourWC.addCar(U_B, "NORTH");
	        ExampleConflictUrg.log.info(fourWC.toString());

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
	        ExampleConflictUrg.log.info(t.toString());

	        final PlParser plparser = new PlParser();
	        final SimpleAspicReasoner<PlFormula> ar = new SimpleAspicReasoner<>(
	                AbstractExtensionReasoner.getSimpleReasonerForSemantics(Semantics.GROUNDED_SEMANTICS));
	        final PlFormula pf0 = plparser.parseFormula("A_PassesFirst");
	        final PlFormula pf1 = plparser.parseFormula("B_PassesFirst");
	        final PlFormula pf2 = plparser.parseFormula("Incident");
	        ExampleConflictUrg.log.info("{} --> {}", pf0, ar.query(t, pf0, InferenceMode.CREDULOUS));
	        ExampleConflictUrg.log.info("{} --> {}", pf1, ar.query(t, pf1, InferenceMode.CREDULOUS));
	        ExampleConflictUrg.log.info("{} --> {}", pf2, ar.query(t, pf2, InferenceMode.CREDULOUS));
	    }

	}

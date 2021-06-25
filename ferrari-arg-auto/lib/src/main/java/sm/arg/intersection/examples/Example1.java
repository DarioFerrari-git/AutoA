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
import sm.intersection.JunctionMap;
import sm.intersection.UrgentCar;

public class Example1 {

	private final static Logger log = LoggerFactory.getLogger(Example1.class);

	public static void main(final String[] args) throws ParserException, IOException {
		final List<Proposition> p = new ArrayList<>();

		List<DIRECTION> route = new ArrayList<>();
		route.add(DIRECTION.LEFT);
		final Car N = new Car("N", 50);
		N.addRoute(route);
		final UrgentCar U_N = new UrgentCar(N, 0.7);

		route = new ArrayList<>();
		route.add(DIRECTION.STRAIGHT);
		final Car R = new Car("R", 52);
		R.addRoute(route);
		route = new ArrayList<>();
		route.add(DIRECTION.RIGHT);
		R.addRoute(route);
		final UrgentCar U_R = new UrgentCar(R, 0.6);

		route = new ArrayList<>();
		route.add(DIRECTION.STRAIGHT);
		final Car A = new Car("A", 55);
		A.addRoute(route);
		final UrgentCar U_A = new UrgentCar(A, 0.5);

		final NumArgsPolicy nap = new NumArgsPolicy("numArgs");

		final BaseRSU rsu = new BaseRSU("RSU", 0.4);
		final DistanceRSU drsu = new DistanceRSU(rsu, 20);

		final FourWaysJunctionConfig fourWC = new FourWaysJunctionConfig("1", nap, drsu);
		Example1.log.info(fourWC.toString());
		fourWC.addCar(U_N, "NORTH");
		fourWC.addCar(U_R, "EAST");
		fourWC.addCar(U_A, "SOUTH");
		Example1.log.info(fourWC.toString());

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
		Example1.log.info(t.toString());

		

		FourWaysJunctionConfig[][] mp=new FourWaysJunctionConfig[2][2];
	
		JunctionMap m= new JunctionMap(2,2,mp);
		m.setJunction(0, 0, fourWC);
		m.setJunction(1, 1, fourWC);
		System.out.println(m.toString());
		
	
		
		
		
		final PlParser plparser = new PlParser();
		final SimpleAspicReasoner<PlFormula> ar = new SimpleAspicReasoner<>(
				AbstractExtensionReasoner.getSimpleReasonerForSemantics(Semantics.GROUNDED_SEMANTICS));
		final PlFormula pf = plparser.parseFormula("Incident");
		Example1.log.info("{} --> {}", pf, ar.query(t, pf, InferenceMode.CREDULOUS));

	}
}

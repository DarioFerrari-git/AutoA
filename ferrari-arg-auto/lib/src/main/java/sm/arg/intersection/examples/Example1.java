package sm.arg.intersection.examples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import sm.arg.intersection.BaseRSU;
import sm.arg.intersection.Car;
import sm.arg.intersection.DIRECTION;
import sm.arg.intersection.DistanceRSU;
import sm.arg.intersection.FourWaysJunctionConfig;
import sm.arg.intersection.NumArgsPolicy;
import sm.arg.intersection.UrgentCar;

public class Example1 {

	public static void main(String[] args) throws ParserException, IOException {
		List<Proposition> p = new ArrayList<Proposition>();

		List<DIRECTION> dir = new ArrayList<DIRECTION>();
		dir.add(DIRECTION.LEFT);
		Car N = new Car("N", 50);
		N.addRoute(dir);
		UrgentCar U_N = new UrgentCar(N, 0.7);

		dir = new ArrayList<DIRECTION>();
		dir.add(DIRECTION.STRAIGHT);
		Car R = new Car("R", 52);
		R.addRoute(dir);
		dir = new ArrayList<DIRECTION>();
		dir.add(DIRECTION.RIGHT);
		R.addRoute(dir);
		UrgentCar U_R = new UrgentCar(R, 0.6);

		dir = new ArrayList<DIRECTION>();
		dir.add(DIRECTION.STRAIGHT);
		Car A = new Car("A", 55);
		A.addRoute(dir);
		UrgentCar U_A = new UrgentCar(A, 0.5);

		NumArgsPolicy nap = new NumArgsPolicy();

		BaseRSU rsu = new BaseRSU("RSU", 0.7);
		DistanceRSU drsu = new DistanceRSU(rsu, 20);

		FourWaysJunctionConfig fourWC = new FourWaysJunctionConfig("1", nap, drsu);
		System.out.println(fourWC);
		fourWC.addCar(U_N, "NORTH");
		fourWC.addCar(U_R, "EAST");
		fourWC.addCar(U_A, "SOUTH");
		System.out.println(fourWC);

		AspicArgumentationTheory<PlFormula> t = new AspicArgumentationTheory<>(new PlFormulaGenerator());
		t.setRuleFormulaGenerator(new PlFormulaGenerator());
		nap.addAsPropAxiom(t);
		Proposition b = drsu.addAsPropAxiom(t);
		p.add(b);
		Proposition a = null;
		for (int i = 0; i < fourWC.getCars().size(); i++) {
			a = fourWC.getCars().get(i).addAsPropAxiom(t);
			p.add(a);
		}
		fourWC.addAsArgTheory(t);
		System.out.println(t);

		PlParser plparser = new PlParser();
		SimpleAspicReasoner<PlFormula> ar = new SimpleAspicReasoner<PlFormula>(
				AbstractExtensionReasoner.getSimpleReasonerForSemantics(Semantics.GROUNDED_SEMANTICS));
		PlFormula pf = (PlFormula) plparser.parseFormula("!PossibleIncident");
		System.out.println(pf + "\t" + ar.query(t, pf, InferenceMode.CREDULOUS));

	}
}

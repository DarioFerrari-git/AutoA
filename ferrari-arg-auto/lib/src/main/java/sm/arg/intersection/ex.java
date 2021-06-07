package sm.arg.intersection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.tweetyproject.arg.aspic.reasoner.SimpleAspicReasoner;
import org.tweetyproject.arg.aspic.ruleformulagenerator.PlFormulaGenerator;
import org.tweetyproject.arg.aspic.syntax.AspicArgumentationTheory;
import org.tweetyproject.arg.aspic.syntax.DefeasibleInferenceRule;
import org.tweetyproject.arg.dung.reasoner.AbstractExtensionReasoner;
import org.tweetyproject.arg.dung.semantics.Semantics;
import org.tweetyproject.commons.InferenceMode;
import org.tweetyproject.commons.ParserException;
import org.tweetyproject.logics.pl.parser.PlParser;
import org.tweetyproject.logics.pl.syntax.PlFormula;
import org.tweetyproject.logics.pl.syntax.Proposition;

public class ex {

	public static void main(String[] args) throws ParserException, IOException {
		// TODO Auto-generated method stub
		
		List<Proposition>p=new ArrayList<Proposition>();
		List<DIRECTION> dir=new ArrayList<DIRECTION>();
		dir.add(DIRECTION.RIGHT);
		Car R=new Car("R", 55);
		R.addRoute(dir);
		UrgentCar U_R=new UrgentCar(R, 0.7);
		
		dir=new ArrayList<DIRECTION>();
		dir.add(DIRECTION.STRAIGHT);
		Car A=new Car("A", 50);
		A.addRoute(dir);
		dir=new ArrayList<DIRECTION>();
		dir.add(DIRECTION.RIGHT);
		A.addRoute(dir);
		UrgentCar U_A=new UrgentCar(A, 0.5);
		
		NumArgsPolicy nap=new NumArgsPolicy();
		
		BaseRSU rsu=new BaseRSU("RSU",0.3);
		DistanceRSU drsu=new DistanceRSU(rsu,20);
		FourWaysJunctionConfig fourWC=new FourWaysJunctionConfig("1",nap,drsu);
		System.out.println(fourWC);
		fourWC.addCar(U_R, "WEST");
		fourWC.addCar(U_A, "EAST");
		System.out.println(fourWC);
		
		AspicArgumentationTheory<PlFormula> t = new AspicArgumentationTheory<>(new PlFormulaGenerator());
		t.setRuleFormulaGenerator(new PlFormulaGenerator());
		nap.ArgumentPolicy(t);
		Proposition b=drsu.ArgumentRSU(t);
	    p.add(b);
	    Proposition a=null;
		for(int i=0; i<fourWC.getCars().size();i++) {
		a=fourWC.getCars().get(i).ArgumentCar(t);
		p.add(a); 
		}
	    fourWC.Rules(t);
	     
	   
	    System.out.println(t.getConclusions());
	    System.out.println(fourWC.getJunction().getPolicy().Policyname());
	    
	    PlParser plparser = new PlParser();
	    SimpleAspicReasoner<PlFormula> ar = new SimpleAspicReasoner<PlFormula>(
				AbstractExtensionReasoner.getSimpleReasonerForSemantics(Semantics.GROUNDED_SEMANTICS));
		PlFormula pf = (PlFormula) plparser.parseFormula("!PossibleIncident");
		System.out.println(pf + "\t" + ar.query(t, pf, InferenceMode.CREDULOUS));

	}
}

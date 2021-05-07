import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.tweetyproject.arg.aspic.parser.AspicParser;
import org.tweetyproject.arg.aspic.reasoner.SimpleAspicReasoner;
import org.tweetyproject.arg.aspic.ruleformulagenerator.PlFormulaGenerator;
import org.tweetyproject.arg.aspic.syntax.AspicArgumentationTheory;
import org.tweetyproject.arg.dung.reasoner.AbstractExtensionReasoner;
import org.tweetyproject.arg.dung.semantics.Semantics;
import org.tweetyproject.commons.InferenceMode;
import org.tweetyproject.commons.ParserException;
import org.tweetyproject.logics.pl.parser.PlParser;
import org.tweetyproject.logics.pl.syntax.PlFormula;

public class main {

	public static void main(String[] args) throws FileNotFoundException, ParserException, IOException {
		// TODO Auto-generated method stub
		Codice C=new Codice();
		ArrayList<Auto>Incrocio=new ArrayList<Auto>();
		//Incrocio=C.creAuto();
		Auto A=new Auto("A","sinistra","80","diritto",70,false);
		Auto B=new Auto("B","basso","10","diritto",770,false);
		Incrocio.add(B);
		Incrocio.add(A);
		ArrayList<Sensore>Sens=new ArrayList<Sensore>();
		//Sens=C.creaSensori();
		Sensore S1=new Sensore("destra",true);
		Sensore S2=new Sensore("sinistra",true);
		Sensore S3=new Sensore("alto",true);
		Sensore S4=new Sensore("basso",true);
		Sens.add(S4);
		Sens.add(S3);
		Sens.add(S2);
		Sens.add(S1);
		
		System.out.println(Incrocio);
		System.out.println(Sens);
		C.Caricadati("file", Incrocio, Sens);
		
		PlParser plparser = new PlParser();
		AspicParser<PlFormula> parser = new AspicParser<>(plparser, new PlFormulaGenerator());
		AspicArgumentationTheory<PlFormula> at = parser.parseBeliefBaseFromFile("file");		
		SimpleAspicReasoner<PlFormula> ar = new SimpleAspicReasoner<PlFormula>(AbstractExtensionReasoner.getSimpleReasonerForSemantics(Semantics.GROUNDED_SEMANTICS));
		PlFormula pf = (PlFormula)plparser.parseFormula("NonSiOstacolano");		
		System.out.println(at);
		System.out.println(pf + "\t" + ar.query(at,pf,InferenceMode.CREDULOUS));		
	
	}
	

}

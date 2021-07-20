package aspic;

import java.io.FileNotFoundException;
import java.io.IOException;

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


public class AspicWithFile {
	public static void main(String[] args) throws FileNotFoundException, ParserException, IOException{
		PlParser plparser = new PlParser();
		AspicParser<PlFormula> parser = new AspicParser<>(plparser, new PlFormulaGenerator());
		AspicArgumentationTheory<PlFormula> at = parser.parseBeliefBaseFromFile("/Users/darioferrari/git/AutoA2/ferrari-arg-auto/lib/src/main/java/aspic/ex1.aspic");		
		SimpleAspicReasoner<PlFormula> ar = new SimpleAspicReasoner<PlFormula>(AbstractExtensionReasoner.getSimpleReasonerForSemantics(Semantics.GROUNDED_SEMANTICS));
		PlFormula pf = (PlFormula)plparser.parseFormula("!has_a_gun");		
		System.out.println(at);
		System.out.println(pf + "\t" + ar.query(at,pf,InferenceMode.CREDULOUS));		
	}
}
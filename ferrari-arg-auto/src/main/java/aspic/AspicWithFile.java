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
    public static void main(final String[] args) throws FileNotFoundException, ParserException, IOException {
        final PlParser plparser = new PlParser();
        final AspicParser<PlFormula> parser = new AspicParser<>(plparser, new PlFormulaGenerator());
        final AspicArgumentationTheory<PlFormula> at = parser.parseBeliefBaseFromFile(
                "/Users/darioferrari/git/AutoA2/ferrari-arg-auto/lib/src/main/java/aspic/ex1.aspic");
        final SimpleAspicReasoner<PlFormula> ar = new SimpleAspicReasoner<>(
                AbstractExtensionReasoner.getSimpleReasonerForSemantics(Semantics.GROUNDED_SEMANTICS));
        final PlFormula pf = plparser.parseFormula("!has_a_gun");
        System.out.println(at);
        System.out.println(pf + "\t" + ar.query(at, pf, InferenceMode.CREDULOUS));
    }
}
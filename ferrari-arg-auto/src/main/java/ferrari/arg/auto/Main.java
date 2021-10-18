package ferrari.arg.auto;

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

public class Main {

    public static void main(final String[] args) throws FileNotFoundException, ParserException, IOException {
        final Codice C = new Codice();
        ArrayList<Auto> Incrocio = new ArrayList<>();
        /*
         * Auto A=new Auto("A","sinistra","80","diritto",70,false); Auto B=new
         * Auto("B","basso","10","diritto",770,false); Incrocio.add(B); Incrocio.add(A);
         */
        Incrocio = Codice.creaConflittoSA();
        ArrayList<Sensore> Sens = new ArrayList<>();
        Sens = Codice.creaSensori();
        /*
         * Auto A=new Auto("A","alto","0","sinistra",94,true); Auto B=new
         * Auto("R","basso","3","sinistra",75,false); Incrocio.add(B); Incrocio.add(A);
         */
        System.out.println(Incrocio);
        System.out.println(Sens);
        C.Caricadati("/Users/darioferrari/git/AutoA/ferrari-arg-auto/lib/src/main/resources/file", Incrocio, Sens);

        final PlParser plparser = new PlParser();
        final AspicParser<PlFormula> parser = new AspicParser<>(plparser, new PlFormulaGenerator());
        final AspicArgumentationTheory<PlFormula> at = parser
                .parseBeliefBaseFromFile("/Users/darioferrari/git/AutoA/ferrari-arg-auto/lib/src/main/resources/file");
        final SimpleAspicReasoner<PlFormula> ar = new SimpleAspicReasoner<>(
                AbstractExtensionReasoner.getSimpleReasonerForSemantics(Semantics.GROUNDED_SEMANTICS));
        final PlFormula pf = plparser.parseFormula("NonSiOstacolano");
        System.out.println(at);
        System.out.println(pf + "\t" + ar.query(at, pf, InferenceMode.CREDULOUS));

    }

}

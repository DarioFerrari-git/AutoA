package aspic;

import java.io.IOException;

import org.tweetyproject.arg.aspic.reasoner.SimpleAspicReasoner;
import org.tweetyproject.arg.aspic.ruleformulagenerator.PlFormulaGenerator;
import org.tweetyproject.arg.aspic.syntax.AspicArgumentationTheory;
import org.tweetyproject.arg.aspic.syntax.DefeasibleInferenceRule;
import org.tweetyproject.arg.aspic.syntax.StrictInferenceRule;
import org.tweetyproject.arg.aspic.util.RandomAspicArgumentationTheoryGenerator;
import org.tweetyproject.arg.dung.reasoner.AbstractExtensionReasoner;
import org.tweetyproject.arg.dung.reasoner.SimpleGroundedReasoner;
import org.tweetyproject.arg.dung.semantics.Semantics;
import org.tweetyproject.arg.dung.syntax.Argument;
import org.tweetyproject.arg.dung.syntax.Attack;
import org.tweetyproject.arg.dung.syntax.DungTheory;
import org.tweetyproject.commons.InferenceMode;
import org.tweetyproject.commons.ParserException;
import org.tweetyproject.logics.pl.parser.PlParser;
import org.tweetyproject.logics.pl.syntax.Negation;
import org.tweetyproject.logics.pl.syntax.PlFormula;
import org.tweetyproject.logics.pl.syntax.Proposition;

public class Aspic {
    public static void main(final String[] args) throws ParserException, IOException {
        final Proposition a = new Proposition("a");
        final Proposition b = new Proposition("b");
        final Proposition c = new Proposition("c");
        final Proposition d = new Proposition("d");
        final Proposition e = new Proposition("e");
        final Proposition f = new Proposition("f");
        final Proposition g = new Proposition("g");

        final PlParser plparser = new PlParser();
        final AspicArgumentationTheory<PlFormula> t = new AspicArgumentationTheory<>(new PlFormulaGenerator());
        t.setRuleFormulaGenerator(new PlFormulaGenerator());
        final SimpleAspicReasoner<PlFormula> ar = new SimpleAspicReasoner<>(
                AbstractExtensionReasoner.getSimpleReasonerForSemantics(Semantics.GROUNDED_SEMANTICS));
        final PlFormula pf = plparser.parseFormula("f");

        DefeasibleInferenceRule<PlFormula> r1 = new DefeasibleInferenceRule<>();
        r1.setConclusion(a);
        r1.addPremise(b);
        r1.addPremise(c);
        t.addRule(r1);

        StrictInferenceRule<PlFormula> r2 = new StrictInferenceRule<>();

        r2 = new StrictInferenceRule<>();
        r2.setConclusion(new Negation(d));
        r2.addPremise(a);
        t.addRule(r2);

        r2 = new StrictInferenceRule<>();
        r2.setConclusion(new Negation(e));
        r2.addPremise(d);
        t.addRule(r2);

        r1 = new DefeasibleInferenceRule<>();
        r1.setConclusion(e);
        r1.addPremise(b);
        t.addRule(r1);

        t.addAxiom(b);
        t.addAxiom(c);

        t.addOrdinaryPremise(d);

        final StrictInferenceRule<PlFormula> r3 = new StrictInferenceRule<>();
        r3.setConclusion(new Negation(g));
        r3.addPremise(f);
        t.addRule(r3);

        final DefeasibleInferenceRule<PlFormula> r4 = new DefeasibleInferenceRule<>();
        r4.setConclusion(new Negation(f));
        r4.addPremise(g);
        t.addRule(r4);

        t.addOrdinaryPremise(g);
        t.addOrdinaryPremise(f);

        // System.out.println(t);
        System.out.println(t);
        System.out.println(pf + "\t" + ar.query(t, pf, InferenceMode.CREDULOUS));

        System.out.println();

        final AbstractExtensionReasoner reasoner = new SimpleGroundedReasoner();

        final DungTheory aaf = t.asDungTheory();
        System.out.println(reasoner.getModel(aaf));
        System.out.println();

        for (final Argument arg : aaf) {
            System.out.println(arg);
        }

        System.out.println();

        for (final Attack att : aaf.getAttacks()) {
            //     System.out.println(att);
        }

        System.out.println();

        final RandomAspicArgumentationTheoryGenerator gen = new RandomAspicArgumentationTheoryGenerator(10, 30, 2, 0.3);
        final Proposition p = new Proposition("A0");
        final AspicArgumentationTheory<PlFormula> t2 = gen.next();
        System.out.println(t2);

        final SimpleAspicReasoner<PlFormula> r9 = new SimpleAspicReasoner<>(new SimpleGroundedReasoner());
        System.out.println(p + "\t" + r9.query(t2, p, InferenceMode.CREDULOUS));

    }
}

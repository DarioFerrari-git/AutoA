/**
 *
 */
package sm.arg.general;

import java.util.List;

import org.tweetyproject.arg.aspic.syntax.AspicArgumentationTheory;
import org.tweetyproject.logics.pl.syntax.PlFormula;
import org.tweetyproject.logics.pl.syntax.Proposition;

/**
 * @author sm
 *
 */
public interface Debatable {

    List<Proposition> addAsArgTheory(final AspicArgumentationTheory<PlFormula> t);

}

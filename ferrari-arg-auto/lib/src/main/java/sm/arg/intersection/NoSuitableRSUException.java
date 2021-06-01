/**
 * 
 */
package sm.arg.intersection;

import java.util.List;

/**
 * @author sm
 *
 */
public final class NoSuitableRSUException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final List<RSU<?>> rsus;

	public NoSuitableRSUException(String msg, List<RSU<?>> rsus) {
		super(msg);
		this.rsus = rsus;
	}

	/**
	 * @return the rsus
	 */
	public List<RSU<?>> getRsus() {
		return rsus;
	}

	@Override
	public String toString() {
		return String.format("NoSuitableRSUException [msg=%s, rsus=%s]", this.getMessage(), rsus);
	}

}

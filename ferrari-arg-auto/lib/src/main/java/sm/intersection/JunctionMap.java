package sm.intersection;

import sm.arg.intersection.FourWaysJunctionConfig;

public class JunctionMap {

	// private final Logger log = LoggerFactory.getLogger(JunctionMap.class);
	private final SmartJunction[][] junctions;
	private final int n;
	private final int m;

	public JunctionMap(final int n, final int m, final SmartJunction[][] junctions) {// ,final
																								// FourWaysJunctionConfig
																								// bho0) {
		this.n = n;
		this.m = m;
		this.junctions = junctions;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				this.junctions[i][j] = new SmartJunction(null, null, null);
			}
		}
	}

	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.m; j++) {
				str += this.junctions[i][j] + "\t";
			}
			str += "\n";
		}
		return str;
	}

	public SmartJunction getJunction(final int x, final int y) {
		return this.junctions[x][y];
	}

	public JunctionMap setJunction(final int x, final int y, final SmartJunction smartJ) {
		this.junctions[x][y] = smartJ;
		return this;
	}
	
	public SmartJunction next(WAY way, DIRECTION dir) {
		// TODO restituire nuovo incrocio sapendo strada di provenienza (way) e direzione (dir) dell'auto
	
		return null;
	}

}
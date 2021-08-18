package sm.intersection;

public class JunctionMatrix {

    // private final Logger log = LoggerFactory.getLogger(JunctionMap.class);
    private final SmartJunction[][] junctions;
    private final int r;
    private final int c;

    public JunctionMatrix(final SmartJunction[][] junctions) {
        this.r = junctions.length;
        this.c = junctions[0].length;
        this.junctions = junctions;
    }

    public SmartJunction getJunction(final int row, final int col) {
        return this.junctions[row][col];
    }

    public JunctionMatrix setJunction(final int row, final int col, final SmartJunction junction) {
        this.junctions[row][col] = junction;
        return this;
    }

    public SmartJunction next(final int[] coord, final WAY way, final DIRECTION dir) {
        // TODO restituire nuovo incrocio sapendo strada di provenienza (way) e direzione (dir) dell'auto (direi che serva anche sapere in quale incrocio è l'auto nella mappa)
        return null;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        for (int i = 0; i < this.r; i++) {
            for (int j = 0; j < this.c; j++) {
                str.append(String.format("\t(%d,%d) %s", i, j, this.junctions[i][j].getName()));
            }
            str.append("\n");
        }
        return str + "}";
    }

}
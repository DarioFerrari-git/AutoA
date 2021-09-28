package sm.intersection;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JunctionMatrix {

    private final Logger log = LoggerFactory.getLogger(JunctionMatrix.class);
    private final SmartJunction[][] junctions;
    private final int r;
    private final int c;

    public JunctionMatrix(final SmartJunction[][] junctions) {
        this.r = junctions.length;
        this.c = junctions[0].length;
        this.junctions = junctions;
    }

    public int nRows() {
        return this.r;
    }

    public int nCols() {
        return this.c;
    }

    public SmartJunction getJunction(final int row, final int col) {
        return this.junctions[row][col]; // TODO handle not found
    }

    public Optional<int[]> getJunction(final String name) {
        Optional<int[]> res = Optional.empty();
        for (int r = 0; r < this.junctions.length; r++) {
            for (int c = 0; c < this.junctions[r].length; c++) {
                if (this.junctions[r][c].getName().equals(name)) {
                    res = Optional.of(new int[] { r, c });
                }
            }
        }
        return res;
    }

    public JunctionMatrix setJunction(final int row, final int col, final SmartJunction junction) {
        this.junctions[row][col] = junction;
        return this;
    }

    public Optional<SmartJunction> next(final int[] coord, final WAY way, final DIRECTION dir) {
        Optional<SmartJunction> res;
        int[] next = null;
        switch (way) {
            case WEST:
                switch (dir) {
                    case STRAIGHT:
                        next = new int[] { coord[0], coord[1] + 1 };
                        break;
                    case RIGHT:
                        next = new int[] { coord[0] + 1, coord[1] };
                        break;
                    case LEFT:
                        next = new int[] { coord[0] - 1, coord[1] };
                        break;
                }
                break;
            case NORTH:
                switch (dir) {
                    case STRAIGHT:
                        next = new int[] { coord[0] + 1, coord[1] };
                        break;
                    case RIGHT:
                        next = new int[] { coord[0], coord[1] - 1 };
                        break;
                    case LEFT:
                        next = new int[] { coord[0], coord[1] + 1 };
                        break;
                }
                break;
            case EAST:
                switch (dir) {
                    case STRAIGHT:
                        next = new int[] { coord[0], coord[1] - 1 };
                        break;
                    case RIGHT:
                        next = new int[] { coord[0] - 1, coord[1] };
                        break;
                    case LEFT:
                        next = new int[] { coord[0] + 1, coord[1] };
                        break;
                }
                break;
            case SOUTH:
                switch (dir) {
                    case STRAIGHT:
                        next = new int[] { coord[0] - 1, coord[1] };
                        break;
                    case RIGHT:
                        next = new int[] { coord[0], coord[1] + 1 };
                        break;
                    case LEFT:
                        next = new int[] { coord[0], coord[1] - 1 };
                        break;
                }
                break;
        }
        try {
            final SmartJunction sm = this.getJunction(next[0], next[1]);
            res = Optional.of(sm);
        } catch (final ArrayIndexOutOfBoundsException e) {
            this.log.warn("JUNCTION ({},{}) DOES NOT EXIST", next[0], next[1]);
            res = Optional.empty();
        }
        return res;
    }

    @Override
    public String toString() {
        final StringBuilder str = new StringBuilder("{");
        for (int i = 0; i < this.r; i++) {
            for (int j = 0; j < this.c; j++) {
                str.append(String.format("\t(%d,%d) %s", i, j, this.junctions[i][j].getName()));
            }
            str.append("\n");
        }
        return str + "}";
    }

}
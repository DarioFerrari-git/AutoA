package sm.intersection;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum DIRECTION {

    STRAIGHT, LEFT, RIGHT;

    private static final List<DIRECTION> VALUES = Collections.unmodifiableList(Arrays.asList(DIRECTION.values()));
    private static final int SIZE = DIRECTION.VALUES.size();
    private static Random RANDOM;

    public static DIRECTION random() {
        return DIRECTION.VALUES.get(DIRECTION.RANDOM.nextInt(DIRECTION.SIZE));
    }

    public static void setSeed(final long seed) {
        DIRECTION.RANDOM = new Random(seed);
    }

}

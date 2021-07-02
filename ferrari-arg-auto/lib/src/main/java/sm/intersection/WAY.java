package sm.intersection;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum WAY {

    NORTH, SOUTH, WEST, EAST;

    private static final List<WAY> VALUES = Collections.unmodifiableList(Arrays.asList(WAY.values()));
    private static final int SIZE = WAY.VALUES.size();
    private static final Random RANDOM = new Random();

    public static WAY random() {
        return WAY.VALUES.get(WAY.RANDOM.nextInt(WAY.SIZE));
    }

}

package sm.intersection;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum WAY {

    NORTH(0), EAST(1), SOUTH(2), WEST(3);

    private final int intValue;

    public static final List<WAY> VALUES = Collections.unmodifiableList(Arrays.asList(WAY.values()));
    private static final int SIZE = WAY.VALUES.size();
    private static final Random RANDOM = new Random();

    WAY(final int intValue) {
        this.intValue = intValue;
    }

    public static WAY random() {
        return WAY.VALUES.get(WAY.RANDOM.nextInt(WAY.SIZE));
    }

    public int intValue() {
        return this.intValue;
    }

}

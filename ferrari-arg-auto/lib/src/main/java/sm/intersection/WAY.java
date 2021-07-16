package sm.intersection;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum WAY {

    NORTH(1), EAST(2), SOUTH(3), WEST(4);

    private final int intValue;

    public static final List<WAY> VALUES = Collections.unmodifiableList(Arrays.asList(WAY.values()));
    private static final int SIZE = WAY.VALUES.size();
    private static final Random RANDOM = new Random();

    WAY(int intValue) {
        this.intValue = intValue;
    }

    public static WAY random() {
        return WAY.VALUES.get(WAY.RANDOM.nextInt(WAY.SIZE));
    }
    
    public int intValue() {
        return this.intValue;
    }

    public static Integer intWay(WAY way) { // TODO can be refactored as constructor?
        int W = 0;
        if (way.toString().equals("EAST"))
            W = 1;
        if (way.toString().equals("SOUTH"))
            W = 2;
        if (way.toString().equals("WEST"))
            W = 3;
        return W;

    }
}

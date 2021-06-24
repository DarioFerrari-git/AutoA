package sm.intersection;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum DIRECTION {

	STRAIGHT, LEFT, RIGHT;

	private static final List<DIRECTION> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	public static DIRECTION random() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}

}

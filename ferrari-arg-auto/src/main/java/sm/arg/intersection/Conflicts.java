/**
 *
 */
package sm.arg.intersection;

import sm.intersection.DIRECTION;
import sm.intersection.WAY;

/**
 * @author sm
 *
 */
public final class Conflicts {

    public static boolean noConflicts(final CrossingCar car1, final CrossingCar car2) { // pare non poter essere semplificato
        return car1.getCurrentRoutePath().get(0).equals(DIRECTION.RIGHT)
                && car2.getCurrentRoutePath().get(0).equals(DIRECTION.RIGHT)

                || car1.getCurrentRoutePath().get(0).equals(DIRECTION.RIGHT)
                        && car2.getCurrentRoutePath().get(0).equals(DIRECTION.STRAIGHT)

                || car1.getCurrentRoutePath().get(0).equals(DIRECTION.LEFT)
                        && car2.getCurrentRoutePath().get(0).equals(DIRECTION.STRAIGHT)
                        && car2.getWay().equals(WAY.SOUTH) && car1.getWay().equals(WAY.WEST)
                || car1.getCurrentRoutePath().get(0).equals(DIRECTION.LEFT)
                        && car2.getCurrentRoutePath().get(0).equals(DIRECTION.STRAIGHT)
                        && car2.getWay().equals(WAY.WEST) && car1.getWay().equals(WAY.NORTH)
                || car1.getCurrentRoutePath().get(0).equals(DIRECTION.LEFT)
                        && car2.getCurrentRoutePath().get(0).equals(DIRECTION.STRAIGHT)
                        && car2.getWay().equals(WAY.NORTH) && car1.getWay().equals(WAY.EAST)
                || car1.getCurrentRoutePath().get(0).equals(DIRECTION.LEFT)
                        && car2.getCurrentRoutePath().get(0).equals(DIRECTION.STRAIGHT)
                        && car2.getWay().equals(WAY.EAST) && car1.getWay().equals(WAY.SOUTH)

                || car1.getCurrentRoutePath().get(0).equals(DIRECTION.RIGHT)
                        && car2.getCurrentRoutePath().get(0).equals(DIRECTION.LEFT)

                || car1.getCurrentRoutePath().get(0).equals(DIRECTION.LEFT)
                        && car2.getCurrentRoutePath().get(0).equals(DIRECTION.LEFT) && car1.getWay().equals(WAY.EAST)
                        && car2.getWay().equals(WAY.WEST)
                || car1.getCurrentRoutePath().get(0).equals(DIRECTION.LEFT)
                        && car2.getCurrentRoutePath().get(0).equals(DIRECTION.LEFT) && car1.getWay().equals(WAY.NORTH)
                        && car2.getWay().equals(WAY.SOUTH)

                || car1.getCurrentRoutePath().get(0).equals(DIRECTION.STRAIGHT)
                        && car2.getCurrentRoutePath().get(0).equals(DIRECTION.STRAIGHT)
                        && car1.getWay().equals(WAY.EAST) && car2.getWay().equals(WAY.WEST)
                || car1.getCurrentRoutePath().get(0).equals(DIRECTION.STRAIGHT)
                        && car2.getCurrentRoutePath().get(0).equals(DIRECTION.STRAIGHT)
                        && car1.getWay().equals(WAY.NORTH) && car2.getWay().equals(WAY.SOUTH)
                || car1.getWay().equals(car2.getWay());

    }

}

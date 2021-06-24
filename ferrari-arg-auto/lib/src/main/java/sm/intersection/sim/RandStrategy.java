/**
 * 
 */
package sm.intersection.sim;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sm.arg.intersection.CrossingCar;
import sm.arg.intersection.DistanceRSU;
import sm.intersection.Car;
import sm.intersection.RSU;
import sm.intersection.STATUS;
import sm.intersection.SmartJunction;
import sm.intersection.UrgentCar;
import sm.intersection.WAY;

/**
 * @author sm
 *
 */
public final class RandStrategy implements VehiclesGenStrategy {

	private final Logger log = LoggerFactory.getLogger(RandStrategy.class);
	private SmartJunction junction;
	private boolean setup = false;

	private List<WAY> values;
	private int size;
	private Random random = new Random();

	private long nCars;

	@Override
	public VehiclesGenStrategy configJunction(final SmartJunction junction) {
		this.junction = junction;
		this.values = List.copyOf(this.junction.getRoads().keySet());
		this.size = values.size();
		this.nCars = 0;
		this.setup = true;
		return this;
	}

	@Override
	public CrossingCar newCar() {
		if (setup) {
			WAY way = values.get(random.nextInt(size));
			Double d = null;
			for (final RSU<?> rsu : this.junction.getRoads().get(way).getRsus()) {
				if (rsu instanceof DistanceRSU && rsu.getType().isAssignableFrom(Double.class)) {
					d = rsu.getMeasurement();
				} else {
					this.log.warn("No RSU instanceof DistanceRSU and assignable from Double found: {}",
							this.junction.getRoads().get(way).getRsus());
					d = Double.NaN;
					// throw new NoSuitableRSUException("No RSU instanceof DistanceRSU and
					// assignable from Double found", this.junction.getRoads().get(way).getRsus());
				}
			}
			this.nCars++;
			return new CrossingCar(
					new UrgentCar(new Car(String.format("%s_%d", way, this.nCars), this.random.nextDouble() * 50), // TODO make speed range configurable
							this.random.nextDouble()),
					way, STATUS.APPROACHING, d);
		} else {
			this.log.warn("REFERENCE JUNCTION NOT YET CONFIGURED");
			return null;
		}
	}

}

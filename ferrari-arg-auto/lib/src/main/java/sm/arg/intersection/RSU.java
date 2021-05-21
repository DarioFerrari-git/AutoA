package sm.arg.intersection;

public interface RSU<T> {

	/**
	 * 
	 * @return the measurement
	 * 
	 * worst case: X is everything subclassing Object
	 */
	<X extends T> X getMeasurement();
	
	Class<T> getType();

}
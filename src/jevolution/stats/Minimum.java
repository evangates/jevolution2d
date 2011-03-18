package jevolution.stats;

/**
 *
 * @author kuhlmancer
 */
public class Minimum implements Stat {
	@Override
	public double calculate(Iterable<Double> values, long numValues) {
		if (numValues == 0) {
			return 0;
		}

		double minimum = Double.MAX_VALUE;
		for (double value: values) {
			minimum = Math.min(minimum, value);
		}

		return minimum;
	}

	@Override
	public String getName() {
		return "Minimum";
	}

}

package jevolution.stats;

/**
 *
 * @author kuhlmancer
 */
public class Maximum implements Stat {
	@Override
	public double calculate(Iterable<Double> values, long numValues) {
		if (numValues == 0) {
			return 0;
		}

		double maximum = Double.MIN_VALUE;
		for (double value: values) {
			maximum = Math.max(maximum, value);
		}

		return maximum;
	}

	@Override
	public String getName() {
		return "Maximum";
	}

}

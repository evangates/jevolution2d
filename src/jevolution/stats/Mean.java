package jevolution.stats;

/**
 *
 * @author kuhlmancer
 */
public class Mean implements Stat {
	@Override
	public double calculate(Iterable<Double> values, long numValues) {
		if (numValues == 0) {
			return 0;
		}

		double sum = 0;

		for (double value: values) {
			sum += value;
		}

		return sum / numValues;
	}

	@Override
	public String getName() {
		return "Mean";
	}
}

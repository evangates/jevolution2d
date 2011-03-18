package jevolution.stats;

/**
 *
 * @author kuhlmancer
 */
public class StandardDeviation implements Stat {
	@Override
	public double calculate(Iterable<Double> values, long numValues) {
		if (numValues == 0) {
			return 0;
		}

		double mean = new Mean().calculate(values, numValues);

		double sumOfSquaredResiduals = 0;
		for (double value: values) {
			sumOfSquaredResiduals += (value - mean) * (value - mean);
		}

		return Math.sqrt(sumOfSquaredResiduals / numValues);
	}

	@Override
	public String getName() {
		return "Standard Deviation";
	}
}

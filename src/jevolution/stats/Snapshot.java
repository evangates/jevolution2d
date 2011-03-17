package jevolution.stats;

/**
 *
 * @author kuhlmancer
 */
public class Snapshot {
	private double mininum, maximum, average, standardDeviation, median;
	private long time;

	Snapshot(long time, double minimum, double maximum, double average, double standardDeviation, double median) {
		this.time = time;
		this.mininum = minimum;
		this.maximum = maximum;
		this.average = average;
		this.standardDeviation = standardDeviation;
		this.median = median;
	}

	public double getAverage() {
		return average;
	}

	public double getMinimum() {
		return mininum;
	}

	public double getMaximum() {
		return maximum;
	}

	public double getMedian() {
		return median;
	}

	public double getStandardDeviation() {
		return standardDeviation;
	}
}

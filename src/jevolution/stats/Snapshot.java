package jevolution.stats;

import java.util.Map;

/**
 *
 * @author kuhlmancer
 */
public class Snapshot {
	private Map<Class, Double> stats;
	private double time;

	Snapshot(double time, Map<Class, Double> stats) {
		this.time = time;
		this.stats = stats;
	}

	public double getStat(Class klass) {
		return stats.get(klass);
	}

	public Iterable<Double> getValues() {
		return stats.values();
	}

	public double getTime() {
		return time;
	}
}

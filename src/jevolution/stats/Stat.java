package jevolution.stats;

/**
 *
 * @author kuhlmancer
 */
public interface Stat {
	double calculate(Iterable<Double> values, long numValues);

	String getName();
}

package jevolution.tests;

import jevolution.Environment;
import jevolution.stats.Stat;
import jevolution.stats.Stats;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kuhlmancer
 */
public class StatsTests_WithEmptyEnvironment {
	Environment environment;
	Stats stats;

	final static double TOLERANCE = 0.0001;
	
    @Before
    public void setUp() {
		environment = new Environment(100, 100);
		stats = new Stats(environment);
    }

	@Test
	public void minimumsShouldBeZero() {
		stats.collect(environment.getCreatures());

		double expected = 0;

		for (Stats.Keys key: Stats.Keys.values()) {
			Stat stat = stats.lookup(key);
			double actual = stat.getMostRecentSnapshot().getMinimum();

			assertEquals(expected, actual, TOLERANCE);
		}
	}

	@Test
	public void averagesShouldBeZero() {
		stats.collect(environment.getCreatures());

		double expected = 0;

		for (Stats.Keys key: Stats.Keys.values()) {
			Stat stat = stats.lookup(key);
			double actual = stat.getMostRecentSnapshot().getAverage();

			assertEquals(expected, actual, TOLERANCE);
		}
	}

	@Test
	public void maximumsShouldBeZero() {
		stats.collect(environment.getCreatures());

		double expected = 0;

		for (Stats.Keys key: Stats.Keys.values()) {
			Stat stat = stats.lookup(key);
			double actual = stat.getMostRecentSnapshot().getMaximum();

			assertEquals(expected, actual, TOLERANCE);
		}
	}

	@Test
	public void mediansShouldBeZero() {
		stats.collect(environment.getCreatures());

		double expected = 0;

		for (Stats.Keys key: Stats.Keys.values()) {
			Stat stat = stats.lookup(key);
			double actual = stat.getMostRecentSnapshot().getMedian();

			assertEquals(expected, actual, TOLERANCE);
		}
	}

	@Test
	public void standardDeviationsShouldBeZero() {
		stats.collect(environment.getCreatures());

		double expected = 0;

		for (Stats.Keys key: Stats.Keys.values()) {
			Stat stat = stats.lookup(key);
			double actual = stat.getMostRecentSnapshot().getStandardDeviation();

			assertEquals(expected, actual, TOLERANCE);
		}
	}
}
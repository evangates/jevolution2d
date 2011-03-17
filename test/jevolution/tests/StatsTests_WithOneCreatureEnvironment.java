package jevolution.tests;

import jevolution.stats.Stat;
import jevolution.Creature;
import jevolution.Environment;
import jevolution.stats.Stats;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kuhlmancer
 */
public class StatsTests_WithOneCreatureEnvironment {
	private final static double TOLERANCE = 0.001;

	Environment environment;
	Stats stats;
	Creature creature;

    @Before
    public void setUp() {
		environment = new Environment(100, 100);
		creature = environment.newRandomCreature();

		environment.addCreature(creature);

		stats = new Stats(environment);
    }

	@Test
	public void minimumsShouldMatchTheCreaturesValue() {
		stats.collect();

		for (Stats.Keys key: Stats.Keys.values()) {
			Stat stat = stats.lookup(key);
			double expected = stat.getValue(creature);
			double actual = stat.getMostRecentSnapshot().getMinimum();

			assertEquals(expected, actual, TOLERANCE);
		}
	}

	@Test
	public void averagesShouldMatchTheCreaturesValue() {
		stats.collect();

		for (Stats.Keys key: Stats.Keys.values()) {
			Stat stat = stats.lookup(key);
			double expected = stat.getValue(creature);
			double actual = stat.getMostRecentSnapshot().getAverage();

			assertEquals(expected, actual, TOLERANCE);
		}
	}

	@Test
	public void maximumsShouldMatchTheCreaturesValue() {
		stats.collect();

		for (Stats.Keys key: Stats.Keys.values()) {
			Stat stat = stats.lookup(key);
			double expected = stat.getValue(creature);
			double actual = stat.getMostRecentSnapshot().getMaximum();

			assertEquals(expected, actual, TOLERANCE);
		}
	}

	@Test
	public void mediansShouldMatchTheCreaturesValue() {
		stats.collect();

		for (Stats.Keys key: Stats.Keys.values()) {
			Stat stat = stats.lookup(key);
			double expected = stat.getValue(creature);
			double actual = stat.getMostRecentSnapshot().getMedian();

			assertEquals(expected, actual, TOLERANCE);
		}
	}

	@Test
	public void standardDeviationsShouldBeZero() {
		stats.collect();

		double expected = 0;

		for (Stats.Keys key: Stats.Keys.values()) {
			Stat stat = stats.lookup(key);
			double actual = stat.getMostRecentSnapshot().getStandardDeviation();

			assertEquals(expected, actual, TOLERANCE);
		}
	}
}
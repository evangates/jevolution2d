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
public class StatsTests_WithMultipleCreatureEnvironment {
	private final static double TOLERANCE = 0.001;

	Environment environment;
	Stats stats;
	Creature first;
	Creature second;

    @Before
    public void setUp() {
		environment = new Environment(100, 100);
		first = environment.newRandomCreature();
		second = environment.newRandomCreature();

		environment.addCreature(first);
		environment.addCreature(second);

		stats = new Stats(environment);
    }

	@Test
	public void minimumsShouldMatchTheCreaturesValues() {
		stats.collect();

		for (Stats.Keys key: Stats.Keys.values()) {
			Stat stat = stats.lookup(key);
			double expected = Math.min(stat.getValue(first), stat.getValue(second));
			double actual = stat.getMostRecentSnapshot().getMinimum();

			assertEquals(expected, actual, TOLERANCE);
		}
	}

	@Test
	public void averagesShouldMatchTheCreaturesValues() {
		stats.collect();

		for (Stats.Keys key: Stats.Keys.values()) {
			Stat stat = stats.lookup(key);
			double expected = 0.5 * (stat.getValue(first) + stat.getValue(second));
			double actual = stat.getMostRecentSnapshot().getAverage();

			assertEquals(expected, actual, TOLERANCE);
		}
	}

	@Test
	public void maximumsShouldMatchTheCreaturesValues() {
		stats.collect();

		for (Stats.Keys key: Stats.Keys.values()) {
			Stat stat = stats.lookup(key);
			double expected = Math.max(stat.getValue(first), stat.getValue(second));
			double actual = stat.getMostRecentSnapshot().getMaximum();

			assertEquals(expected, actual, TOLERANCE);
		}
	}

	@Test
	public void standardDeviationsShouldMatchTheCreaturesValues() {
		stats.collect();

		for (Stats.Keys key: Stats.Keys.values()) {
			Stat stat = stats.lookup(key);
			double firstValue = stat.getValue(first);
			double secondValue = stat.getValue(second);
			double average = 0.5 * (firstValue + secondValue);
			double sumOfDiffsSquared = (firstValue - average)*(firstValue - average) + (secondValue - average)*(secondValue - average);
			double expected = Math.sqrt(sumOfDiffsSquared / 2);
			double actual = stat.getMostRecentSnapshot().getStandardDeviation();

			assertEquals(expected, actual, TOLERANCE);
		}
	}
}
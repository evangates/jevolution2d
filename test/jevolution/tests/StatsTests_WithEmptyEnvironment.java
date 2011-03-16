package jevolution.tests;

import jevolution.Environment;
import jevolution.Stats;
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
	public void averageEnergyShouldBeZero() {
		stats.collect();

		double actual = stats.lookup(Stats.Keys.AverageEnergy);
		double expected = 0;

		assertEquals(expected, actual, TOLERANCE);
	}
}
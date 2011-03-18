package jevolution.tests;

import jevolution.Environment;
import jevolution.stats.Maximum;
import jevolution.stats.Mean;
import jevolution.stats.Minimum;
import jevolution.stats.StandardDeviation;
import jevolution.stats.StatReport;
import jevolution.stats.StatReports;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kuhlmancer
 */
public class StatsTests_WithEmptyEnvironment {
	Environment environment;
	StatReports stats;

	final static double TOLERANCE = 0.0001;
	
    @Before
    public void setUp() {
		environment = new Environment(100, 100);
		stats = new StatReports(environment);
    }

	@Test
	public void minimumsShouldBeZero() {
		stats.collect();

		double expected = 0;

		for (StatReports.Keys key: StatReports.Keys.values()) {
			StatReport stat = stats.lookup(key);
			double actual = stat.getMostRecentSnapshot().getStat(Minimum.class);

			assertEquals(expected, actual, TOLERANCE);
		}
	}

	@Test
	public void averagesShouldBeZero() {
		stats.collect();

		double expected = 0;

		for (StatReports.Keys key: StatReports.Keys.values()) {
			StatReport stat = stats.lookup(key);
			double actual = stat.getMostRecentSnapshot().getStat(Mean.class);

			assertEquals(expected, actual, TOLERANCE);
		}
	}

	@Test
	public void maximumsShouldBeZero() {
		stats.collect();

		double expected = 0;

		for (StatReports.Keys key: StatReports.Keys.values()) {
			StatReport stat = stats.lookup(key);
			double actual = stat.getMostRecentSnapshot().getStat(Maximum.class);

			assertEquals(expected, actual, TOLERANCE);
		}
	}

	@Test
	public void standardDeviationsShouldBeZero() {
		stats.collect();

		double expected = 0;

		for (StatReports.Keys key: StatReports.Keys.values()) {
			StatReport stat = stats.lookup(key);
			double actual = stat.getMostRecentSnapshot().getStat(StandardDeviation.class);

			assertEquals(expected, actual, TOLERANCE);
		}
	}
}
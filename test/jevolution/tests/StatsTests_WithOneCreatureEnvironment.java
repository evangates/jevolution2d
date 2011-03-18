package jevolution.tests;

import jevolution.stats.StatReport;
import jevolution.Creature;
import jevolution.Environment;
import jevolution.expressions.CreatureExpression;
import jevolution.stats.Maximum;
import jevolution.stats.Mean;
import jevolution.stats.Minimum;
import jevolution.stats.StandardDeviation;
import jevolution.stats.StatReports;
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
	StatReports stats;
	Creature creature;

    @Before
    public void setUp() {
		environment = new Environment(100, 100);
		environment.setStrengthExpression(new CreatureExpression("blue"));
		environment.setCostOfLivingExpression(new CreatureExpression("red"));
		creature = environment.newRandomCreature();

		environment.addCreature(creature);

		stats = new StatReports(environment);
    }

	@Test
	public void minimumsShouldMatchTheCreaturesValue() {
		stats.collect();

		for (StatReports.Keys key: StatReports.Keys.values()) {
			StatReport stat = stats.lookup(key);
			double expected = stat.getValue(creature);
			double actual = stat.getMostRecentSnapshot().getStat(Minimum.class);

			assertEquals(expected, actual, TOLERANCE);
		}
	}

	@Test
	public void averagesShouldMatchTheCreaturesValue() {
		stats.collect();

		for (StatReports.Keys key: StatReports.Keys.values()) {
			StatReport stat = stats.lookup(key);
			double expected = stat.getValue(creature);
			double actual = stat.getMostRecentSnapshot().getStat(Mean.class);

			assertEquals(expected, actual, TOLERANCE);
		}
	}

	@Test
	public void maximumsShouldMatchTheCreaturesValue() {
		stats.collect();

		for (StatReports.Keys key: StatReports.Keys.values()) {
			StatReport stat = stats.lookup(key);
			double expected = stat.getValue(creature);
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
package jevolution.stats.tests;

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
public class StatsTests_WithMultipleCreatureEnvironment {
	private final static double TOLERANCE = 0.001;

	Environment environment;
	StatReports stats;
	Creature first;
	Creature second;

    @Before
    public void setUp() {
		environment = new Environment(100, 100);
		environment.setStrengthExpression(new CreatureExpression("blue"));
		environment.setCostOfLivingExpression(new CreatureExpression("red"));
		
		first = environment.newRandomCreature();
		second = environment.newRandomCreature();

		environment.addCreature(first);
		environment.addCreature(second);

		stats = new StatReports(environment);
    }

	@Test
	public void minimumsShouldMatchTheCreaturesValues() {
		stats.collect();

		for (StatReports.Keys key: StatReports.Keys.values()) {
			StatReport stat = stats.lookup(key);
			double expected = Math.min(stat.getValue(first), stat.getValue(second));
			double actual = stat.getMostRecentSnapshot().getStat(Minimum.class);

			assertEquals(expected, actual, TOLERANCE);
		}
	}

	@Test
	public void averagesShouldMatchTheCreaturesValues() {
		stats.collect();

		for (StatReports.Keys key: StatReports.Keys.values()) {
			StatReport stat = stats.lookup(key);
			double expected = 0.5 * (stat.getValue(first) + stat.getValue(second));
			double actual = stat.getMostRecentSnapshot().getStat(Mean.class);

			assertEquals(expected, actual, TOLERANCE);
		}
	}

	@Test
	public void maximumsShouldMatchTheCreaturesValues() {
		stats.collect();

		for (StatReports.Keys key: StatReports.Keys.values()) {
			StatReport stat = stats.lookup(key);
			double expected = Math.max(stat.getValue(first), stat.getValue(second));
			double actual = stat.getMostRecentSnapshot().getStat(Maximum.class);

			assertEquals(expected, actual, TOLERANCE);
		}
	}

	@Test
	public void standardDeviationsShouldMatchTheCreaturesValues() {
		stats.collect();

		for (StatReports.Keys key: StatReports.Keys.values()) {
			StatReport stat = stats.lookup(key);
			double firstValue = stat.getValue(first);
			double secondValue = stat.getValue(second);
			double average = 0.5 * (firstValue + secondValue);
			double sumOfDiffsSquared = (firstValue - average)*(firstValue - average) + (secondValue - average)*(secondValue - average);
			double expected = Math.sqrt(sumOfDiffsSquared / 2);
			double actual = stat.getMostRecentSnapshot().getStat(StandardDeviation.class);

			assertEquals(expected, actual, TOLERANCE);
		}
	}
}
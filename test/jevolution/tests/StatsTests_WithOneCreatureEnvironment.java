package jevolution.tests;

import jevolution.Creature;
import jevolution.Environment;
import jevolution.Stats;
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
    public void averageEnergyShouldMatchCreaturesEnergy() {
		stats.collect();
		
		double expected = creature.getEnergy();
		double actual = stats.lookup(Stats.Keys.AverageEnergy);

		assertEquals(expected, actual, TOLERANCE);
	}
}
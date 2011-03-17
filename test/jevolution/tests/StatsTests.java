package jevolution.tests;

import jevolution.Environment;
import jevolution.stats.Stats;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kuhlmancer
 */
public class StatsTests {
	Environment environment;
	Stats stats;

	final static double TOLERANCE = 0.0001;

    @Before
    public void setUp() {
		environment = new Environment(100, 100);
		stats = new Stats(environment);
    }

	@Test
    public void ctorTest() {
		assertNotNull(stats);
	}
}
package jevolution.stats.tests;

import jevolution.Environment;
import jevolution.stats.StatReports;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kuhlmancer
 */
public class StatsTests {
	Environment environment;
	StatReports stats;

	final static double TOLERANCE = 0.0001;

    @Before
    public void setUp() {
		environment = new Environment(100, 100);
		stats = new StatReports(environment);
    }

	@Test
    public void ctorTest() {
		assertNotNull(stats);
	}
}
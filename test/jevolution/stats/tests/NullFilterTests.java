package jevolution.stats.tests;

import jevolution.Creature;
import jevolution.Environment;
import jevolution.stats.CreatureFilter;
import jevolution.stats.NullFilter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kuhlmancer
 */
public class NullFilterTests {
	private Environment environment;

    @Before
    public void setUp() {
		environment = new Environment(100, 100);
		for (int i = 0; i < 5; ++i) {
			environment.addCreature(environment.newRandomCreature());
		}

		environment.addCreature(environment.newRandomCreature());
    }

	@Test
	public void nullFilterShouldReturnItsInput() {
		CreatureFilter filter = NullFilter.NULL;
		
		Iterable<Creature> expected = environment.getCreatures();
		Iterable<Creature> actual = filter.filter(expected);
		
		assertEquals(expected, actual);
	}

}
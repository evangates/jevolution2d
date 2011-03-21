package jevolution.stats.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import jevolution.Creature;
import jevolution.Environment;
import jevolution.stats.GenerationFilter;
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
public class GenerationFilterTests {
	private Environment environment;

    @Before
    public void setUp() {
		environment = new Environment(100, 100);
		for (int i = 0; i < 5; ++i) {
			environment.addCreature(environment.newRandomCreature());
		}
    }

    @Test
    public void ctorTest() {
		GenerationFilter filter = new GenerationFilter(1);

		assertNotNull(filter);
	}

	@Test
	public void filterGreaterThanGenerationOneShouldRemoveAllCreatures() {
		GenerationFilter filter = new GenerationFilter(1);

		Iterable<Creature> result = filter.filter(environment.getCreatures());
		boolean hasNext = result.iterator().hasNext();

		assertFalse("Generation filter didn't remove all creatures.", hasNext);
	}

	@Test
	public void filterGreaterThanGenerationNegativeOneShouldKeepAllCreatures() {
		GenerationFilter filter = new GenerationFilter(-1);

		Iterable<Creature> result = filter.filter(environment.getCreatures());

		List<Creature> actual = iterableToList(result);
		List<Creature> expected = iterableToList(environment.getCreatures());

		assertArrayEquals(expected.toArray(), actual.toArray());
	}

	private List<Creature> iterableToList(Iterable<Creature> creatures) {
		List<Creature> retval = new ArrayList<Creature>();

		for (Creature creature: creatures) {
			retval.add(creature);
		}

		return retval;
	}
}
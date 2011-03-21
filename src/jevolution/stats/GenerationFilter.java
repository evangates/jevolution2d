package jevolution.stats;

import java.util.LinkedList;
import java.util.List;
import jevolution.Creature;

/**
 * Filters out creatures that aren't from a generation greater than the
 * given one.  I'll worry about making this class more flexible for 'less than'
 * type filters if I ever actually want them.  Otherwise, just do the simplest
 * thing that could work.
 * 
 * @author kuhlmancer
 */
public class GenerationFilter {
	private long generation;

	public GenerationFilter(long generation) {
		this.generation = generation;
	}

	public Iterable<Creature> filter(Iterable<Creature> creatures) {
		List<Creature> retval = new LinkedList<Creature>();

		for (Creature creature: creatures) {
			if (creature.getGeneration() > generation) {
				retval.add(creature);
			}
		}

		return retval;
	}
}

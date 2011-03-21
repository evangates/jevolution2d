package jevolution.stats;

import jevolution.Creature;

/**
 *
 * @author kuhlmancer
 */
public interface CreatureFilter {
	Iterable<Creature> filter(Iterable<Creature> creatures);
}

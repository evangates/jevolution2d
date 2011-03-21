package jevolution.stats;

import jevolution.Creature;

/**
 *
 * @author kuhlmancer
 */
public class NullFilter implements CreatureFilter {
	public static NullFilter NULL = new NullFilter();
	/**
	 * prevent instantiation
	 */
	private NullFilter() {}

	@Override
	public Iterable<Creature> filter(Iterable<Creature> creatures) {
		return creatures;
	}
}

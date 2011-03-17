package jevolution.stats;

import jevolution.Creature;

/**
 *
 * @author kuhlmancer
 */
public class MaxEnergyStat extends Stat {

	@Override
	public double getValue(Creature creature) {
		return creature.getMaxEnergy();
	}

	@Override
	public String getName() {
		return "Maximum Energy";
	}
}

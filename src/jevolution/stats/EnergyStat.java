package jevolution.stats;

import jevolution.Creature;

/**
 *
 * @author kuhlmancer
 */
class EnergyStat extends Stat {
	@Override
	public double getValue(Creature creature) {
		return creature.getEnergy();
	}

	@Override
	public String getName() {
		return "Energy";
	}
}
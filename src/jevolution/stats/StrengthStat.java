package jevolution.stats;

import jevolution.Creature;

/**
 *
 * @author kuhlmancer
 */
public class StrengthStat extends Stat {
	@Override
	public double getValue(Creature creature) {
		return creature.getStrength();
	}

	@Override
	public String getName() {
		return "Strength";
	}

}

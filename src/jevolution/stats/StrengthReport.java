package jevolution.stats;

import jevolution.Creature;

/**
 *
 * @author kuhlmancer
 */
public class StrengthReport extends StatReport {
	@Override
	public double getValue(Creature creature) {
		return creature.getStrength();
	}

	@Override
	public String getName() {
		return "Strength";
	}

}

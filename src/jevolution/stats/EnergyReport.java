package jevolution.stats;

import jevolution.Creature;

/**
 *
 * @author kuhlmancer
 */
class EnergyReport extends StatReport {
	@Override
	public double getValue(Creature creature) {
		return creature.getEnergy();
	}

	@Override
	public String getName() {
		return "Energy";
	}
}
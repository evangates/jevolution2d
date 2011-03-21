package jevolution.stats;

import jevolution.Creature;

/**
 *
 * @author kuhlmancer
 */
public class BlueReport extends StatReport {

	@Override
	public double getValue(Creature creature) {
		return creature.getBlue();
	}

	@Override
	public String getName() {
		return "Blue";
	}

}

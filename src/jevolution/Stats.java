/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jevolution;

/**
 *
 * @author kuhlmancer
 */
public class Stats {
	public static enum Keys {
		AverageEnergy,
	}

	private Environment environment;

	private double minEnergy;
	private double averageEnergy;
	private double maxEnergy;

	public Stats(Environment environment) {
		this.environment = environment;
	}

	public void collect() {
		minEnergy = Double.MAX_VALUE;
		averageEnergy = 0;
		maxEnergy = 0;
		int counter = 0;

		for (Creature c: environment.getCreatures()) {
			double energy = c.getEnergy();

			if (energy < minEnergy) {
				minEnergy = energy;
			}

			if (energy > maxEnergy) {
				maxEnergy = energy;
			}

			averageEnergy = (averageEnergy * counter + energy) / (counter + 1);

			counter++;
		}
	}

	public double lookup(Keys key) {
		switch (key) {
			case AverageEnergy:
				return averageEnergy;
			default:
				return 0;
		}
	}

}

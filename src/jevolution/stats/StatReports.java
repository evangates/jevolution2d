/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jevolution.stats;

import java.util.EnumMap;
import jevolution.Creature;
import jevolution.Environment;

/**
 *
 * @author kuhlmancer
 */
public class StatReports {
	public static enum Keys {
		Energy,
		MaximumEnergy,
		Strength,
	}

	private final static EnumMap<Keys, StatReport> trackedStats;
	static {
		trackedStats = new EnumMap<Keys, StatReport>(Keys.class);
		trackedStats.put(Keys.Energy, new EnergyReport());
		trackedStats.put(Keys.MaximumEnergy, new MaxEnergyReport());
		trackedStats.put(Keys.Strength, new StrengthReport());
	}

	private Environment environment;

	public StatReports(Environment environment) {
		this.environment = environment;
	}

	public void collect() {
		long time = System.currentTimeMillis();
		Iterable<Creature> creatures = environment.getCreatures();

		for (StatReport stat: trackedStats.values()) {
			stat.saveSnapshot(time, creatures);
		}
	}

	public StatReport lookup(Keys key) {
		return trackedStats.get(key);
	}

	public Iterable<StatReport> getTrackedStats() {
		return trackedStats.values();
	}
}
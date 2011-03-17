/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jevolution.stats;

import java.util.ArrayList;
import java.util.Deque;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import jevolution.Creature;
import jevolution.Environment;

/**
 *
 * @author kuhlmancer
 */
public class Stats {
	public static enum Keys {
		Energy,
	}

	private final static EnumMap<Keys, Stat> trackedStats;
	static {
		trackedStats = new EnumMap<Keys, Stat>(Keys.class);
		trackedStats.put(Keys.Energy, new EnergyStat());
	}

	private Environment environment;

	public Stats(Environment environment) {
		this.environment = environment;
	}

	public void collect(Iterable<Creature> creatures) {
		long time = System.currentTimeMillis();

		for (Stat stat: trackedStats.values()) {
			stat.saveSnapshot(time, creatures);
		}
	}

	public Stat lookup(Keys key) {
		return trackedStats.get(key);
	}
}
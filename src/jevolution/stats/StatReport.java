package jevolution.stats;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import jevolution.Creature;

/**
 *
 * @author kuhlmancer
 */
public abstract class StatReport {
	public final static int MAX_NUM_SNAPSHOTS = 400;
	private final static Stat[] stats = {
		new Maximum(),
		new Mean(),
		new Minimum(),
		new StandardDeviation()
	};

	private Deque<Snapshot> snapshots;

	StatReport() {
		snapshots = new LinkedList<Snapshot>();
	}

	void saveSnapshot(long time, Iterable<Creature> creatures) {
		List<Double> values = new ArrayList<Double>();

		for (Creature creature: creatures) {
			values.add(getValue(creature));
		}

		Map<Class, Double> calculatedStats = new HashMap<Class, Double>();
		for (Stat stat: stats) {
			calculatedStats.put(stat.getClass(), stat.calculate(values, values.size()));
		}

		snapshots.addLast(new Snapshot(time, calculatedStats));
		dropExtraOldSnapshots();
	}

	private void dropExtraOldSnapshots() {
		if (snapshots.size() > MAX_NUM_SNAPSHOTS) {
			snapshots.removeFirst();
		}
	}

	public abstract double getValue(Creature creature);
	public abstract String getName();

	public Snapshot getMostRecentSnapshot() {
		return snapshots.getLast();
	}

	public Iterable<Snapshot> getSnapshots() {
		return snapshots;
	}

	public int getNumSnapshots() {
		return snapshots.size();
	}

	public double getLargestValue() {
		if (snapshots.isEmpty()) {
			return 0;
		}

		double largestValue = Double.MIN_VALUE;

		for (Snapshot snapshot: snapshots) {
			for (double value: snapshot.getValues()) {
				largestValue = Math.max(largestValue, value);
			}
		}

		// Shouldn't have to worry about this still being Double.MIN_VALUE
		// because we're getting snapshots all the time.
		// Should be ok for the value here to be weird at the beginning once.
		return largestValue;
	}

	public double getSmallestValue() {
		if (snapshots.isEmpty()) {
			return 0;
		}

		double smallestValue = Double.MAX_VALUE;

		for (Snapshot snapshot: snapshots) {
			for (double value: snapshot.getValues()) {
				smallestValue = Math.min(smallestValue, value);
			}
		}

		// Shouldn't have to worry about this still being Double.MAX_VALUE
		// because we're getting snapshots all the time.
		// Should be ok for the value here to be weird at the beginning once.
		return smallestValue;
	}

	public long getEarliestTime() {
		return snapshots.getFirst().getTime();
	}

	public long getLatestTime() {
		return snapshots.getLast().getTime();
	}

	@Override
	public String toString() {
		return getName();
	}
}
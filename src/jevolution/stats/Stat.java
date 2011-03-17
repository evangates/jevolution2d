package jevolution.stats;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import jevolution.Creature;

/**
 *
 * @author kuhlmancer
 */
public abstract class Stat {
	private Deque<Snapshot> snapshots;
	private final static int numSnapshotsToKeep = 20;

	Stat() {
		snapshots = new LinkedList<Snapshot>();
	}

	void saveSnapshot(long time, Iterable<Creature> creatures) {
		List<Double> values = new ArrayList<Double>();

		double minimum = Double.MAX_VALUE;
		double maximum = 0;
		double median = 0;
		double average = 0;
		double standardDeviation = 0;
		int numValues = 0;

		// min, max, sum
		double sum = 0;
		for (Creature creature: creatures) {
			double value = getValue(creature);
			values.add(value);

			minimum = Math.min(minimum, value);
			maximum = Math.max(maximum, value);

			sum += value;
			++numValues;
		}

		if (values.isEmpty()) {
			// 0 makes more sense than Double.MAX_VALUE as a default min when there are no creatures
			// only reason we started with MAX_VALUE was to make sure the first creature would have a lower value
			minimum = 0;
		}
		else {
			average = sum / numValues;
			
			// standard deviation
			double sumOfSquares = 0;
			for(double value: values) {
				double difference = value - average;
				sumOfSquares += difference * difference;
			}
			standardDeviation = Math.sqrt(sumOfSquares / numValues);

			// median
			if (numValues % 2 == 0) {
				median = 0.5 * values.get(numValues / 2) + values.get(numValues / 2 + 1);
			}
			else {
				median = values.get(numValues / 2);
			}
		}

		snapshots.addLast(new Snapshot(time, minimum, maximum, average, standardDeviation, median));
		dropExtraOldSnapshots();
	}

	private void dropExtraOldSnapshots() {
		if (snapshots.size() > numSnapshotsToKeep) {
			snapshots.removeFirst();
		}
	}

	public abstract double getValue(Creature creature);

	public Snapshot getMostRecentSnapshot() {
		return snapshots.getLast();
	}
}

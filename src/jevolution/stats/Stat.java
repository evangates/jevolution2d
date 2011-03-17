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
	public final static int MAX_NUM_SNAPSHOTS = 400;

	Stat() {
		snapshots = new LinkedList<Snapshot>();
	}

	void saveSnapshot(long time, Iterable<Creature> creatures) {
		List<Double> values = new ArrayList<Double>();

		double minimum = Double.MAX_VALUE;
		double maximum = 0;
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
		}

		snapshots.addLast(new Snapshot(time, minimum, maximum, average, standardDeviation));
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
			largestValue = Math.max(largestValue, snapshot.getMaximum());
		}

		return largestValue;
	}

	public double getSmallestValue() {
		if (snapshots.isEmpty()) {
			return 0;
		}

		double smallestValue = Double.MAX_VALUE;

		for (Snapshot snapshot: snapshots) {
			smallestValue = Math.min(smallestValue, snapshot.getMinimum());
		}

		return smallestValue;
	}

	public long getEarliestTime() {
		return snapshots.getFirst().getTime();
	}

	public long getLatestTime() {
		return snapshots.getLast().getTime();
	}
}
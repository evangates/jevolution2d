package jevolution.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;
import jevolution.stats.Maximum;
import jevolution.stats.Mean;
import jevolution.stats.Minimum;
import jevolution.stats.Snapshot;
import jevolution.stats.StandardDeviation;
import jevolution.stats.StatReport;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kuhlmancer
 */
public class Graph extends JPanel {
	private final static int NUM_INTERVALS = 20;
	private final static int TICK_PIXEL_LENGTH = 5;

	JComboBox comboBox;
	GraphInnerPanel graph;

	Graph(Iterable<StatReport> stats) {
		super(new MigLayout("wrap, insets 0", "[grow]", "[][grow]"));

		this.comboBox = new JComboBox();
		for (StatReport st: stats) {
			comboBox.addItem(st);
		}

		StatReport selectedStat = (StatReport)comboBox.getSelectedItem();

		graph = new GraphInnerPanel(selectedStat);

		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					StatReport selectedStat = (StatReport)e.getItem();

					graph.setStat(selectedStat);
				}
			}
		});

		this.add(comboBox);
		this.add(graph, "grow");
	}

	private class GraphInnerPanel extends JPanel {
		private StatReport stat;

		GraphInnerPanel(StatReport stat) {
			this.stat = stat;
			this.setBackground(Color.white);
		}

		void setStat(StatReport stat) {
			this.stat = stat;
		}

		@Override
		protected void paintComponent(Graphics gr) {
			super.paintComponent(gr);
			Graphics2D g = (Graphics2D)gr;

			double largestYValue = stat.getLargestValue();
			double smallestYValue = stat.getSmallestValue();
			double yDifference = largestYValue - smallestYValue;
			double yInterval = yDifference / NUM_INTERVALS;

			// add some padding to the top and bottom y values
			// so they aren't at the edge of the graph
			double paddedLargestYValue = largestYValue + yInterval;
			double paddedSmallestYValue = smallestYValue - yInterval;

			long largestXValue = stat.getLatestTime();
			long smallestXValue = stat.getEarliestTime();
			long xDifference = largestXValue - smallestXValue;
			long xInterval = xDifference / NUM_INTERVALS;

			for (Snapshot snapshot: stat.getSnapshots()) {
				long time = snapshot.getTime();

				double average = snapshot.getStat(Mean.class);
				double standardDeviation = snapshot.getStat(StandardDeviation.class);
				double minimum = snapshot.getStat(Minimum.class);
				double maximum = snapshot.getStat(Maximum.class);

				// standard deviation
				g.setColor(Color.green);
				drawStandardDeviation(g, smallestXValue, largestXValue, paddedSmallestYValue, paddedLargestYValue, time, average, standardDeviation);
				
				// minimum
				g.setColor(Color.red);
				drawPoint(g, smallestXValue, largestXValue, paddedSmallestYValue, paddedLargestYValue, time, minimum);

				// average
				g.setColor(Color.black);
				drawPoint(g, smallestXValue, largestXValue, paddedSmallestYValue, paddedLargestYValue, time, average);

				// maximum
				g.setColor(Color.blue);
				drawPoint(g, smallestXValue, largestXValue, paddedSmallestYValue, paddedLargestYValue, time, maximum);
			}

			// zero line
			g.setColor(Color.black);
			drawHorizontalLine(g, paddedSmallestYValue, paddedLargestYValue, 0);

			// max value
			drawYValue(g, paddedSmallestYValue, paddedLargestYValue, largestYValue);

			// min value
			drawYValue(g, paddedSmallestYValue, paddedLargestYValue, smallestYValue);
		}

		private void drawYValue(Graphics2D g, double minY, double maxY, double yValue) {
			int height = getHeight();
			String formattedValueStr = String.format("%5.2f", yValue);

			double scaledY = (yValue - minY) / (maxY - minY) * height;

			g.drawString(formattedValueStr, 1, height - (float)scaledY);
		}

		private void drawHorizontalLine(Graphics2D g, double minY, double maxY, double lineYValue) {
			int height = getHeight();

			double scaledY = (lineYValue - minY) / (maxY - minY) * height;

			// remember to flip the y-axis to get into normal cartesian coords
			int y = height - (int)Math.round(scaledY);

			g.drawLine(0, y, getWidth(), y);
		}

		private void drawPoint(Graphics2D g, double minX, double maxX, double minY, double maxY, double xValue, double yValue) {
			int width = getWidth();
			int height = getHeight();

			double scaledX = (xValue - minX) / (maxX - minX) * width;
			double scaledY = (yValue - minY) / (maxY - minY) * height;

			int x = (int)Math.round(scaledX);
			// remember (0,0) is in the upper left corner
			// have to flip y here so our graph has (0,0) at the bottom left
			int y = height - (int)Math.round(scaledY);

			g.drawRect(x, y, 1, 1);
		}

		private void drawStandardDeviation(Graphics2D g, double minX, double maxX, double minY, double maxY, double xValue, double midLineYValue, double lineHeight) {
			int width = getWidth();
			int height = getHeight();

			double scaledX = (xValue - minX) / (maxX - minX) * width;
			double scaledMinY = (midLineYValue - lineHeight / 2 - minY) / (maxY - minY) * height;
			double scaledMaxY = (midLineYValue + lineHeight / 2 - minY) / (maxY - minY) * height;

			int x = (int)Math.round(scaledX);

			// remember (0,0) is in the upper left corner
			// have to flip y here so our graph has (0,0) at the bottom left
			int yMin = height - (int)Math.round(scaledMinY);
			int yMax = height - (int)Math.round(scaledMaxY);

			g.drawLine(x, yMin, x, yMax);
		}
	}
}

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

			long largestXValue = stat.getLatestTime();
			long smallestXValue = stat.getEarliestTime();
			double xDifference = largestXValue - smallestXValue;
			double xInterval = xDifference / NUM_INTERVALS;

			for (Snapshot snapshot: stat.getSnapshots()) {
				long time = snapshot.getTime();

				double average = snapshot.getStat(Mean.class);
				double standardDeviation = snapshot.getStat(StandardDeviation.class);
				double minimum = snapshot.getStat(Minimum.class);
				double maximum = snapshot.getStat(Maximum.class);

				// standard deviation
				g.setColor(Color.green);
				drawStandardDeviation(g, smallestXValue, largestXValue, smallestYValue, largestYValue, time, average, standardDeviation);
				
				// minimum
				g.setColor(Color.red);
				drawPoint(g, smallestXValue, largestXValue, smallestYValue, largestYValue, time, minimum);

				// average
				g.setColor(Color.black);
				drawPoint(g, smallestXValue, largestXValue, smallestYValue, largestYValue, time, average);

				// maximum
				g.setColor(Color.blue);
				drawPoint(g, smallestXValue, largestXValue, smallestYValue, largestYValue, time, maximum);
			}
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

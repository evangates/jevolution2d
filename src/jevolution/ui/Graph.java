package jevolution.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;
import jevolution.stats.Snapshot;
import jevolution.stats.Stat;

/**
 *
 * @author kuhlmancer
 */
public class Graph extends JPanel {
	private final static int NUM_INTERVALS = 20;
	private Stat stat;
	private String title;

	Graph(Stat stat) {
		this.stat = stat;

		title = stat.getName();
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

		g.drawString(title, 0, 10);

		for (Snapshot snapshot: stat.getSnapshots()) {
			long time = snapshot.getTime();
			// minimum
			g.setColor(Color.red);
			drawPoint(g, smallestXValue, largestXValue, smallestYValue, largestYValue, time, snapshot.getMinimum());

			// average
			g.setColor(Color.black);
			drawPoint(g, smallestXValue, largestXValue, smallestYValue, largestYValue, time, snapshot.getAverage());

			// maximum
			g.setColor(Color.blue);
			drawPoint(g, smallestXValue, largestXValue, smallestYValue, largestYValue, time, snapshot.getMaximum());
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
}

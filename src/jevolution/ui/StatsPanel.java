package jevolution.ui;

import java.awt.Dimension;
import javax.swing.*;
import jevolution.stats.StatReports;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kuhlmancer
 */
public class StatsPanel extends JPanel {
	StatReports stats;

	Graph graph;

	public StatsPanel(int width, int height, StatReports stats) {
		super(new MigLayout("fill, wrap"));
		this.stats = stats;

		graph = new Graph(stats.getTrackedStats());
		graph.setPreferredSize(new Dimension(width, height - 20));

		add(graph, "grow");
	}
}

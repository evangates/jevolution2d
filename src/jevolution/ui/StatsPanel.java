package jevolution.ui;

import java.awt.Dimension;
import javax.swing.*;
import jevolution.stats.Stats;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kuhlmancer
 */
public class StatsPanel extends JPanel {
	Stats stats;

	Graph graph;

	public StatsPanel(int width, int height, Stats stats) {
		super(new MigLayout("fill, wrap"));
		this.stats = stats;

		graph = new Graph(stats.lookup(Stats.Keys.Energy));
		graph.setPreferredSize(new Dimension(width, height - 20));

		add(graph, "grow");
	}
}

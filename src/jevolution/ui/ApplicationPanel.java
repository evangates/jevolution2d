package jevolution.ui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import jevolution.Environment;
import jevolution.expressions.CreatureExpression;
import jevolution.stats.CreatureFilter;
import jevolution.stats.GenerationFilter;
import jevolution.stats.StatReports;
import net.miginfocom.swing.MigLayout;


public class ApplicationPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private final static int TICK_INTERVAL = 15;
	private final static int STAT_INTERVAL = 100;

	private CreatureFilter filter;
	private Environment environment;
	private EnvironmentPanel environmentPanel;
	private StatReports stats;
	private StatsPanel statsPanel;
	private Timer tickTimer;
	private Timer statTimer;
	private long startTime = 0;
	private int numFrames = 0;
	private double fps = 60.0f;
	private int width, height;

	private double speedModifier;
	
	public ApplicationPanel() {
		super(new MigLayout("fill, wrap", "[grow, center]", "[center, top][grow, center, top]"));

		width = 800;
		height = 600;

		environment = new Environment(width, height);
		environment.addCreatures();

		filter = new GenerationFilter(1);
		stats = new StatReports(environment, filter);
		
		environmentPanel = new EnvironmentPanel(environment);
		statsPanel = new StatsPanel(width, height, stats);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		tabbedPane.addTab("World", environmentPanel);
		tabbedPane.addTab("Statistics", statsPanel);

		this.add(tabbedPane);

		JPanel configPanel = new JPanel(new MigLayout("fill, insets 0", "[grow | ]", "[grow, top]"));
		configPanel.add(new ExpressionPanel(this), "grow");
		configPanel.add(new InfoPanel());

		this.add(configPanel, "grow");

		speedModifier = 1d;
		tickTimer = new Timer(TICK_INTERVAL, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateFPS();
				environment.tick(speedModifier / fps);
				environmentPanel.repaint();
			}
		});
		statTimer = new Timer(STAT_INTERVAL, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// don't collect stats if we're paused
				if (speedModifier > 0) {
					stats.collect();
				}
				statsPanel.repaint();
			}
		});

		tickTimer.start();
		statTimer.start();

		this.setVisible(true);
	}

	public void updateFPS() {
		++numFrames;
		if (startTime == 0) {
			startTime = System.currentTimeMillis();
		} else {
			long currentTime = System.currentTimeMillis();
			long delta = (currentTime - startTime);
			if (delta > 1000) {
				fps = (numFrames * 1000) / delta;
				if (fps <= 0) {
					fps = 60;
				}
				numFrames = 0;
				startTime = currentTime;
			}
		}
	}

	/*
	 * This is a hack to funnel change messages to the environment
	 * when the user updates one of the changable functions.
	 *
	 * Should probably do proper custom events.  Those require extra thought and
	 * work that can be put off for now, as eventing isn't the focus of this project.
	 */
	public void updateExpression(CreatureExpression expr, ExpressionId id) {
		switch(id) {
			case STRENGTH:
				environment.setStrengthExpression(expr);
				break;
			case COST_OF_LIVING:
				environment.setCostOfLivingExpression(expr);
				break;
		}
	}

	/*
	 * This is a hack to funnel change messages to the environment
	 * when the user updates one of the changable values.
	 *
	 * Should probably do proper custom events.  Those require extra thought and
	 * work that can be put off for now, as eventing isn't the focus of this project.
	 */
	public void updateValue(double value, ValueId id) {
		switch(id) {
			case SIMULATION_SPEED:
				this.speedModifier = value;
				break;
			case MATINGS_PER_SECOND:
				environment.setMatingsPerSecond(value);
				break;
			case RANDOM_CREATURES_PER_SECOND:
				environment.setRandomCreaturesPerSecond(value);
				break;
		}
	}
}

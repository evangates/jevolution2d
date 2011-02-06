package jevolution.ui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import jevolution.expressions.CreatureExpression;
import net.miginfocom.swing.MigLayout;


public class ApplicationPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private final static int TICK_INTERVAL = 15;

	private EnvironmentPanel canvas;
	private Timer timer;
	private long startTime = 0;
	private int numFrames = 0;
	private double fps = 60.0f;
	private int width, height;

	private double speedModifier;
	
	public ApplicationPanel() {
		super(new MigLayout("fill, wrap", "[grow, center]", "[center, top][grow, center, top]"));

		width = 800;
		height = 600;

		canvas = new EnvironmentPanel(width, height);
		this.add(canvas);

		JPanel configPanel = new JPanel(new MigLayout("fill, insets 0", "[grow | ]", "[grow, top]"));
		configPanel.add(new ExpressionPanel(this), "grow");
		configPanel.add(new InfoPanel());

		this.add(configPanel, "grow");

		speedModifier = 1d;
		timer = new Timer(TICK_INTERVAL, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateFPS();
				canvas.tick(speedModifier / fps);
				canvas.repaint();
			}
		});
		timer.start();

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

	public void updateExpression(CreatureExpression expr, ExpressionId id) {
		switch(id) {
			case STRENGTH:
				canvas.setStrengthExpression(expr);
				break;
			case COST_OF_LIVING:
				canvas.setCostOfLivingExpression(expr);
				break;
		}
	}

	public void updateValue(double value, ValueId id) {
		switch(id) {
			case SIMULATION_SPEED:
				this.speedModifier = value;
				break;
			case MATINGS_PER_SECOND:
				canvas.setMatingsPerSecond(value);
				break;
		}
	}
}

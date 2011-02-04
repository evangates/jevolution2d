package jevolution.ui;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import jevolution.expressions.CreatureExpression;
import net.miginfocom.swing.MigLayout;


public class ApplicationPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private EnvironmentPanel canvas;
	private Timer timer;
	private long startTime = 0;
	private int numFrames = 0;
	private double fps = 60.0f;
	private int width, height;
	
	public ApplicationPanel() {
		super(new MigLayout("fill, wrap", "[grow, center]", "[grow, center][grow, center]"));

		width = 800;
		height = 600;

		canvas = new EnvironmentPanel(width, height);
		this.add(canvas);

		JPanel configPanel = new JPanel(new MigLayout("fill, insets 0", "[grow | ]", "[grow, top]"));
		configPanel.add(new ExpressionPanel(this), "grow");
		configPanel.add(new InfoPanel());

		this.add(configPanel, "grow");

		timer = new Timer(20, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateFPS();
				canvas.tick(1/fps);
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

	public void updateStrengthFunction(CreatureExpression expr) {
		canvas.setStrengthExpression(expr);
	}
}

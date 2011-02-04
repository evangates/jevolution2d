package jevolution.ui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;


public class ApplicationPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private EnvironmentPanel canvas;
	private CreatureExpressionTextField strengthFunctionTextField;
	private Timer timer;
	private long startTime = 0;
	private int numFrames = 0;
	private double fps = 60.0f;
	private int width, height;
	
	public ApplicationPanel() {
		super(new MigLayout("fill", "center"));

		String initialStrengthFunction = "-red - green + blue + 3*(width - height)";

		width = 800;
		height = 600;

		canvas = new EnvironmentPanel(width, height);
		this.add(canvas, "wrap");

		JPanel configPanel = new JPanel(new MigLayout("fill", String.format("[%d]", width)));
		this.add(configPanel);

		// strength function
		configPanel.add(new JLabel("Strength function:"), "left, grow, wrap");
		strengthFunctionTextField = new CreatureExpressionTextField(initialStrengthFunction, this);
		updateStrengthFunction();
		configPanel.add(strengthFunctionTextField, "left, grow, wrap");

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

	public void updateStrengthFunction() {
		canvas.setStrengthExpression(strengthFunctionTextField.getExpression());
	}
}

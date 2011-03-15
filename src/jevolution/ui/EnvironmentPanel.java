package jevolution.ui;

import jevolution.Environment;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import jevolution.Creature;


public class EnvironmentPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private Environment environment;
	
	public EnvironmentPanel(Environment environment) {
		Dimension size = new Dimension(environment.getWidth(),environment.getHeight());
		setSize(size);
		setPreferredSize(size);
		
		setBackground(Color.DARK_GRAY);
		
		this.environment = environment;
	}
	
	@Override
	public void paintComponent(Graphics gc) {
		super.paintComponent(gc); // clear off-screen bitmap

		Graphics2D g = (Graphics2D) gc;

		for(Creature t : environment.getCreatures()) {
			t.draw(g);
		}
	}
}

package jevolution.ui;

import jevolution.Environment;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import jevolution.Thing;
import jevolution.expressions.CreatureExpression;


public class EnvironmentPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private Environment environment;
	
	public EnvironmentPanel(int width, int height) {
		Dimension size = new Dimension(width,height);
		setSize(size);
		setPreferredSize(size);
		
		setBackground(Color.DARK_GRAY);
		
		environment = new Environment(width, height, this);
	}
	
	@Override
	public void paintComponent(Graphics gc) {
		super.paintComponent(gc); // clear off-screen bitmap

		Graphics2D g = (Graphics2D) gc;

		for(Thing t : environment.getCreatures()) {
			t.draw(g);
		}
	}

	public void tick(double timePerFrame) {
		environment.tick(timePerFrame);
	}

	public CreatureExpression getStrengthExpression() {
		return environment.getStrengthExpression();
	}

	public CreatureExpression getCostOfLivingExpression() {
		return environment.getCostOfLivingExpression();
	}

	public void setStrengthExpression(CreatureExpression expr) {
		environment.setStrengthExpression(expr);
	}

	public void setCostOfLivingExpression(CreatureExpression expr) {
		environment.setCostOfLivingExpression(expr);
	}

	public void setMatingsPerSecond(double matingsPerSecond) {
		environment.setMatingsPerSecond(1d/matingsPerSecond);
	}

	public void setRandomCreaturesPerSecond(double creaturesPerSecond) {
		environment.setRandomCreaturesPerSecond(1d/creaturesPerSecond);
	}
}

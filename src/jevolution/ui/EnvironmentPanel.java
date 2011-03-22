package jevolution.ui;

import jevolution.Environment;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;
import jevolution.Creature;

/*
 * Contains code related to drawing the environment and creatures within it.
 * There aren't enough types of things to draw right now (3/15/2010) to worry
 * about splitting stuff up more.
 *
 * @author Evan Gates
 */
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
			drawCreature(g, t);
		}
	}

	public void drawCreature(Graphics2D g, Creature creature) {
		g.setColor(creature.getColor());

		Shape shape = creature.getShape();

		g.fill(shape);

		g.setColor(Color.WHITE);
		
		double mag = headingLineMagnitude(creature);
		int x = (int)creature.getX();
		int y = (int)creature.getY();
		double angle = creature.getAngle();
		g.drawLine((int) x, (int) y,
				(int) Math.round(x + mag * Math.cos(angle)),
				(int) Math.round(y + mag * Math.sin(angle)));

		String energyStr = String.format("%.0f / %.0f", creature.getEnergy(), creature.getMaxEnergy());
		if (creature.getEnergy() > creature.getMinEnergyToMate()) {
			energyStr = "+ " + energyStr;
		}
		g.drawString(energyStr, (int) x, (int) y);

		double strength = creature.getStrength();

		String ageStr = String.format(Math.abs(strength) < 1 ? "%.2f" : "%.0f", strength);
		g.drawString(ageStr, (int)x, (int)y + 10);

		g.drawString("" + creature.getGeneration(), (int)x, (int)y + 20);
	}

	private double headingLineMagnitude(Creature c) {
		return (c.getVelocity() / c.getMaxVelocity()) * 1.5d * c.getHeight();
	}
}

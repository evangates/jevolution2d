package jevolution;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JPanel;


final class EnvironmentPanel extends JPanel {
	final static double maxVelocity = 400;
	
	final static int minNumCreatures = 50;
	final static int preferredNumCreatures = 75;
	final static int maxNumCreatures = 100;
	final static double secondsBetweenRandoms = 1;
	final static double secondsBetweenMatings = 0.05;
	
	private static final long serialVersionUID = 1L;
	private LinkedList<Creature> creatures;
	int height;
	
	int width;
	
	double timeSinceLastRandom;
	double timeSinceLastMating;
	
	public EnvironmentPanel(int width, int height) {
		this.width = width;
		this.height = height;
		
		Dimension size = new Dimension(width,height);
		setSize(size);
		setPreferredSize(size);
		
		setBackground(Color.DARK_GRAY);
		
		creatures = new LinkedList<Creature>();
		
		addThings();
		
		timeSinceLastRandom = 0;
	}
	
	private void addThings() {
		for(int i = 0; i < preferredNumCreatures; ++i) {
			creatures.add(Creature.random(this));
		}
	}
	
	private void drawLines(Graphics2D g) {
		int spacing = 40;

		// lines
		g.setColor(Color.white);
		for (int cursor = 1; cursor <= width/spacing; ++cursor) {
			g.drawLine(cursor * spacing, 0, cursor * spacing, height);
		}
		for (int cursor = 1; cursor <= height/spacing; ++cursor) {
			g.drawLine(0, cursor * spacing, width, cursor * spacing);
		}

		// line numbers
		g.setColor(Color.red);
		for (int cursor = 1; cursor <= width/spacing; ++cursor) {
			g.drawString(cursor * spacing + "", cursor * spacing - 22, 10);
		}

		for (int cursor = 1; cursor <= height/spacing; ++cursor) {
			g.drawString(cursor * spacing + "", 2, cursor * spacing - 2);

		}
	}
	
	@Override
	public void paintComponent(Graphics gc) {
		super.paintComponent(gc); // clear off-screen bitmap

		Graphics2D g = (Graphics2D) gc;

//		drawLines(g);
		
		for(Thing t : creatures) {
			t.draw(g);
		}
	}
	
	public void tick(double timePerFrame) {
		for(Thing t: creatures) {
			t.tick(timePerFrame);
		}
		
		LinkedList<Thing> deads = new LinkedList<Thing>();
		
		for(Thing t: creatures) {
			for (Thing other: creatures) {
				if (t != other) {
					if (t.getShape().intersects(other.getShape().getBounds2D())) {
						t.interactWith(other, timePerFrame);
					}
				}
			}
		}
		
		for(Thing t: creatures) {
			if (t.isDead()) {
				deads.add(t);
			}
			else {
				t.clipEnergy();
			}
		}
		
		creatures.removeAll(deads);
		
		while (creatures.size() < minNumCreatures) {
			creatures.add(Creature.random(this));
		}
		
		if (timeSinceLastRandom > secondsBetweenRandoms && creatures.size() < preferredNumCreatures) {
			creatures.add(Creature.random(this));
			timeSinceLastRandom = 0;
		}
		
		if (timeSinceLastMating > secondsBetweenMatings && creatures.size() < maxNumCreatures) {
			Random r = new Random();
			
			int index1 = r.nextInt(creatures.size());
			int index2 = r.nextInt(creatures.size());
			
			Creature one = creatures.get(index1);
			Creature two = creatures.get(index2);
			
			Creature child = Creature.mate(one, two);
	
			if (child != null) {
				creatures.add(child);
				timeSinceLastMating = 0;
			}
		}
		
		timeSinceLastRandom += timePerFrame;
		timeSinceLastMating += timePerFrame;
	}
}

package jevolution.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JPanel;
import jevolution.Creature;
import jevolution.Thing;
import jevolution.expressions.CreatureExpression;


public class EnvironmentPanel extends JPanel {
	public final static double maxVelocity = 400;

	private CreatureExpression strengthExpression;
	private CreatureExpression costOfLivingExpression;
	
	final static int minNumCreatures = 20;
	final static int preferredNumCreatures = 75;
	final static int maxNumCreatures = 100;

	private double timeBetweenRandoms = 1;
	private double timeBetweenMatings = 0.05;
	
	private static final long serialVersionUID = 1L;
	private LinkedList<Creature> creatures;

	private int height;
	private int width;

	private double timeSinceLastRandom;
	private double timeSinceLastMating;
	
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

	public CreatureExpression getStrengthExpression() {
		return this.strengthExpression;
	}

	public CreatureExpression getCostOfLivingExpression() {
		return this.costOfLivingExpression;
	}

	public void setStrengthExpression(CreatureExpression expr) {
		this.strengthExpression = expr;
		System.out.println(String.format("evironment panel strength function updated: %s", expr.toString()));
	}

	public void setCostOfLivingExpression(CreatureExpression expr) {
		this.costOfLivingExpression = expr;
		System.out.println(String.format("environment panel cost of living function updated: %s", expr.toString()));
	}

	public void setMatingsPerSecond(double matingsPerSecond) {
		timeBetweenMatings = 1d/matingsPerSecond;
	}

	public void setRandomCreaturesPerSecond(double creaturesPerSecond) {
		timeBetweenRandoms = 1d/creaturesPerSecond;
	}
	
	@Override
	public void paintComponent(Graphics gc) {
		super.paintComponent(gc); // clear off-screen bitmap

		Graphics2D g = (Graphics2D) gc;

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
		
		if (timeSinceLastRandom > timeBetweenRandoms) {
			creatures.add(Creature.random(this));
			timeSinceLastRandom = 0;
		}
		
		if (timeSinceLastMating > timeBetweenMatings && creatures.size() > 0) {
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

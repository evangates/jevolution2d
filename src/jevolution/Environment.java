/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jevolution;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import jevolution.expressions.CreatureExpression;
import jevolution.ui.EnvironmentPanel;

/**
 * @author kuhlmancer
 */
public class Environment {
	final static int minNumCreatures = 20;
	final static int preferredNumCreatures = 75;
	final static int maxNumCreatures = 100;

	private CreatureExpression strengthExpression;
	private CreatureExpression costOfLivingExpression;

	private int width, height;
	private List<Creature> creatures;

	/*
	 * This should be adjustable through the UI.  Don't assume that it won't change.
	 */
	private double timeBetweenRandoms = 1;

	/*
	 * This should be adjustable through the UI.  Don't assume that it won't change.
	 */
	private double timeBetweenMatings = 0.05;

	private double timeSinceLastRandom;
	private double timeSinceLastMating;

	// TODO: this is here as part of an artifact of factoring Environment out of EnvironmentPanel
	// the dependency should go to other way when done refactoring
	private EnvironmentPanel panel;

	/*
	 * TODO: panel should not be needed when done factoring out Environment.
	 * An Environment instance should be injected into the EnvironmentPanel instead.
	 */
	public Environment(int width, int height, EnvironmentPanel panel) {
		// TODO: remove this when the panel references is no longer needed
		this.panel = panel;

		this.width = width;
		this.height = height;

		timeSinceLastRandom = 0;
		timeSinceLastMating = 0;

		creatures = new LinkedList<Creature>();

		addThings();
	}

	private void addThings() {
		for(int i = 0; i < preferredNumCreatures; ++i) {
			creatures.add(Creature.random(panel));
		}
	}

	/*
	 * Recheck if this is needed after factoring out Environment from EnvironmentPanel.
	 */
	public Iterable<Creature> getCreatures() {
		return creatures;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public double getMaxVelocity() {
		return Math.min(width, height);
	}

	public CreatureExpression getStrengthExpression() {
		return this.strengthExpression;
	}

	public CreatureExpression getCostOfLivingExpression() {
		return this.costOfLivingExpression;
	}

	public void setStrengthExpression(CreatureExpression expr) {
		this.strengthExpression = expr;
	}

	public void setCostOfLivingExpression(CreatureExpression expr) {
		this.costOfLivingExpression = expr;
	}

	public void setMatingsPerSecond(double matingsPerSecond) {
		timeBetweenMatings = 1d/matingsPerSecond;
	}

	public void setRandomCreaturesPerSecond(double creaturesPerSecond) {
		timeBetweenRandoms = 1d/creaturesPerSecond;
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
			creatures.add(Creature.random(panel));
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
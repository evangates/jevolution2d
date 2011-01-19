package thoughtmerge.jevolution;
import java.awt.Graphics2D;
import java.awt.Shape;


public abstract class Thing {
	protected double energy;
	
	public abstract void tick(double timePerFrame);
	
	public abstract void draw(Graphics2D g);
	
	public abstract Shape getShape();
	
	public abstract void interactWith(Thing other, double timePerFrame);
	
	public double getEnergy() {
		return energy;
	}
	
	public void takeEnergyFrom(Thing other, double amount) {
		energy += amount;
		other.energy -= amount;
	}
	
	public void clipEnergy() {
		if (energy > getMaxEnergy()) {
			energy = getMaxEnergy();
		}
	}
	
	protected abstract double getMaxEnergy();
	
	public final boolean isDead() {
		return energy <= 0;
	}
	
	public abstract boolean isViable();
}

package jevolution;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Creature implements Comparable<Creature> {
	private static class Keys {
		public final static String accelRange = "maxAccel";
		public final static String angularRange = "maxAngular";
		public final static String blue = "blue";
		public final static String childEnergyDonation = "childEnergyDonation";
		public final static String green = "green";
		public final static String height = "height";
		public final static String maxVelocity = "maxVelocity";
		public final static String minAccel = "minAccel";
		public final static String minAngular = "minAngular";
		public final static String minEnergyToReproduce = "minEnergyToReproduce";
		public final static String minTimeUntilAccelChange = "minTimeUntilAccelChange";
		public final static String minTimeUntilAngleChange = "minTimeUntilAngleChange";
		public final static String red = "red";
		public final static String timeUntilAccelChangeRange = "timeUntilAccelChangeRange";
		public final static String timeUntilAngleChangeRange = "timeUntilAngleChangeRange";
		public final static String width = "width";
	}
	
	final static double ACCEL_RANGE = 5;
	final static double ANGULAR_RANGE = Math.PI / 20;
	final static int MAX_DIMENSION = 60;
	final static double MAX_VELOCITY = 200;

	final static int MIN_DIMENSION = 4;
	
	private int numChildren;
	private double energy;

	static Random r = new Random();

	public static Creature mate(Creature one, Creature two) {
		if (one.getEnergy() >= one.getReproductionThreshold() && two.getEnergy() > two.getReproductionThreshold()) {
			DNA left = one.getDNA();
			DNA right = two.getDNA();
	
			DNA dna = DNA.splice(left, right);
			dna.mutate();
			
			Creature child = new Creature(one.environment, dna);
			
			if (child.isViable()) {
				child.takeEnergyFrom(one, one.childEnergyDonation);
				child.takeEnergyFrom(two, two.childEnergyDonation);
				one.numChildren++;
				two.numChildren++;
				return child;
			}
		}
		
		return null;
	}

	public static Creature random(Environment environment) {
		final double RANDOM_MAX_VELOCITY = 400d;
		
		int x = r.nextInt(environment.getWidth());
		int y = r.nextInt(environment.getHeight());

		int width = r.nextInt(MAX_DIMENSION - MIN_DIMENSION) + MIN_DIMENSION;
		int height = r.nextInt(MAX_DIMENSION - MIN_DIMENSION) + MIN_DIMENSION;

		double minAccel = 0;
		double accelRange = r.nextDouble() * ACCEL_RANGE;

		double minAngular = 0;
		// up to a quarter of a turn per second
		double angularRange = r.nextDouble() * Math.PI / 2;

		double minTimeUntilAccelChange = 0.1d * r.nextDouble() + 0.01;
		double ticksUntilAccelChangeRange = 2.5d * r.nextDouble() + minTimeUntilAccelChange;

		double minTimeUntilAngleChange = 0.1d * r.nextDouble() + 0.01;
		double timeUntilAngleChangeRange = 2.5d * r.nextDouble() + minTimeUntilAngleChange;

		int red = r.nextInt(256);
		int green = r.nextInt(256);
		int blue = r.nextInt(256);
		
		double maxVelocity = RANDOM_MAX_VELOCITY * r.nextDouble() + 1;
		
		double childEnergyDonation = r.nextDouble()*50;
		double minEnergyToReproduce = r.nextDouble()*50+childEnergyDonation;
		
		Creature retval = new Creature(environment, x, y, width, height, minAccel, accelRange,
				minAngular, angularRange, minTimeUntilAccelChange,
				ticksUntilAccelChangeRange, minTimeUntilAngleChange,
				timeUntilAngleChangeRange, red, green, blue, maxVelocity,
				childEnergyDonation, minEnergyToReproduce);
		retval.energy = retval.getMaxEnergy();
		
		return retval;
	}
	
	private static boolean within(int min, int value, int max) {
		return min <= value && value <= max;
	}

	double acceleration;

	double accelRange;

	private double age;

	double angle;

	double angularRange;

	double angularVelocity;
	private double childEnergyDonation;

	Color color;
	private DNA dna;
	double height;
	double maxEnergy;

	double maxVelocity;
	double minAccel;

	double minAngular;
	private double minEnergyToReproduce;

	double minTimeUntilNextAccelerationChange;
	double minTimeUntilNextAngleChange;
	double timeUntilNextAccelerationChange;
	double timeUntilNextAccelerationRange;

	double timeUntilNextAngleChange;
	double timeUntilNextAngleRange;

	double velocity;

	private boolean viable = true;

	double width;

	Environment environment;

	double x;

	double y;

	public Creature(Environment environment, DNA dna) {
		this(environment, r.nextInt(environment.getWidth()), r.nextInt(environment.getHeight()),
				dna.getDouble(Keys.width),
				dna.getDouble(Keys.height),
				dna.getDouble(Keys.minAccel),
				dna.getDouble(Keys.accelRange),
				dna.getDouble(Keys.minAngular),
				dna.getDouble(Keys.angularRange),
				dna.getDouble(Keys.minTimeUntilAccelChange),
				dna.getDouble(Keys.timeUntilAccelChangeRange),
				dna.getDouble(Keys.minTimeUntilAngleChange),
				dna.getDouble(Keys.timeUntilAngleChangeRange),
				dna.getInt(Keys.red),
				dna.getInt(Keys.blue),
				dna.getInt(Keys.green),
				dna.getDouble(Keys.maxVelocity),
				dna.getDouble(Keys.childEnergyDonation),
				dna.getDouble(Keys.minEnergyToReproduce));
	}
	public Creature(Environment environment, double x, double y, double width,
			double height, double minAccel, double accelRange,
			double minAngular, double angularRange,
			double minTimeUntilAccelChange, double timeUntilAccelChangeRange,
			double minTimeUntilAngleChange, double timeUntilAngleChangeRange,
			int red, int blue, int green, double maxVelocity, double childEnergyDonation, double minEnergyToReproduce) {
		this.environment = environment;
		this.x = x;
		this.y = y;
		this.angle = 2*Math.PI*r.nextDouble();

		if (within(0, red, 255) && within(0, blue, 255)
				&& within(0, green, 255)) {
			color = new Color(red, green, blue);
		} else {
			viable = false;
			//System.out.println(String.format("Color out of bounds: RGB = (%d, %d, %d)", red, green, blue));
			return;
		}

		if (width > 0 && height > 0 && width < environment.getWidth() && height < environment.getHeight()) {
			this.width = width;
			this.height = height;
		} else {
			viable = false;
			//System.out.println(String.format("dimensions out of bounds: [%.2f x %.2f]", width, height));
			return;
		}

		if (accelRange > 0 && minAccel >= 0) {
			this.accelRange = accelRange;
			this.minAccel = minAccel;
		} else {
			viable = false;
			//System.out.println(String.format("bad acceleration: %.2f x rand() + %.2f", accelRange, minAccel));
			return;
		}

		if (angularRange > 0 && minAngular >= 0) {
			this.angularRange = angularRange;
			this.minAngular = minAngular;
		} else {
			viable = false;
			//System.out.println(String.format("bad angular: %.2f x rand() + %.2f", angularRange, minAngular));
			return;
		}

		if (minTimeUntilAccelChange > 0 && timeUntilAccelChangeRange > 0) {
			this.minTimeUntilNextAccelerationChange = minTimeUntilAccelChange;
			this.timeUntilNextAccelerationRange = timeUntilAccelChangeRange;
		} else {
			viable = false;
			//System.out.println(String.format("bad accel ticks: %d x rand() + %d", ticksUntilAccelChangeRange, minTicksUntilAccelChange));
			return;
		}

		if (minTimeUntilAngleChange > 0 && timeUntilAngleChangeRange > 0) {
			this.minTimeUntilNextAngleChange = minTimeUntilAngleChange;
			this.timeUntilNextAngleRange = timeUntilAngleChangeRange;
		} else {
			viable = false;
			//System.out.println(String.format("bad angle ticks: %d x rand() + %d", ticksUntilAngleChangeRange, minTicksUntilAngleChange));
			return;
		}
		
		if (maxVelocity > 0 && maxVelocity < Math.min(environment.getWidth(), environment.getHeight())) {
			this.maxVelocity = maxVelocity;
			this.velocity = maxVelocity * r.nextDouble();
		}
		else {
			viable = false;
			//System.out.println(String.format("bad max velocity: %.2f", maxVelocity));
			return;
		}
		
		if (minEnergyToReproduce > 0 && childEnergyDonation > 0 && childEnergyDonation < minEnergyToReproduce) {
			this.minEnergyToReproduce = minEnergyToReproduce;
			this.childEnergyDonation = childEnergyDonation;
		}
		else {
			viable = false;
			//System.out.println(String.format("bad minEnergyToReproduce/childEnergyDonation: %.2f/%.2f", minEnergyToReproduce, childEnergyDonation));
			return;
		}

		maxEnergy = getMaxEnergy();
	}

	private void clampVelocity() {
		if (velocity > maxVelocity) {
			velocity = maxVelocity;
		}
		else if (velocity < -maxVelocity) {
			velocity = -maxVelocity;
		}
	}

	@Override
	public int compareTo(Creature other) {
		return (int) (getEnergy() / getMaxEnergy() - other.getEnergy()
				/ other.getMaxEnergy());
	}

	public double getAcceleration() {
		return acceleration;
	}

	private double getAge() {
		return age;
	}

	public double getAngle() {
		return angle;
	}

	public double getAngularVelocity() {
		return angularVelocity;
	}

	public int getBlue() {
		return color.getBlue();
	}

	public Color getColor() {
		return color;
	}

	public double getChildEnergyDonation() {
		return childEnergyDonation;
	}

	private double getCostOfLiving() {
		return environment.getCostOfLivingExpression().evaluate(this);
	}

	public int getRed() {
		return color.getRed();
	}

	public DNA getDNA() {
		if (dna == null) {
			dna = new DNA();

			dna.add(Keys.red, color.getRed());
			dna.add(Keys.blue, color.getBlue());
			dna.add(Keys.green, color.getGreen());
			dna.add(Keys.height, height);
			dna.add(Keys.accelRange, accelRange);
			dna.addRadian(Keys.angularRange, angularRange);
			dna.add(Keys.timeUntilAccelChangeRange,	timeUntilNextAccelerationRange);
			dna.add(Keys.timeUntilAngleChangeRange, timeUntilNextAngleRange);
			dna.add(Keys.minAccel, minAccel);
			dna.addRadian(Keys.minAngular, minAngular);
			dna.add(Keys.minTimeUntilAccelChange, minTimeUntilNextAccelerationChange);
			dna.add(Keys.minTimeUntilAngleChange, minTimeUntilNextAngleChange);
			dna.add(Keys.width, width);
			dna.add(Keys.maxVelocity, maxVelocity);
			dna.add(Keys.minEnergyToReproduce, minEnergyToReproduce);
			dna.add(Keys.childEnergyDonation, childEnergyDonation);
		}

		return dna;
	}

	public int getGreen() {
		return color.getGreen();
	}

	private double getMass() {
		return width * height;
	}

	public double getMaxEnergy() {
		return getMass();
	}

	public double getReproductionThreshold() {
		return minEnergyToReproduce;
	}

	public double getStrength() {
		return environment.getStrengthExpression().evaluate(this);
	}

	public double getVelocity() {
		return velocity;
	}

	public double getMaxVelocity() {
		return maxVelocity;
	}

	public int getNumChildren() {
		return numChildren;
	}

	public Shape getShape() {
		Shape shape = new Rectangle2D.Double(-height / 2, -width / 2, height, width);

		AffineTransform transform = new AffineTransform();

		transform.translate(x, y);
		transform.rotate(angle);

		return transform.createTransformedShape(shape);
	}

	public double getWidth() {
		return width;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getHeight() {
		return height;
	}

	public void interactWith(Creature other, double timePerFrame) {
		if (getShape().intersects(other.getShape().getBounds())) {
			takeEnergyFrom(other, getStrength() * timePerFrame);
		}
	}

	public boolean isViable() {
		return viable;
	}
	
	public void tick(double timePerFrame) {
		timeUntilNextAccelerationChange -= timePerFrame;
		timeUntilNextAngleChange -= timePerFrame;

		if (timeUntilNextAccelerationChange <= 0) {
			acceleration = accelRange * r.nextDouble() + minAccel;
			if (r.nextBoolean()) {
				acceleration *= -1;
			}
			timeUntilNextAccelerationChange = timeUntilNextAccelerationRange * r.nextDouble() + minTimeUntilNextAccelerationChange;
		}
		if (timeUntilNextAngleChange <= 0) {
			angularVelocity = angularRange * r.nextDouble() + minAngular;
			if (r.nextBoolean()) {
				angularVelocity *= -1;
			}
			timeUntilNextAngleChange = timeUntilNextAngleRange * r.nextDouble()	+ minTimeUntilNextAngleChange;
		}

		angle += angularVelocity * timePerFrame;
		velocity += (acceleration - 0.1 * velocity) * timePerFrame;

		// cost of living
		energy -= timePerFrame * getCostOfLiving();

		clampVelocity();

		double xDelta = velocity * Math.cos(angle) * timePerFrame;
		double yDelta = velocity * Math.sin(angle) * timePerFrame;

		x += xDelta;
		y += yDelta;

		wrap();

		age += timePerFrame;
	}
	
	private void wrap() {
		double worldWidth = environment.getWidth();
		double worldHeight = environment.getHeight();

		while (x < 0) {
			x += worldWidth;
		}
		while (x >= worldWidth) {
			x -= worldWidth;
		}

		while (y < 0) {
			y += worldHeight;
		}
		while (y >= worldHeight) {
			y -= worldHeight;
		}
	}

	public double getEnergy() {
		return energy;
	}

	public void takeEnergyFrom(Creature other, double amount) {
		energy += amount;
		other.energy -= amount;
	}

	public void clipEnergy() {
		if (energy > getMaxEnergy()) {
			energy = getMaxEnergy();
		}
	}
	public final boolean isDead() {
		return energy <= 0;
	}
}

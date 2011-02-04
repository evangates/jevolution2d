package jevolution;

import jevolution.ui.EnvironmentPanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Creature extends Thing implements Comparable<Creature> {
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
		public final static String minTicksUntilAccelChange = "minTicksUntilAccelChange";
		public final static String minTicksUntilAngleChange = "minTicksUntilAngleChange";
		public final static String red = "red";
		public final static String ticksUntilAccelChangeRange = "ticksUntilAccelChangeRange";
		public final static String ticksUntilAngleChangeRange = "ticksUntilAngleChangeRange";
		public final static String width = "width";
	}
	
	final static double ACCEL_RANGE = 5;
	final static double ANGULAR_RANGE = Math.PI / 20;
	final static int MAX_DIMENSION = 60;
	final static double MAX_VELOCITY = 200;

	final static int MIN_DIMENSION = 4;
	
	private int numChildren;

	static Random r = new Random();

	private static double colorDistance(Color a, Color b) {
		int dred = a.getRed() - b.getRed();
		int dblue = a.getBlue() - b.getBlue();
		int dgreen = a.getGreen() - b.getGreen();
		
		return Math.sqrt(dred*dred + dblue*dblue + dgreen*dgreen);
	}
	
	public static Creature mate(Creature one, Creature two) {
		if (one.getEnergy() >= one.getReproductionThreshold() && two.getEnergy() > two.getReproductionThreshold()) {
			DNA left = one.getDNA();
			DNA right = two.getDNA();
	
			DNA dna = DNA.splice(left, right);
			dna.mutate();
			
			Creature child = new Creature(one.world, dna);
			
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

	public static Creature random(EnvironmentPanel world) {
		int x = r.nextInt(world.getWidth());
		int y = r.nextInt(world.getHeight());

		int width = r.nextInt(MAX_DIMENSION - MIN_DIMENSION) + MIN_DIMENSION;
		int height = r.nextInt(MAX_DIMENSION - MIN_DIMENSION) + MIN_DIMENSION;

		double minAccel = 0;
		double accelRange = r.nextDouble() * ACCEL_RANGE;

		double minAngular = 0;
		double angularRange = r.nextDouble() * Math.PI / 120;

		int minTicksUntilAccelChange = r.nextInt(100) + 10;
		int ticksUntilAccelChangeRange = r.nextInt(500)
				+ minTicksUntilAccelChange;

		int minTicksUntilAngleChange = r.nextInt(100) + 10;
		int ticksUntilAngleChangeRange = r.nextInt(500)
				+ minTicksUntilAngleChange;

		int red = r.nextInt(256);
		int green = r.nextInt(256);
		int blue = r.nextInt(256);
		
		double maxVelocity = EnvironmentPanel.maxVelocity * r.nextDouble() + 1;
		
		double childEnergyDonation = r.nextDouble()*50;
		double minEnergyToReproduce = r.nextDouble()*50+childEnergyDonation;
		
		Creature retval = new Creature(world, x, y, width, height, minAccel, accelRange,
				minAngular, angularRange, minTicksUntilAccelChange,
				ticksUntilAccelChangeRange, minTicksUntilAngleChange,
				ticksUntilAngleChangeRange, red, green, blue, maxVelocity,
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

	private int ageInTicks;

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

	int minTicksUntilNextAccelerationChange;

	int minTicksUntilNextAngleChange;
	int ticksUntilNextAccelerationChange;
	int ticksUntilNextAccelerationRange;

	int ticksUntilNextAngleChange;

	int ticksUntilNextAngleRange;

	double velocity;

	private boolean viable = true;

	double width;

	EnvironmentPanel world;

	double x;

	double y;

	public Creature(EnvironmentPanel world, DNA dna) {
		this(world, r.nextInt(world.getWidth()), r.nextInt(world.getHeight()),
				dna.getDouble(Keys.width), dna.getDouble(Keys.height), dna
						.getDouble(Keys.minAccel), dna
						.getDouble(Keys.accelRange), dna
						.getDouble(Keys.minAngular), dna
						.getDouble(Keys.angularRange), dna
						.getInt(Keys.minTicksUntilAccelChange), dna
						.getInt(Keys.ticksUntilAccelChangeRange), dna
						.getInt(Keys.minTicksUntilAngleChange), dna
						.getInt(Keys.ticksUntilAngleChangeRange), dna
						.getInt(Keys.red), dna.getInt(Keys.blue), dna
						.getInt(Keys.green), dna.getDouble(Keys.maxVelocity),
						dna.getDouble(Keys.childEnergyDonation), dna.getDouble(Keys.minEnergyToReproduce));
	}
	public Creature(EnvironmentPanel world, double x, double y, double width,
			double height, double minAccel, double accelRange,
			double minAngular, double angularRange,
			int minTicksUntilAccelChange, int ticksUntilAccelChangeRange,
			int minTicksUntilAngleChange, int ticksUntilAngleChangeRange,
			int red, int blue, int green, double maxVelocity, double childEnergyDonation, double minEnergyToReproduce) {
		this.world = world;
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

		if (width > 0 && height > 0 && width < world.getWidth() && height < world.getHeight()) {
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

		if (minTicksUntilAccelChange > 0 && ticksUntilAccelChangeRange > 0) {
			this.minTicksUntilNextAccelerationChange = minTicksUntilAccelChange;
			this.ticksUntilNextAccelerationRange = ticksUntilAccelChangeRange;
		} else {
			viable = false;
			//System.out.println(String.format("bad accel ticks: %d x rand() + %d", ticksUntilAccelChangeRange, minTicksUntilAccelChange));
			return;
		}

		if (minTicksUntilAngleChange > 0 && ticksUntilAngleChangeRange > 0) {
			this.minTicksUntilNextAngleChange = minTicksUntilAngleChange;
			this.ticksUntilNextAngleRange = ticksUntilAngleChangeRange;
		} else {
			viable = false;
			//System.out.println(String.format("bad angle ticks: %d x rand() + %d", ticksUntilAngleChangeRange, minTicksUntilAngleChange));
			return;
		}
		
		if (maxVelocity > 0 && maxVelocity < Math.min(world.getWidth(), world.getHeight())) {
			this.maxVelocity = maxVelocity;
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

		ageInTicks = 0;
	}

	private void clampVelocity() {
		if (velocity > maxVelocity) {
			velocity = maxVelocity;
		} else if (velocity < 0) {
			velocity = 0;
		}
	}

	@Override
	public int compareTo(Creature other) {
		return (int) (getEnergy() / getMaxEnergy() - other.getEnergy()
				/ other.getMaxEnergy());
	}

	public void draw(Graphics2D g) {
		g.setColor(color);

		Shape shape = getShape();

		g.fill(shape);

		g.setColor(Color.WHITE);
		double mag = headingLineMagnitude();
		g.drawLine((int) x, (int) y,
				(int) Math.round(x + mag * Math.cos(angle)),
				(int) Math.round(y + mag * Math.sin(angle)));

		String energyStr = String.format("%.0f / %.0f", energy, getMaxEnergy());
		if (getEnergy() > getReproductionThreshold()) {
			energyStr = "+ " + energyStr;
		}
		g.drawString(energyStr, (int) x, (int) y);

		String ageStr = String.format("%.0f", getAge());
		g.drawString(ageStr, (int)x, (int)y + 10);
		
		if (numChildren > 0) {
			g.drawString("" + numChildren, (int)x, (int)y + 20);
		}
	}

	public double getAcceleration() {
		return acceleration;
	}

	private double getAge() {
		return age;
	}

	public int getAgeInTicks() {
		return ageInTicks;
	}

	public int getBlue() {
		return color.getBlue();
	}

	public double getChildEnergyDonation() {
		return childEnergyDonation;
	}

	private double getCostOfLiving() {
		return world.getCostOfLivingExpression().evaluate(this);
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
			dna.add(Keys.ticksUntilAccelChangeRange,
					ticksUntilNextAccelerationRange);
			dna.add(Keys.ticksUntilAngleChangeRange, ticksUntilNextAngleRange);
			dna.add(Keys.minAccel, minAccel);
			dna.addRadian(Keys.minAngular, minAngular);
			dna.add(Keys.minTicksUntilAccelChange,
					minTicksUntilNextAccelerationChange);
			dna.add(Keys.minTicksUntilAngleChange, minTicksUntilNextAngleChange);
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

	private double getPerimeter() {
		return 2 * (width + height);
	}
	
	public double getReproductionThreshold() {
		return minEnergyToReproduce;
	}

	public Shape getShape() {
		Shape shape = new Rectangle2D.Double(-height / 2, -width / 2, height,
				width);

		AffineTransform transform = new AffineTransform();

		transform.translate(x, y);
		transform.rotate(angle);

		return transform.createTransformedShape(shape);
	}

	private double getStrength() {
//		return getAge() + color.getRGB()/0xfff;
		//return getPerimeter() * Math.sqrt(velocity) * Math.abs(angularVelocity) * color.getRGB()/0xffff;
//		return -color.getRed() - color.getGreen() + color.getBlue()+ 3*(height - width);
//		return getPerimeter() + velocity/2 - color.getGreen();
//		return getPerimeter()/getMass() + velocity;
		return world.getStrengthExpression().evaluate(this);
	}

	public double getVelocity() {
		return velocity;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	private double headingLineMagnitude() {
		return (velocity / maxVelocity) * height + height;
	}

	public void interactWith(Thing other, double timePerFrame) {
		takeEnergyFrom(other, getStrength() * timePerFrame);
	}

	public boolean isViable() {
		return viable;
	}
	
	public void tick(double timePerFrame) {
		boolean moving = true;
		boolean none = false;

		if (moving) {
			--ticksUntilNextAccelerationChange;
			--ticksUntilNextAngleChange;

			if (ticksUntilNextAccelerationChange <= 0 || velocity <= 0) {
				acceleration = accelRange * r.nextDouble() + minAccel;
				if (r.nextBoolean()) {
					acceleration *= -1;
				}
				ticksUntilNextAccelerationChange = r
						.nextInt(ticksUntilNextAccelerationRange)
						+ minTicksUntilNextAccelerationChange;
			}
			if (ticksUntilNextAngleChange <= 0) {
				angularVelocity = angularRange * r.nextDouble() + minAngular;
				if (r.nextBoolean()) {
					angularVelocity *= -1;
				}
				ticksUntilNextAngleChange = r.nextInt(ticksUntilNextAngleRange)
						+ minTicksUntilNextAngleChange;
			}

			angle += angularVelocity;
			velocity += acceleration;

			clampVelocity();

			double xDelta = velocity * Math.cos(angle) * timePerFrame;
			double yDelta = velocity * Math.sin(angle) * timePerFrame;

			x += xDelta;
			y += yDelta;


			// cost of living
			energy -= timePerFrame * getCostOfLiving();

			// energy expenditure from spinning
//			energy -= getPerimeter() * 0.0001 * timePerFrame * angularVelocity;

			wrap();
		} else if (!none) {
			double rotationPerSecond = Math.PI / 2;
			double xPerSecond = 10;
			double yPerSecond = 10;

			double rPerFrame = rotationPerSecond * timePerFrame;
			double xPerFrame = xPerSecond * timePerFrame;
			double yPerFrame = yPerSecond * timePerFrame;

			x += xPerFrame;
			y += yPerFrame;

			angle += rPerFrame;
		}

		++ageInTicks;
		age += timePerFrame;
	}
	
	private void wrap() {
		double worldWidth = world.getWidth();
		double worldHeight = world.getHeight();

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
}

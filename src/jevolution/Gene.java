package jevolution;


public class Gene implements Comparable<Gene> {
	String name;
	long value;
	ValueType type;
	
	public enum ValueType {INT, LONG, DOUBLE, RADIANS };
	
	public Gene(String name, double value, boolean isRadian) {
		this(name, Double.doubleToLongBits(value), isRadian ? ValueType.RADIANS : ValueType.DOUBLE);
	}
	
	public Gene(String name, double value) {
		this(name, Double.doubleToLongBits(value), ValueType.DOUBLE);
	}
	
	public Gene(String name, long value) {
		this(name, value, ValueType.LONG);
	}
	
	private Gene(String name, long value, ValueType type) {
		this.name = name;
		this.value = value;
		this.type = type;
	}
	
	public Gene(String name, int value) {
		this(name, value, ValueType.INT);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return name.equals(obj);
	}

	@Override
	public int compareTo(Gene other) {
		return name.compareTo(other.name);
	}
}
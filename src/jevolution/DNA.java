package jevolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class DNA {
	private List<Gene> genes;
	
	public DNA() {
		genes = new ArrayList<Gene>();
	}
	
	public void add(Gene gene) {
		genes.add(gene);
	}
	
	public void add(String name, long value) {
		genes.add(new Gene(name, value));
	}
	
	public void add(String name, double value) {
		genes.add(new Gene(name, value));
	}
	
	public void addRadian(String name, double value) {
		genes.add(new Gene(name, value, true));
	}
	
	public void add(String name, int value) {
		genes.add(new Gene(name, value));
	}
	
	public Gene getGene(String name) {
		for(Gene g: genes) {
			if (g.name.equals(name)) {
				return g;
			}
		}
		
		return null;
	}
	
	public double getRadian(String name) {
		return getDouble(name);
	}
	
	public double getDouble(String name) {
		Gene gene = getGene(name);
		
		return Double.longBitsToDouble(gene.value);
	}
	
	public int getInt(String name) {
		Gene gene = getGene(name);
		
		return (int)gene.value;
	}
	
	public int getNumGenes() {
		return genes.size();
	}
	
	public static DNA splice(DNA one, DNA two) {
		if (one.getNumGenes() != two.getNumGenes()) {
			throw new IllegalArgumentException("DNA must be same size to splice.");
		}
		
		Collections.sort(one.genes);
		Collections.sort(two.genes);
		
		int spliceIndex = new Random().nextInt(one.getNumGenes() - 1) + 1;
		
		DNA retval = new DNA();
		for(int i = 0; i < spliceIndex; ++i) {
			retval.add(one.genes.get(i));
		}
		for(int i = spliceIndex; i < two.getNumGenes(); ++i) {
			retval.add(two.genes.get(i));
		}
		
		return retval;
	}
	
	public void mutate() {
		Random r = new Random();
		
		int mutateIndex = r.nextInt(getNumGenes());
		boolean increase = r.nextBoolean();
		double maxChange = 10;
		double minChange = 5;
		double modifier = (maxChange - minChange)*r.nextDouble() + minChange;
		double multiplier = (maxChange - minChange)*r.nextDouble() + minChange;
		if (!increase) {
			modifier *= -1;
			multiplier = 1 - multiplier / 100;
		}
		else {
			multiplier = 1 + multiplier / 100;
		}
		
		Gene toMutate = genes.get(mutateIndex);
		
		switch (toMutate.type) {
		case RADIANS:
			double rvalue = Double.longBitsToDouble(toMutate.value);
			
			// turn modifier into a % of full turn
			modifier /= 200 * Math.PI;
			
			rvalue = multiplier*rvalue + modifier;
			
			toMutate.value = Double.doubleToLongBits(rvalue);
			break;
		case DOUBLE:
			double dvalue = Double.longBitsToDouble(toMutate.value);
			
			dvalue = multiplier*dvalue + modifier;
			
			toMutate.value = Double.doubleToLongBits(dvalue);
			break;
		case INT:
			int ivalue = (int)(0xFFFFFFFF & toMutate.value);
			
			ivalue = (int)Math.round(multiplier*ivalue + modifier);
			
			toMutate.value = ivalue;
			break;
		default:
			throw new IllegalStateException(String.format("Unknown gene value type: %s", toMutate.type.toString()));
		}
		
	}
}
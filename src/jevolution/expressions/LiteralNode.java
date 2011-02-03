/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jevolution.expressions;

import jevolution.Creature;

/**
 *
 * @author kuhlmancer
 */
public class LiteralNode extends ValueNode {
	double value;

	public LiteralNode(double value) {
		this.value = value;
	}

	@Override
	public double evaluate(Creature c) {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

}

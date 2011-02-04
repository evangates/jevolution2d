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
public class ExponentiationNode extends CreatureExpressionNode {
	CreatureExpressionNode lhs, rhs;

	public ExponentiationNode(CreatureExpressionNode lhs, CreatureExpressionNode rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public double evaluate(Creature c) {
		return Math.pow(lhs.evaluate(c), rhs.evaluate(c));
	}

	@Override
	public String toString() {
		return String.format("%s^%s", lhs.toString(), rhs.toString());
	}
}

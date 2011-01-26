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
public class AdditionNode extends CreatureExpressionNode {
	private CreatureExpressionNode lhs, rhs;

	public AdditionNode(CreatureExpressionNode lhs, CreatureExpressionNode rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public double evaluate(Creature c) {
		return lhs.evaluate(c) + rhs.evaluate(c);
	}

	@Override
	public String toString() {
		return String.format("%s + %s", lhs.toString(), rhs.toString());
	}

}

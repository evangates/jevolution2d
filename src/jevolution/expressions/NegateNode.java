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
public class NegateNode extends CreatureExpressionNode {
	CreatureExpressionNode expr;

	public NegateNode(CreatureExpressionNode expr) {
		this.expr = expr;
	}

	@Override
	public double evaluate(Creature c) {
		return -expr.evaluate(c);
	}

	@Override
	public boolean isValid() {
		return expr.isValid();
	}

	@Override
	public String toString() {
		return String.format("-%s", expr.toString());
	}

}

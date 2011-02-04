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
public class CreatureExpression {
	private CreatureExpressionNode node;

	public CreatureExpression(String expression) {
		this.node = CreatureExpressionParser.parse(expression);
	}

	public double evaluate(Creature c) {
		if (isValid()) {
			return node.evaluate(c);
		}
		else return Double.NaN;
	}

	public boolean isValid() {
		return node != null && node.isValid();
	}

	@Override
	public String toString() {
		if (isValid()) {
			return node.toString();
		}
		else return "";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj instanceof CreatureExpression == false) {
			return false;
		}

		CreatureExpression other = (CreatureExpression)obj;

		return toString().equals(other.toString());
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}
}

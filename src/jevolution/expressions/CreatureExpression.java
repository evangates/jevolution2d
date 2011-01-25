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
		return node.evaluate(c);
	}
}

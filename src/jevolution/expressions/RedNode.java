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
public class RedNode extends CreatureExpressionNode {

	@Override
	public double evaluate(Creature c) {
		return c.getRed();
	}

	@Override
	public String toString() {
		return "red";
	}

}

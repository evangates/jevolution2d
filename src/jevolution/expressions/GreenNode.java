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
public class GreenNode extends CreatureExpressionNode {

	@Override
	public double evaluate(Creature c) {
		return c.getGreen();
	}

	@Override
	public String toString() {
		return "green";
	}

}

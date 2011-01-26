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
public class BlueNode extends CreatureExpressionNode {

	@Override
	public double evaluate(Creature c) {
		return c.getBlue();
	}

	@Override
	public String toString() {
		return "blue";
	}

}

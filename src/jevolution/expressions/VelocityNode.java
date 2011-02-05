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
public class VelocityNode extends ValueNode {

	@Override
	public double evaluate(Creature c) {
		return Math.abs(c.getVelocity());
	}

	@Override
	public String toString() {
		return "velocity";
	}

}

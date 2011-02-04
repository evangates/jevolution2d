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
public class AngularVelocityMagnitudeNode extends ValueNode {
	@Override
	public double evaluate(Creature c) {
		return Math.abs(c.getAngularVelocity());
	}

	@Override
	public String toString() {
		return "angularVelocity";
	}

}

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
public class PiNode extends ValueNode {

	@Override
	public double evaluate(Creature c) {
		return Math.PI;
	}

	@Override
	public String toString() {
		return "pi";
	}

}

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
public class HeightNode extends ValueNode {

	@Override
	public double evaluate(Creature c) {
		return c.getHeight();
	}

	@Override
	public String toString() {
		return "height";
	}
}

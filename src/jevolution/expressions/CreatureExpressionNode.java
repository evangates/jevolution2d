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
public abstract class CreatureExpressionNode {
	public abstract double evaluate(Creature c);

	@Override
	public abstract String toString();

	public boolean isValid() {
		return true;
	}
}

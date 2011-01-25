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
public class InvalidTokenNode extends CreatureExpressionNode {
	private String token;

	public InvalidTokenNode(String token) {
		this.token = token;
	}

	@Override
	public double evaluate(Creature c) {
		return Double.NaN;
	}

	@Override
	public String toString() {
		return String.format("invalid:%s", token);
	}

}

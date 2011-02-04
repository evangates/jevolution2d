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
public class ErrorNode extends CreatureExpressionNode {
	private String text;
	
	public ErrorNode() {
		this("");
	}
	
	public ErrorNode(String text) {
		this.text = text;
	}

	@Override
	public double evaluate(Creature c) {
		return Double.NaN;
	}

	@Override
	public String toString() {
		if (StringUtils.isBlank(text)) {
			return String.format("error");
		}
		else return String.format("error<%s>", text);
	}

	@Override
	public boolean isValid() {
		return false;
	}
}

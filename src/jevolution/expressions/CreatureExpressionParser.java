/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jevolution.expressions;

import java.io.StringReader;
import java_cup.runtime.Symbol;

/**
 *
 * @author kuhlmancer
 */
public class CreatureExpressionParser {
	public static CreatureExpressionNode parse(String expression) {
		Parser parser = new Parser(new Scanner(new StringReader(expression)));

		Symbol result;
		try {
			result = parser.parse();
			return (CreatureExpressionNode)result.value;
		} catch (Exception ex) {
			System.out.println(String.format("parse error: %s", expression));
			return null;
		}
	}
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jevolution.expressions;

import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 *
 * @author kuhlmancer
 */
public class CreatureExpressionTokenizer {
	public static Queue<Token> tokenize(String expression) {
		StringTokenizer tokenizer = new StringTokenizer(expression, " *\t\n\r", true);
		LinkedList<Token> tokens = new LinkedList<Token>();

		while(tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();

			// skip whitespace
			if (StringUtils.isBlank(token)) {
				continue;
			}

			if (token.equals("width")) {
				tokens.addLast(Token.WIDTH);
			}
			else if (token.equals("height")) {
				tokens.addLast(Token.HEIGHT);
			}
			else if (token.equals("*")) {
				tokens.addLast(Token.MULTIPLY);
			}
			else tokens.addLast(Token.INVALID);
		}

		return tokens;
	}
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jevolution.expressions;

import com.sun.java.swing.plaf.windows.resources.windows_de;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 *
 * @author kuhlmancer
 */
public class CreatureExpressionParser {
	public final static ArrayList<String> terminals;

	static {
		 terminals = new ArrayList<String>();
		 terminals.add("width");
		 terminals.add("height");
		 terminals.add("*");
	}

	public static CreatureExpressionNode parse(String expression) {
		StringTokenizer tokenizer = new StringTokenizer(expression, " *\t\n\r", true);

		Stack<CreatureExpressionNode> nodes = new Stack<CreatureExpressionNode>();

		while(tokenizer.hasMoreTokens()) {
			String token;
			do {
				token = tokenizer.nextToken();
			} while(StringUtils.isBlank(token) && tokenizer.hasMoreTokens());

			if (StringUtils.isBlank(token) && !tokenizer.hasMoreTokens()) {
				return nodes.pop();
			}

			if (!terminals.contains(token)) {
				return new InvalidTokenNode(token);
			}
			else if (token.equals("width")) {
				nodes.push(new WidthNode());
			}
			else if (token.equals("height")) {
				nodes.push(new HeightNode());
			}
			else if (token.equals("*")) {
				if (!tokenizer.hasMoreTokens()) {
					return new InvalidTokenNode(token);
				}

				CreatureExpressionNode lhs = nodes.pop();
				String nextToken = tokenizer.nextToken();
				CreatureExpressionNode rhs;

				if (nextToken.equals("width")) {
					rhs = new WidthNode();
				}
				else if (nextToken.equals("height")) {
					rhs = new HeightNode();
				}
				else return new InvalidTokenNode(token);

				nodes.push(new MultiplicationNode(lhs, rhs));
			}
		}
		
		return nodes.pop();
	}

	public static String[] getTokens(String expression) {
		StringTokenizer tokenizer = new StringTokenizer(expression);
		String[] tokens = new String[tokenizer.countTokens()];

		for(int i = 0; i < tokenizer.countTokens(); ++i) {
			tokens[i] = tokenizer.nextToken();
		}

		return tokens;
	}
}

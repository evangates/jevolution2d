/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jevolution.expressions;

import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author kuhlmancer
 */
public class CreatureExpressionParser {
	public static CreatureExpressionNode parse(String expression) {
		Queue<Token> tokens = CreatureExpressionTokenizer.tokenize(expression);

		Stack<CreatureExpressionNode> nodes = new Stack<CreatureExpressionNode>();

		while(!tokens.isEmpty()) {
			Token token = tokens.poll();
			Token rhsToken;
			CreatureExpressionNode lhs, rhs;

			switch (token) {
				case HEIGHT:
					nodes.push(new HeightNode());
					break;
				case WIDTH:
					nodes.push(new WidthNode());
					break;
				case RED:
					nodes.push(new RedNode());
					break;
				case GREEN:
					nodes.push(new GreenNode());
					break;
				case BLUE:
					nodes.push(new BlueNode());
					break;
				case MULTIPLY:
					// need another token
					if (tokens.isEmpty()) {
						return new InvalidTokenNode(token.toString());
					}

					rhsToken = tokens.poll();

					switch(rhsToken) {
						case HEIGHT:
							lhs = nodes.pop();
							rhs = new HeightNode();
							nodes.push(new MultiplicationNode(lhs, rhs));
							break;
						case WIDTH:
							lhs = nodes.pop();
							rhs = new WidthNode();
							nodes.push(new MultiplicationNode(lhs, rhs));
							break;
						default:
							return new InvalidTokenNode(rhsToken.toString());
					}
					break;
					// end MULTIPLY case
				case ADD:
					// need another token
					if (tokens.isEmpty()) {
						return new InvalidTokenNode(token.toString());
					}

					rhsToken = tokens.poll();

					switch(rhsToken) {
						case HEIGHT:
							lhs = nodes.pop();
							rhs = new HeightNode();
							nodes.push(new AdditionNode(lhs, rhs));
							break;
						case WIDTH:
							lhs = nodes.pop();
							rhs = new WidthNode();
							nodes.push(new AdditionNode(lhs, rhs));
							break;
						default:
							return new InvalidTokenNode(rhsToken.toString());
					}
					break;
					// end ADD case
				default:
					return new InvalidTokenNode(token.toString());
			}
		}

		return nodes.pop();
	}
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jevolution.expressions.tests;

import jevolution.expressions.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kuhlmancer
 */
public class BinaryExpressionTests {
	@Test
	public void multiplicationExpressionTest() {
		CreatureExpressionNode result = CreatureExpressionParser.parse("width*height");

		assertTrue("Expression wasn't a multiplication node.", result instanceof MultiplicationNode);
	}

	@Test
	public void additionExpressionTest() {
		CreatureExpressionNode result = CreatureExpressionParser.parse("width + width");

		assertTrue("Expression wasn't an addition node.", result instanceof AdditionNode);
	}

	@Test
	public void ignoreWhitespaceBetweenTokensTest() {
		CreatureExpressionNode node = CreatureExpressionParser.parse("width   *    height");

		assertTrue("Parser didn't ignore whitespace properly.", node instanceof MultiplicationNode);
	}

	@Test
	public void twoAdditionOpsTest() {
		CreatureExpressionNode result = CreatureExpressionParser.parse("red + red + red");

		assertTrue("Expression wasn't an addition node.", result instanceof AdditionNode);
	}

	@Test
	public void twoMultOpsTest() {
		CreatureExpressionNode result = CreatureExpressionParser.parse("red * red * red");

		assertTrue("Expression wasn't an addition node.", result instanceof MultiplicationNode);
	}
}

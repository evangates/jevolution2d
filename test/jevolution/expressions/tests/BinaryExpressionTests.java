/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jevolution.expressions.tests;

import org.junit.Test;

/**
 *
 * @author kuhlmancer
 */
public class BinaryExpressionTests extends ExpressionTestBase {
	@Test
	public void multiplicationExpressionTest() {
		testExpression("width*height", creature.getWidth() * creature.getHeight());
	}

	@Test
	public void additionExpressionTest() {
		testExpression("width + width", creature.getWidth() + creature.getWidth());
	}

	@Test
	public void ignoreWhitespaceBetweenTokensTest() {
		testExpression("width   \t  *  \t height", creature.getWidth() * creature.getHeight());
	}

	@Test
	public void twoAdditionOpsTest() {
		testExpression("red + red + red", creature.getRed() + creature.getRed() + creature.getRed());
	}

	@Test
	public void twoMultOpsTest() {
		testExpression("red * red  * red", creature.getRed() * creature.getRed() * creature.getRed());
	}

	@Test
	public void literalAdditionTest() {
		testExpression("1 + 1", 2);
	}

	@Test
	public void literalMultiplicationTest() {
		testExpression("2 * 3", 6);
	}

	@Test
	public void positiveLiteralAndPropertyTest() {
		testExpression("2.5 * red", 2.5 * creature.getRed());
	}

	@Test
	public void propertyAndPositiveLiteralTest() {
		testExpression("red * 2.5", creature.getRed() * 2.5);
	}

	@Test
	public void negativeLiteralAndPropertyTest() {
		testExpression("-2.5 * red", -2.5 * creature.getRed());
	}

	@Test
	public void propertyAndNegativeLiteralTest() {
		testExpression("red * -2.5", creature.getRed() * -2.5);
	}
}

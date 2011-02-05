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

	@Test
	public void multiplicationHasHigherPrecedenceThanAddition() {
		testExpression("2 + 3 * 4", 14);
		testExpression("3 * 4 + 2", 14);
		testExpression("2 * 3 + 4", 10);
	}

	@Test
	public void sutractionTest() {
		testExpression("3 - 2", 1);
	}

	@Test
	public void subtractionLeftAssociativityTest() {
		testExpression("10 - 5 - 2", 3);
		testExpression("10 - (5 - 2)", 7);
	}

	@Test
	public void plusMinusTest() {
		testExpression("3 + -2", 1);
	}

	@Test
	public void minusMinusTest() {
		testExpression("3 - -2", 5);
	}

	@Test
	public void minusMinusPropertyTest() {
		testExpression("red --blue", creature.getRed() + creature.getBlue());
	}

	@Test
	public void bubbleUpBadNodeTest() {
		testExpression("eree + 5", Double.NaN);
	}

	@Test
	public void divisionTest() {
		testExpression("6 / 2", 3);
	}

	@Test
	public void divisionAssociativityTest() {
		testExpression("12 / 6 / 2", 1);
		testExpression("12 / (6 / 2)", 4);
	}
}

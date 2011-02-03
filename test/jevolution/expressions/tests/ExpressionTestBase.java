/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jevolution.expressions.tests;

import jevolution.*;
import jevolution.expressions.*;
import org.junit.Before;

import static org.junit.Assert.*;

/**
 * This is an abstract base class to make it very simple to test expressions.
 * Expression testing classes should extend from this and use the testExpression method.
 *
 * A Creature instance will be created in the <code>creature</code> member variable
 * before each test.
 *
 * Example test:
 *
 * <pre> @Test
 * public void someTest() {
 *	testExpression("red + blue", creature.getRed() + creature.getBlue());
 * }</pre>
 *
 * The test will fail if the parsed expression doesn't evaluate to the expected value (within <code>TOLERANCE</code>).
 *
 * @author kuhlmancer
 */
public abstract class ExpressionTestBase {
	protected final static double TOLERANCE = 0.0001;

	protected EnvironmentPanel panel;
	protected Creature creature;

	@Before
	public void setup() {
		panel = new EnvironmentPanel(800, 600);
		creature = Creature.random(panel);
	}

	protected void testExpression(String expression, double expectedValue) {
		CreatureExpression e = new CreatureExpression(expression);

		assertEquals("Expression did not evaluate to the expected value.", expectedValue, e.evaluate(creature), TOLERANCE);
	}
}

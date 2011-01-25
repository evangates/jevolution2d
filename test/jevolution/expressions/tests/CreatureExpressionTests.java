/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jevolution.expressions.tests;

import jevolution.Creature;
import jevolution.expressions.CreatureExpression;
import jevolution.EnvironmentPanel;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kuhlmancer
 */
public class CreatureExpressionTests {

	private final static double delta = 0.0001;

	@Test
	public void constructorWithExpressionTest() {
		CreatureExpression e = new CreatureExpression("width");

		assertNotNull(e);
	}

	@Test
	public void canEvaluateWidthExpressionTest() {
		CreatureExpression e = new CreatureExpression("width");
		EnvironmentPanel panel = new EnvironmentPanel(800, 600);
		Creature c = Creature.random(panel);

		double expectedValue = c.getWidth();
		double actualValue = e.evaluate(c);

		assertEquals("Expression did not evaluate to the correct value.", expectedValue, actualValue, delta);
	}

	@Test
	public void canEvaluateHeightExpressionTest() {
		CreatureExpression e = new CreatureExpression("height");
		EnvironmentPanel panel = new EnvironmentPanel(800, 600);
		Creature c = Creature.random(panel);

		double expectedValue = c.getHeight();
		double actualValue = e.evaluate(c);

		assertEquals("Expression did not evaluate to the correct value.", expectedValue, actualValue, delta);
	}

	@Test
	public void canEvaluateWidthTimesHeightExpressionTest() {
		CreatureExpression e = new CreatureExpression("width*height");
		EnvironmentPanel panel = new EnvironmentPanel(800, 600);
		Creature c = Creature.random(panel);

		double expectedValue = c.getWidth() * c.getHeight();
		double actualValue = e.evaluate(c);

		assertEquals("Expression did not evaluate to the correct value.", expectedValue, actualValue, delta);

	}
}

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
public class ParenthesisTests extends ExpressionTestBase {
     @Test
     public void aroundLiteralTest() {
		 testExpression("(3)", 3);
	 }

 	 @Test
	 public void aroundLiteralsTest() {
		 testExpression("(3) * (3)", 9);
	 }


	 @Test
	 public void overridesPrecedence() {
		 testExpression(" 2 + 3  * 4", 14);
		 testExpression("4 * (2 + 3)", 20);
		 testExpression("(4 + 2) * 3", 18);
	 }

	 @Test
	 public void allMultTest() {
		 testExpression("(3*2)*5", 30);
		 testExpression("3*(2*5)", 30);
	 }

	 @Test
	 public void nestedTest() {
		 testExpression("((3))", 3);
		 testExpression("((3+2)*4 + 3) * 2", 46);
	 }

	 @Test
	 public void currentStrengthFormulaTest() {
		 testExpression("-red - green + blue + 3*(height - width)",
				 -creature.getRed() - creature.getGreen() + creature.getBlue() + 3*(creature.getHeight() - creature.getWidth()));
	 }
}
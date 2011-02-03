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
public class PropertyTests extends ExpressionTestBase {
     @Test
     public void widthExpressionTest() {
		 testExpression("width", creature.getWidth());
	 }

	 @Test
	 public void negatedWidthTest() {
		 testExpression("-width", -creature.getWidth());
	 }

	 @Test
	 public void heightExpressionTest() {
		 testExpression("height", creature.getHeight());
	 }

	 @Test
	 public void redExpressionTest() {
		 testExpression("red", creature.getRed());
	 }

	 @Test
	 public void blueExpressionTest() {
		 testExpression("blue", creature.getBlue());
	 }

	 @Test
	 public void greenExpressionTest() {
		 testExpression("green", creature.getGreen());
	 }

	 @Test
	 public void ignoreWhitespaceTest() {
		 testExpression("   width   \t", creature.getWidth());
	 }
}
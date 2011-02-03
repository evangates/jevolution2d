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
public class LiteralTests extends ExpressionTestBase {

     @Test
     public void positiveIntLiteralTest() {
		 testExpression("1", 1);
	 }

	 @Test
	 public void negativeIntLiteralTest() {
		 testExpression("-1", -1);
	 }

	 @Test
	 public void positiveDoubleLiteralTest() {
		 testExpression("10.23", 10.23);
	 }

	 @Test
	 public void negativeDoubleLiteralTest() {
		 testExpression("-10.23", -10.23);
	 }
}
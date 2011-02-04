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

	 @Test
	 public void noDigitsBeforeDecimialTesT() {
		 testExpression(".5", .5);
	 }

	 @Test
	 public void badLiteralTest() {
		 testExpression("ree", Double.NaN);
	 }

	 @Test
	 public void positiveExponentLiteralTest() {
		 testExpression("0.1e4", 0.1e4);
	 }

	 @Test
	 public void negativeExponentLiteralTest() {
		 testExpression("0.1e-4", 0.1e-4);
	 }

	 @Test
	 public void missingExponentPartLiteralTest() {
		 testExpression("0.1e", Double.NaN);
	 }

	 @Test
	 public void noLeadingDigitTest() {
		 testExpression(".1e-4", 0.1e-4);
	 }

	 @Test
	 public void capitalETest() {
		 testExpression("0.1E-4", 0.1E-4);
	 }
}
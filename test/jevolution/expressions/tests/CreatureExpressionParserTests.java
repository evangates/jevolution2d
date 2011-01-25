/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jevolution.expressions.tests;

import jevolution.expressions.HeightNode;
import jevolution.expressions.WidthNode;
import jevolution.expressions.CreatureExpressionNode;
import jevolution.expressions.CreatureExpressionParser;
import jevolution.expressions.MultiplicationNode;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kuhlmancer
 */
public class CreatureExpressionParserTests {
     @Test
     public void widthExpressionTest() {
		 CreatureExpressionNode result = CreatureExpressionParser.parse("width");
		 
		 assertTrue("Expression wasn't a width node.", result instanceof WidthNode);
	 }

	 @Test
	 public void heightExpressionTest() {
		 CreatureExpressionNode result = CreatureExpressionParser.parse("height");

		 assertTrue("Expression wasn't a height node.", result instanceof HeightNode);
	 }

	 @Test
	 public void multiplicationExpressionTest() {
		 CreatureExpressionNode result = CreatureExpressionParser.parse("width*height");

		 assertTrue("Expression wasn't a multiplication node.", result instanceof MultiplicationNode);
	 }

	 @Test
	 public void ignoreWhitespaceTest() {
		 CreatureExpressionNode result = CreatureExpressionParser.parse("  width  ");

		 assertTrue("Parser didn't ignore whitespace properly.", result instanceof WidthNode);
	 }

}
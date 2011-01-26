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
	 public void redExpressionTest() {
		 CreatureExpressionNode result = CreatureExpressionParser.parse("red");

		 assertTrue("Expression wasn't a red node.", result instanceof RedNode);
	 }

	 @Test
	 public void blueExpressionTest() {
		 CreatureExpressionNode result = CreatureExpressionParser.parse("blue");

		 assertTrue("Expression wasn't a blue node.", result instanceof BlueNode);
	 }

	 @Test
	 public void greenExpressionTest() {
		 CreatureExpressionNode result = CreatureExpressionParser.parse("green");

		 assertTrue("Expression wasn't a green node.", result instanceof GreenNode);
	 }

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
	 public void ignoreWhitespaceTest() {
		 CreatureExpressionNode result = CreatureExpressionParser.parse("  width  ");

		 assertTrue("Parser didn't ignore whitespace properly.", result instanceof WidthNode);
	 }

	 @Test
	 public void ignoreWhitespaceBetweenTokensTest() {
		 CreatureExpressionNode node = CreatureExpressionParser.parse("width   *    height");

		 assertTrue("Parser didn't ignore whitespace properly.", node instanceof MultiplicationNode);
	 }
}
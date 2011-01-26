/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jevolution.expressions.tests;

import jevolution.expressions.CreatureExpressionTokenizer;
import jevolution.expressions.Token;
import java.util.Queue;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kuhlmancer
 */
public class CreatureExpressionTokenizerTests {
     @Test
     public void canTokenizeWidthExpressionTest() {
		 Queue<Token> tokens = CreatureExpressionTokenizer.tokenize("width");
		 
		 assertEquals("Tokenizer didn't create the correct number of tokens.", 1, tokens.size());
		 assertEquals("Tokenizer didn't create the correct token.", Token.WIDTH, tokens.peek());
	 }

	 @Test
	 public void canTokenizeHeightExpression() {
		 Queue<Token> tokens = CreatureExpressionTokenizer.tokenize("height");

		 assertEquals("Tokenizer didn't create the correct token.", Token.HEIGHT, tokens.peek());
	 }

	 @Test
	 public void whitespaceTokenizesToEmptyTokenQueue() {
		 Queue<Token> tokens = CreatureExpressionTokenizer.tokenize("    ");

		 assertTrue("Tokenizer didn't turn whitespace into empty token colleciton.", tokens.isEmpty());
	 }

	 @Test
	 public void multiplicationExpressionIsThreeTokens() {
		 Queue<Token> tokens = CreatureExpressionTokenizer.tokenize("width*height");

		 assertEquals("First token wasn't width.", Token.WIDTH, tokens.poll());
		 assertEquals("Second token wasn't multiplication.", Token.MULTIPLY, tokens.poll());
		 assertEquals("Third token wasn't height.", Token.HEIGHT, tokens.poll());
		 assertTrue("There were more than 3 tokens.", tokens.isEmpty());
	 }

	 @Test
	 public void ignoreWhitespaceBetweenTokens() {
		 Queue<Token> tokens = CreatureExpressionTokenizer.tokenize("width	  *   height");

		 assertEquals("First token wasn't width.", Token.WIDTH, tokens.poll());
		 assertEquals("Second token wasn't multiplication.", Token.MULTIPLY, tokens.poll());
		 assertEquals("Third token wasn't height.", Token.HEIGHT, tokens.poll());
		 assertTrue("There were more than 3 tokens.", tokens.isEmpty());
	 }

}
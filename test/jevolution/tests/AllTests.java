package jevolution.tests;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import jevolution.expressions.tests.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author kuhlmancer
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	CreatureExpressionParserTests.class,
	CreatureExpressionTests.class
})
public class AllTests {
}
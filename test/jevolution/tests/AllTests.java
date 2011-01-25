package jevolution.tests;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import jevolution.expressions.tests.CreatureExpressionParserTests;
import jevolution.expressions.tests.CreatureExpressionTests;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author kuhlmancer
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({CreatureExpressionParserTests.class, CreatureExpressionTests.class})
public class AllTests {
}
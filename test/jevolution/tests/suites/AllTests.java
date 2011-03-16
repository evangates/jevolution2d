package jevolution.tests.suites;

import jevolution.expressions.tests.*;
import jevolution.tests.StatsTests_WithEmptyEnvironment;
import jevolution.tests.StatsTests_WithOneCreatureEnvironment;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author kuhlmancer
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	ParenthesisTests.class,
	LiteralTests.class,
	PropertyTests.class,
	BinaryExpressionTests.class,

	StatsTests_WithEmptyEnvironment.class,
	StatsTests_WithOneCreatureEnvironment.class
})
public class AllTests {
}
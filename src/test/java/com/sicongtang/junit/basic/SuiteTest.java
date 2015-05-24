package com.sicongtang.junit.basic;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 
 * This works in conjunction with the @Suite annotation, which tells the Suite
 * runner which test classes to include in this suite and in which order.
 * 
 * @author BobbyTang
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ AssertTests.class, RunnerMockitoTest.class, RunnerSpringTest.class })
public class SuiteTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}

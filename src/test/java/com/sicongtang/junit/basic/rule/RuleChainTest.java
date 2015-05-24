package com.sicongtang.junit.basic.rule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.experimental.results.PrintableResult.testResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class RuleChainTest {

	private static final List<String> LOG = new ArrayList<String>();

	private static class LoggingRule extends TestWatcher {
		private final String label;

		public LoggingRule(String label) {
			this.label = label;
		}

		@Override
		protected void starting(Description description) {
			LOG.add("starting " + label);
		}

		@Override
		protected void finished(Description description) {
			LOG.add("finished " + label);
		}
	}

	public static class UseRuleChain {
		@Rule
		public final RuleChain chain = RuleChain.outerRule(new LoggingRule("outer rule")).around(new LoggingRule("middle rule"))
				.around(new LoggingRule("inner rule"));

		@Test
		public void example() {
			assertTrue(true);
		}
	}

	@Test
	public void executeRulesInCorrectOrder() throws Exception {
		testResult(UseRuleChain.class);
		List<String> expectedLog = Arrays.asList("starting outer rule", "starting middle rule", "starting inner rule",
				"finished inner rule", "finished middle rule", "finished outer rule");
		assertEquals(expectedLog, LOG);
	}

}

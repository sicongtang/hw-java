package me.sicongtang.junit.basic.rule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;

public class RuleTimeoutTest {

	public static String log;

	@Rule
	public TestRule globalTimeout = new Timeout(20);

	@Test
	public void testInfiniteLoop1() {
		log += "ran1";
		for (;;) {
		}
	}

	@Test
	public void testInfiniteLoop2() {
		log += "ran2";
		for (;;) {
		}
	}
}

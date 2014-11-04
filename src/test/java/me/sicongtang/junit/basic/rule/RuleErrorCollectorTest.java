package me.sicongtang.junit.basic.rule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

public class RuleErrorCollectorTest {

	@Rule
	public ErrorCollector collector = new ErrorCollector();

	@Test
	public void example() {
		collector.addError(new Throwable("first thing went wrong"));
		collector.addError(new Throwable("second thing went wrong"));
	}

}

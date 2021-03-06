package com.sicongtang.junit.basic.rule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.experimental.results.ResultMatchers.*;
import static org.junit.experimental.results.PrintableResult.testResult;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Verifier;

public class RuleVerifierTest {

	private static String sequence;

	public static class UsesVerifier {
	  @Rule
	  public Verifier collector = new Verifier() {
	    @Override
	    protected void verify() {
	      sequence += "verify ";
	    }
	  };

	  @Test
	  public void example() {
	    sequence += "test ";
	  }
	}

	@Test
	public void verifierRunsAfterTest() {
	  sequence = "";
	  assertThat(testResult(UsesVerifier.class), isSuccessful());
	  assertEquals("test verify ", sequence);
	}

}

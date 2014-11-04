package me.sicongtang.junit.basic;

import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;

public class IgnoringTest {

	@Ignore("Test is ignored as a demonstration")
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}

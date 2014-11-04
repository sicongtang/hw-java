package me.sicongtang.junit.basic;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class GlobalTimeoutTest {

	public static String log;
	

	/**
	 * 	10 seconds max per method tested 
	 */
	@Rule
	public Timeout globalTimeout = new Timeout(1000);

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

package me.sicongtang.junit.basic;

import org.junit.Test;

public class TimeoutTest {

	@Test(timeout = 1000)
	public void testWithTimeout() {
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test(timeout = 1000)
	public void testWithTimeout2() {
		try {
			Thread.sleep(900);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}

package com.sicongtang.junit.basic;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FixturesTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("FixturesTest.setUpBeforeClass()");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("FixturesTest.tearDownAfterClass()");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("FixturesTest.setUp()");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("FixturesTest.tearDown()");
	}

	@Test
	public void test1() {
		System.out.println("FixturesTest.test1()");
	}
	
	@Test
	public void test2() {
		System.out.println("FixturesTest.test2()");
	}

}

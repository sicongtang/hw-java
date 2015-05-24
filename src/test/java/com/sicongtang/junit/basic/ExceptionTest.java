package com.sicongtang.junit.basic;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;
import org.junit.rules.ExpectedException;

public class ExceptionTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	/**
	 * @Test (expected = Exception.class)
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public void empty() {
		new ArrayList<Object>().get(0);
	}

	/**
	 * The try/fail/catch Idiom
	 */
	@Test
	public void testExceptionMessage() {
		try {
			System.out.println("ExceptionTest.testExceptionMessage() 1 ");
			new ArrayList<Object>().get(0);
			System.out.println("ExceptionTest.testExceptionMessage() 2 ");
			fail("Expected an IndexOutOfBoundsException to be thrown");
			System.out.println("ExceptionTest.testExceptionMessage() 3 ");
		} catch (IndexOutOfBoundsException anIndexOutOfBoundsException) {
			assertThat(anIndexOutOfBoundsException.getMessage(), is("Index: 0, Size: 0"));
		}
	}

	/**
	 * ExpectedException Rule
	 * @throws IndexOutOfBoundsException
	 */
	@Test
	public void shouldTestExceptionMessage() throws IndexOutOfBoundsException {
		List<Object> list = new ArrayList<Object>();

		thrown.expect(IndexOutOfBoundsException.class);
		thrown.expectMessage("Index: 0, Size: 0");
		thrown.expectMessage(JUnitMatchers.containsString("Size: 0"));
		System.out.println("ExceptionTest.shouldTestExceptionMessage() 1 ");
		list.get(0); // execution will never get past this line
		System.out.println("ExceptionTest.shouldTestExceptionMessage() 2 ");
	}

}

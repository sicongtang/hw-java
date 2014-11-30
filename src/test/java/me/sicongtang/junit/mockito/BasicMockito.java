package me.sicongtang.junit.mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class BasicMockito {

	/**
	 * 1. Let's verify some behaviour!
	 */
	@Test
	public void testBehaviour() {
		// mock creation
		List mockedList = mock(List.class);

		// using mock object
		mockedList.add("one");
		mockedList.clear();

		// verification
		verify(mockedList).add("one");
		verify(mockedList).clear();
	}

	/**
	 * 2. How about some stubbing?
	 */
	@Test
	public void testStubbing() {
		// You can mock concrete classes, not only interfaces
		LinkedList mockedList = mock(LinkedList.class);

		// stubbing
		when(mockedList.get(0)).thenReturn("first");
		when(mockedList.get(1)).thenThrow(new RuntimeException());

		// following prints "first"
		System.out.println(mockedList.get(0));

		// following throws runtime exception
		System.out.println(mockedList.get(1));

		// following prints "null" because get(999) was not stubbed
		System.out.println(mockedList.get(999));

		/*
		 * Although it is possible to verify a stubbed invocation, usually it's
		 * just redundant
		 * 
		 * If your code cares what get(0) returns then something else breaks
		 * (often before even verify() gets executed).
		 * 
		 * If your code doesn't care what get(0) returns then it should not be
		 * stubbed. Not convinced? See here.
		 */
		verify(mockedList).get(0);
	}

	/**
	 * 3. Argument matchers
	 */
	@Test
	public void testArgumentMatchers() {
		// // stubbing using built-in anyInt() argument matcher
		// when(mockedList.get(anyInt())).thenReturn("element");
		//
		// // stubbing using hamcrest (let's say isValid() returns your own
		// // hamcrest matcher):
		// when(mockedList.contains(argThat(isValid()))).thenReturn("element");
		//
		// // following prints "element"
		// System.out.println(mockedList.get(999));
		//
		// // you can also verify using an argument matcher
		// verify(mockedList).get(anyInt());

	}

	/**
	 * 4. Verifying exact number of invocations / at least x / never
	 */
	@Test
	public void testVerifyInvocations() {

	}
}

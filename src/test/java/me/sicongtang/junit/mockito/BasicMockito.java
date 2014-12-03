package me.sicongtang.junit.mockito;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.mockito.InOrder;

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
		LinkedList mockedList = mock(LinkedList.class);

		// stubbing using built-in anyInt() argument matcher
		when(mockedList.get(anyInt())).thenReturn("element");

		// stubbing using hamcrest (let's say isValid() returns your own
		// hamcrest matcher):
		// when(mockedList.contains(argThat(isValid()))).thenReturn("element");

		// following prints "element"
		System.out.println(mockedList.get(999));

		// you can also verify using an argument matcher
		verify(mockedList).get(anyInt());

	}

	/**
	 * 4. Verifying exact number of invocations / at least x / never
	 */
	@Test
	public void testVerifyInvocations() {
		LinkedList mockedList = mock(LinkedList.class);
		// using mock
		mockedList.add("once");

		mockedList.add("twice");
		mockedList.add("twice");

		mockedList.add("three times");
		mockedList.add("three times");
		mockedList.add("three times");

		// following two verifications work exactly the same - times(1) is used
		// by default
		verify(mockedList).add("once");
		verify(mockedList, times(1)).add("once");

		// exact number of invocations verification
		verify(mockedList, times(2)).add("twice");
		verify(mockedList, times(3)).add("three times");

		// verification using never(). never() is an alias to times(0)
		verify(mockedList, never()).add("never happened");

		// verification using atLeast()/atMost()
		verify(mockedList, atLeastOnce()).add("three times");
		verify(mockedList, atLeast(2)).add("five times");
		verify(mockedList, atMost(5)).add("three times");
	}

	/**
	 * 5. Stubbing void methods with exceptions
	 */
	@Test
	public void testStubbingVoidMethodsWithExceptions() {
		LinkedList mockedList = mock(LinkedList.class);
		doThrow(new RuntimeException()).when(mockedList).clear();

		// following throws RuntimeException:
		mockedList.clear();
	}

	/**
	 * 6. Verification in order
	 * 
	 */
	@Test
	public void testVerificationInOrder() {
		List firstMock = mock(List.class);
		List secondMock = mock(List.class);

		// using mocks
		firstMock.add("was called first");
		secondMock.add("was called second");

		// create inOrder object passing any mocks that need to be verified in
		// order
		InOrder inOrder = inOrder(firstMock, secondMock);

		// following will make sure that firstMock was called before secondMock
		inOrder.verify(firstMock).add("was called first");
		inOrder.verify(secondMock).add("was called second");
	}

	/**
	 * 7. Making sure interaction(s) never happened on mock
	 */
	@Test
	public void testInteractionNeverHappenedOnMock() {
		List mockOne = mock(List.class);
		List mockTwo = mock(List.class);
		List mockThree = mock(List.class);
		// using mocks - only mockOne is interacted
		mockOne.add("one");

		// ordinary verification
		verify(mockOne).add("one");

		// verify that method was never called on a mock
		verify(mockOne, never()).add("two");

		// verify that other mocks were not interacted
		verifyZeroInteractions(mockTwo, mockThree);

	}

	/**
	 * 8. Finding redundant invocations
	 */
	@Test
	public void testFindRedundantInvocations() {
		List mockedList = mock(List.class);

		// using mocks
		mockedList.add("one");
		mockedList.add("two");

		verify(mockedList).add("one");

		// following verification will fail
		verifyNoMoreInteractions(mockedList);
	}

	/**
	 * 10. Stubbing consecutive calls (iterator-style stubbing)
	 */
	@Test
	public void testStubbingConsecutiveCalls() {
		
//		when(mock.someMethod("some arg")).thenThrow(new RuntimeException()).thenReturn("foo");
//
//		// First call: throws runtime exception:
//		mock.someMethod("some arg");
//
//		// Second call: prints "foo"
//		System.out.println(mock.someMethod("some arg"));
//
//		// Any consecutive call: prints "foo" as well (last stubbing wins).
//		System.out.println(mock.someMethod("some arg"));

	}

}

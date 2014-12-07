package me.sicongtang.junit.mockito;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.internal.verification.api.VerificationData;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.verification.Timeout;
import org.mockito.verification.VerificationMode;

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
		List<String> mock = mock(List.class);

		when(mock.contains("some arg")).thenThrow(new RuntimeException()).thenReturn(false);

		// First call: throws runtime exception:
		mock.add("some arg");

		// Second call: prints "false"
		System.out.println(mock.add("some arg"));

		// Any consecutive call: prints "false" as well (last stubbing wins).
		System.out.println(mock.add("some arg"));

	}

	/**
	 * 11. Stubbing with callbacks
	 */
	@Test
	public void testStubbingWithCallbacks() {
		HashMap<String, String> mock = mock(HashMap.class);

		when(mock.get(anyString())).thenAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				Object mock = invocation.getMock();
				return "called with arguments: " + args;
			}
		});

		// Following prints "called with arguments: foo"
		System.out.println(mock.get("foo"));
	}

	/**
	 * 12. doThrow()|doAnswer()|doNothing()|doReturn() family of methods for
	 * stubbing voids (mostly)
	 */
	@Test
	public void testStubbingVoids() {
		List<String> mockedList = mock(List.class);

		doThrow(new RuntimeException()).when(mockedList).clear();

		// following throws RuntimeException:
		mockedList.clear();
	}

	/**
	 * 13. Spying on real objects
	 */
	@Test
	public void testSpyingOnRealObjects() {
		List list = new LinkedList();
		List spy = spy(list);

		// optionally, you can stub out some methods:
		when(spy.size()).thenReturn(100);

		// using the spy calls real methods
		spy.add("one");
		spy.add("two");

		// prints "one" - the first element of a list
		System.out.println(spy.get(0));

		// size() method was stubbed - 100 is printed
		System.out.println(spy.size());

		// optionally, you can verify
		verify(spy).add("one");
		verify(spy).add("two");
	}

	/**
	 * 14. Changing default return values of unstubbed invocations (Since 1.7)
	 * However, it can be helpful for working with legacy systems.
	 */
	@Test
	public void testChangingDefaultReturnValuesOfUnstubbedInvocations() {
		List<String> mock = mock(List.class, Mockito.RETURNS_SMART_NULLS);

		// calling unstubbed method here:
		String stuff = mock.get(1);

		// using object returned by unstubbed call:
		stuff.toString();

		// Above doesn't yield NullPointerException this time!
		// Instead, SmartNullPointerException is thrown.
		// Exception's cause links to unstubbed mock.getStuff() - just click on
		// the stack trace.
	}

	/**
	 * 15. Capturing arguments for further assertions (Since 1.8.0)
	 */
	@Test
	public void testCapturingArgsForFurtherAssertions() {
		// ArgumentCaptor<Person> argument =
		// ArgumentCaptor.forClass(Person.class);
		// verify(mock).doSomething(argument.capture());
		// assertEquals("John", argument.getValue().getName());
	}

	/**
	 * 16. Real partial mocks (Since 1.8.0)
	 */
	@Test
	public void testRealPartialMocks() {
		// you can create partial mock with spy() method:
		List list = spy(new LinkedList());

		// you can enable partial mock capabilities selectively on mocks:
		List mock = mock(List.class);
		// Be sure the real implementation is 'safe'.
		// If real implementation throws exceptions or depends on specific state
		// of the object then you're in trouble.
		when(mock.get(0)).thenCallRealMethod();
	}

	/**
	 * 17. Resetting mocks (Since 1.8.0)
	 */
	@Test
	public void testResttingMocks() {
		List mock = mock(List.class);
		when(mock.size()).thenReturn(10);
		mock.add(1);

		reset(mock);
		// at this point the mock forgot any interactions & stubbing
	}

	/**
	 * 18. Troubleshooting & validating framework usage (Since 1.8.0)
	 */
	@Test
	public void testValidatingFrameworkUsage() {

	}

	/**
	 * 19. Aliases for behavior driven development (Since 1.8.0)
	 */
	@Test
	public void testAliasesForBehaviorDrivenDevelopment() {

	}

	/**
	 * 20. (**New**) Serializable mocks (Since 1.8.1)
	 */
	@Test
	public void testSerializableMocks() {
		List serializableMock = mock(List.class, withSettings().serializable());
		// No worries, you will hardly ever use it.
		List<Object> list = new ArrayList<Object>();
		List<Object> spy = mock(ArrayList.class, withSettings().spiedInstance(list).defaultAnswer(CALLS_REAL_METHODS)
				.serializable());

	}

	/**
	 * 21. (**New**) New annotations: @Captor, @Spy, @InjectMocks (Since 1.8.3)
	 */
	@Test
	public void testNewAnnotations() {
		/*
		 * @Captor simplifies creation of ArgumentCaptor - useful when the
		 * argument to capture is a nasty generic class and you want to avoid
		 * compiler warnings
		 * 
		 * @Spy - you can use it instead spy(Object).
		 * 
		 * @InjectMocks - injects mocks into tested object automatically.
		 */
	}

	/**
	 * 22. (**New**) Verification with timeout (Since 1.8.5)
	 */
	@Test
	public void testVerifyicationWithTimeout() {
		List mock = mock(List.class);
		
		// passes when someMethod() is called within given time span
		verify(mock, timeout(100)).add("a");
		// above is an alias to:
		verify(mock, timeout(100).times(1)).add("a");

		// passes when someMethod() is called *exactly* 2 times within given
		// time span
		verify(mock, timeout(100).times(2)).add("a");

		// passes when someMethod() is called *at lest* 2 times within given
		// time span
		verify(mock, timeout(100).atLeast(2)).add("a");

		// verifies someMethod() within given time span using given verification
		// mode
		// useful only if you have your own custom verification modes.
		verify(mock, new Timeout(100, new VerificationMode() {
			@Override
			public void verify(VerificationData data) {
				
			}
		})).add("a");
	}

}

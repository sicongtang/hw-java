package me.sicongtang.junit.basic;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.matchers.JUnitMatchers.both;
import static org.junit.matchers.JUnitMatchers.containsString;
import static org.junit.matchers.JUnitMatchers.everyItem;
import static org.junit.matchers.JUnitMatchers.hasItems;

import java.util.Arrays;

import org.hamcrest.core.CombinableMatcher;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

//@FixMethodOrder(MethodSorters.JVM)
// Leaves the test methods in the order returned by the JVM. This order may vary from run to run.
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
// Sorts the test methods by method name, in lexicographic order.
public class AssertTests {
	@Test
	public void testAssertArrayEquals() {
		byte[] expected = "trial".getBytes();
		byte[] actual = "trial".getBytes();
		assertArrayEquals("failure - byte arrays not same", expected, actual);
	}

	@Test
	public void testAssertEquals() {
		assertEquals("failure - strings are not equal", "text", "text");
	}

	@Test
	public void testAssertFalse() {
		assertFalse("failure - should be false", false);
	}

	@Test
	public void testAssertNotNull() {
		assertNotNull("should not be null", new Object());
	}

	@Test
	public void testAssertNotSame() {
		assertNotSame("should not be same Object", new Object(), new Object());
	}

	@Test
	public void testAssertNull() {
		assertNull("should be null", null);
	}

	@Test
	public void testAssertSame() {
		Integer aNumber = Integer.valueOf(768);
		assertSame("should be same", aNumber, aNumber);
	}

	// JUnit Matchers assertThat
	@Test
	public void testAssertThatBothContainsString() {
		assertThat("albumen", both(containsString("a")).and(containsString("b")));
	}

	@Test
	public void testAssertThathasItemsContainsString() {
		assertThat(Arrays.asList("one", "two", "three"), hasItems("one", "three"));
	}

	@Test
	public void testAssertThatEveryItemContainsString() {
		org.junit.Assert
				.assertThat(Arrays.asList(new String[] { "fun", "ban", "net" }), everyItem(containsString("n")));
	}

	// Core Hamcrest Matchers with assertThat
	@Test
	public void testAssertThatHamcrestCoreMatchers() {
		assertThat("good", allOf(equalTo("good"), startsWith("good")));
		assertThat("good", not(allOf(equalTo("bad"), equalTo("good"))));
		assertThat("good", anyOf(equalTo("bad"), equalTo("good")));
		assertThat(7, not(CombinableMatcher.<Integer> either(equalTo(3)).or(equalTo(4))));
		assertThat(new Object(), not(sameInstance(new Object())));
	}

	@Test
	public void testAssertTrue() {
		assertTrue("failure - should be true", true);
	}
}
package me.sicongtang.utils.guava.basic.collections.immutable;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

/**
 * Immutable objects have many advantages, including:
 * 
 * Safe for use by untrusted libraries. Thread-safe: can be used by many threads with no risk of race conditions.
 * Doesn't need to support mutation, and can make time and space savings with that assumption. All immutable collection
 * implementations are more memory-efficient than their mutable siblings (analysis) Can be used as a constant, with the
 * expectation that it will remain fixed
 * 
 * @author BobbyTang
 *
 */
public class Sample {
	public static final ImmutableSet<String> COLOR_NAMES = ImmutableSet.of("red", "orange", "yellow", "green", "blue",
			"purple");

	public static void main(String[] args) {
		
	}
}

class Bar {

}

class Foo {
	final ImmutableSet<Bar> bars;

	Foo(Set<Bar> bars) {
		this.bars = ImmutableSet.copyOf(bars); // defensive copy!
	}
}
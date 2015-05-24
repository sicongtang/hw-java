package com.sicongtang.jdk.tutorial.generics.typeinference;

/**
 * Generic version of the Box class.
 * 
 * @param <T>
 *            the type of value being boxed
 */
public class Box<T> {

	private T t; // T stands for "Type"

	public void set(T t) {
		this.t = t;
	}

	public T get() {
		return t;
	}
}

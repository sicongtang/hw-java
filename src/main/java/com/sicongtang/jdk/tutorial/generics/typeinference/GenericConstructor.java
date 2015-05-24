package com.sicongtang.jdk.tutorial.generics.typeinference;

public class GenericConstructor<X> {
	<T> GenericConstructor(T t) {
		// ...
	}

	public static void main(String[] args) {
		GenericConstructor<Integer> myObject = new GenericConstructor<>("");
	}
}

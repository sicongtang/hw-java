package com.sicongtang.jdk.tutorial.generics.exercises;

/**
 * Will the following code compile? If not, why?
 * 
 * @author BobbyTang
 *
 */
public class Ex11 {
	public static void main(String[] args) {
		Node<String> node = new Node<>();
		Comparable<String> comp = node;
	}
}

/**
 * Consider this class:
 *
 * @param <T>
 */
class Node<T> implements Comparable<T> {
	public int compareTo(T obj) { /* ... */
		return 0;
	}
	// ...
}
package com.sicongtang.jdk.tutorial.generics.exercises;

/**
 * Write a generic method to exchange the positions of two different elements in
 * an array.
 * 
 * @author BobbyTang
 *
 */
public class Ex03 {
	public static <T> void exchange(T[] array, int a, int b) {
		T tmp = array[a];
		array[a] = array[b];
		array[b] = tmp;
	}
}

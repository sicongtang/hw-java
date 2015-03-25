package me.sicongtang.jdk.tutorial.generics.exercises;

import java.util.List;

/**
 * Will the following method compile? If not, why?
 * 
 * @author BobbyTang
 *
 */
public class Ex7 {
	public static void print(List<? extends Number> list) {
		for (Number n : list)
			System.out.print(n + " ");
		System.out.println();
	}
}

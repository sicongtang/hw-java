package me.sicongtang.jdk.tutorial.forkjoin.hello;

import java.util.concurrent.ForkJoinPool;

public class ForkJoinTest {

	static ForkJoinPool forkJoinPool = new ForkJoinPool();
	public static final int LENGTH = 1000;

	public static void main(String[] args) {
		int[] numbers = new int[LENGTH];
		// Create an array with some values.
		for (int i = 0; i < LENGTH; i++) {
			numbers[i] = i * 2;
		}
		/*
		 * Invoke the NumberDividerTask with the array which in turn creates
		 * multiple sub tasks.
		 */
		int sum = forkJoinPool.invoke(new NumberDividerTask(numbers));

		System.out.println("Sum: " + sum);
	}
}
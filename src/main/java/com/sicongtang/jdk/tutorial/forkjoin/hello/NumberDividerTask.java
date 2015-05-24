package com.sicongtang.jdk.tutorial.forkjoin.hello;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class NumberDividerTask extends RecursiveTask<Integer> {

	int[] numbers;

	NumberDividerTask(int[] numbers) {
		this.numbers = numbers;
	}

	/*
	 * If the array has less than 20 elements, then just compute the sum, else
	 * split the array further.
	 */
	@Override
	protected Integer compute() {
		int sum = 0;
		List<RecursiveTask<Integer>> forks = new ArrayList<>();
		if (numbers.length > 20) {
			NumberDividerTask numberDividerTaskFirst = new NumberDividerTask(Arrays.copyOfRange(numbers, 0,
					numbers.length / 2));
			NumberDividerTask numberDividerTaskSecond = new NumberDividerTask(Arrays.copyOfRange(numbers,
					numbers.length / 2, numbers.length));
			forks.add(numberDividerTaskFirst);
			forks.add(numberDividerTaskSecond);
			numberDividerTaskFirst.fork();
			numberDividerTaskSecond.fork();

		} else {

			SumCalculatorTask sumCalculatorTask = new SumCalculatorTask(numbers);
			forks.add(sumCalculatorTask);
			sumCalculatorTask.fork();
		}

		// Combine the result from all the tasks
		for (RecursiveTask<Integer> task : forks) {
			sum += task.join();
		}
		return sum;

	}
}

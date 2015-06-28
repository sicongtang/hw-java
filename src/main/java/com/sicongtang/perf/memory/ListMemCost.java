package com.sicongtang.perf.memory;

import java.util.ArrayList;
import java.util.List;

public class ListMemCost {
	private static final long MEGABYTE = 1024L * 1024L;
	private static int INITIAL_CAPACITY = 10_000_000;

	public static long bytesToMegabytes(long bytes) {
		return bytes / MEGABYTE;
	}

	public static long calculateMemory() {
		Runtime runtime = Runtime.getRuntime();
		long memory = runtime.totalMemory() - runtime.freeMemory();
		return bytesToMegabytes(memory);
	}

	public static void main(String[] args) throws Exception {
		long initialMemory = ListMemCost.calculateMemory();
		long startTime = System.currentTimeMillis();
		System.out.println(initialMemory);
		List<Object> list = new ArrayList<Object>(INITIAL_CAPACITY);
		for (int i = 0; i < INITIAL_CAPACITY; i++) {
			list.add(new ConcreteObject());
		}

		System.out.println("list size: " + list.size());

		Runtime.getRuntime().gc();

		Thread.sleep(5000L);

		long endMemory = ListMemCost.calculateMemory();
		System.out.println(endMemory);
		System.out.println("memory cost: " + (endMemory - initialMemory) + " MB");
		System.out.println("time cost: " + (System.currentTimeMillis() - startTime) + " ms");
	}

}

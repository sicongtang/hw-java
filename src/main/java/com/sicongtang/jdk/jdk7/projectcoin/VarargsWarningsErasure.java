package com.sicongtang.jdk.jdk7.projectcoin;

import java.util.Arrays;
import java.util.List;

public class VarargsWarningsErasure {
	public static void main(String[] args) {
		List<List<String>> monthsInTwoLanguages = Arrays.asList(Arrays.asList("Jan", "Feb"),
				Arrays.asList("Mar", "Apr"));

		System.out.println(monthsInTwoLanguages);
	}
}

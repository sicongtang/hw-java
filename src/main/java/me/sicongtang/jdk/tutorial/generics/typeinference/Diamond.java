package me.sicongtang.jdk.tutorial.generics.typeinference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Diamond {
	public static void main(String[] args) {
		Map<String, List<String>> myMap1 = new HashMap<String, List<String>>();
		Map<String, List<String>> myMap2 = new HashMap<>();
		// unchecked conversion warning

		// Note that to take advantage of type inference during generic class
		// instantiation, you must use the diamond. In the following example,
		// the compiler generates an unchecked conversion warning because the
		// HashMap() constructor refers to the HashMap raw type, not the
		// Map<String, List<String>> type:
		Map<String, List<String>> myMap3 = new HashMap();

	}
}

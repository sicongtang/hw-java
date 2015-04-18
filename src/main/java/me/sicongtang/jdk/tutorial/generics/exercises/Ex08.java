package me.sicongtang.jdk.tutorial.generics.exercises;

import java.util.List;

/**
 * Write a generic method to find the maximal element in the range [begin, end)
 * of a list.
 * 
 * @author BobbyTang
 *
 */
public class Ex08 {

	public <T extends Comparable> T findMaxElement(List<? extends Comparable> list, int begin, int end) {
		return null;
	}

	/**
	 * subtle here
	 * 
	 * <T extends Object & Comparable<? super T>>
	 * 
	 * @param list
	 * @param begin
	 * @param end
	 * @return
	 */
	public static <T extends Object & Comparable<? super T>> T max(List<? extends T> list, int begin, int end) {

		T maxElem = list.get(begin);

		for (++begin; begin < end; ++begin)
			if (maxElem.compareTo(list.get(begin)) < 0)
				maxElem = list.get(begin);
		return maxElem;
	}
}

package me.sicongtang.utils.guava.basic.collections.newtypes;

import java.util.Iterator;
import java.util.Set;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;

public class MultisetSample {
	public static void main(String[] args) {
		Multiset<Integer> multiset = HashMultiset.create();
		multiset.add(1);
		multiset.add(1);
		multiset.add(2);
		multiset.add(3);
		multiset.add(1);

		System.out.println(multiset);

		Iterator<Integer> iter = multiset.iterator();
		while (iter.hasNext()) {
			Integer i = iter.next();
			System.out.println(i);
		}

		System.out.println("=====================");
		// count
		System.out.println("Count the number of occurrences of an element: " + multiset.count(1));
		// size
		System.out.println("The total number of occurrences of all elements: " + multiset.size());

		// elementSet: View the distinct elements of a Multiset<E> as a Set<E>.
		Set<Integer> elementSet = multiset.elementSet();
		System.out.println(elementSet);

		System.out.println("=====================");
		// entrySet(): Similar to Map.entrySet(), returns a Set<Multiset.Entry<E>>, containing entries supporting
		// getElement() and getCount().
		Set<Entry<Integer>> entrySet = multiset.entrySet();
		for (Entry<Integer> entry : entrySet) {
			System.out.println(entry.getElement() + "|" + entry.getCount());
		}

		// add(E, int) : Adds the specified number of occurrences of the specified element.
		multiset.add(4, 5);
		System.out.println(multiset);
		multiset.add(4);
		System.out.println(multiset);
		// remove(E, int) : Removes the specified number of occurrences of the specified element.
		multiset.remove(4, 2);
		System.out.println(multiset);

	}
}

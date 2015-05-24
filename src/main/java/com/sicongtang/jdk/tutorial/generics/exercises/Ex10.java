package com.sicongtang.jdk.tutorial.generics.exercises;

/**
 * Will the following code compile? If not, why?
 * 
 * @author BobbyTang
 *
 */
public class Ex10 {
	/**
	 * Because Node<Circle> is not a subtype of Node<Shape>
	 * @param args
	 */
	public static void main(String[] args) {
		Node<Circle> nc = new Node<>();
//		Node<Shape> ns = nc;
	}
}

/**
 * Given the following classes:
 */
class Shape { /* ... */
}

class Circle extends Shape { /* ... */
}

class Rectangle extends Shape { /* ... */
}

class Node<T> { /* ... */
}

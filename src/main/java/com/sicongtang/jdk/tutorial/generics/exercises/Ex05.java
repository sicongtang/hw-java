package com.sicongtang.jdk.tutorial.generics.exercises;

/**
 * What is the following class converted to after type erasure?
 * 
 * @author BobbyTang
 *
 */
public class Ex05 {

}

class PairErasure {

	public PairErasure(Object key, Object value) {
		this.key = key;
		this.value = value;
	}

	public Object getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	private Object key;
	private Object value;
}

class Pair<K, V> {

	public Pair(K key, V value) {
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public void setValue(V value) {
		this.value = value;
	}

	private K key;
	private V value;
}
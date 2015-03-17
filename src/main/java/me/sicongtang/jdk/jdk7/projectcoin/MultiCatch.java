package me.sicongtang.jdk.jdk7.projectcoin;

import java.util.NoSuchElementException;

public class MultiCatch {
	public static void main(String[] args) {

		try {
			
		} catch (ClassCastException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException | ArithmeticException | NoSuchElementException e) {
			e.printStackTrace();
		}

	}
}

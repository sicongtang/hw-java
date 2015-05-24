package com.sicongtang.jdk.jdk7.projectcoin;

import java.util.NoSuchElementException;

public class PreciseRethrow {

	public void foo(String bar) throws IllegalArgumentException, NoSuchElementException {
		try {

		} catch (Exception e) {
			throw e;
		}
	}
}

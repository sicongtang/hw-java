package com.linkedin.bobby.pattern.future;

public interface Request<T> {
	T invoke();
}

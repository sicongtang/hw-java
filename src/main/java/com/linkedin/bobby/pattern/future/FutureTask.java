package com.linkedin.bobby.pattern.future;

import java.util.concurrent.Callable;

public class FutureTask<T> implements Callable<T>{
	private Request<T> request;
	public FutureTask(Request<T> request) {
		this.request = request;
	}
	
	@Override
	public T call() throws Exception {
		T result = request.invoke();
		return result;
	}
	
}

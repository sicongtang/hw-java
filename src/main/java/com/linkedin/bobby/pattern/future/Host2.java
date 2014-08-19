package com.linkedin.bobby.pattern.future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Host2 {
	private ExecutorService service = Executors.newCachedThreadPool();

	public Future<Account> putRequest(Request request) {
		FutureTask<Account> task = new FutureTask<Account>(request);
		Future<Account> result = service.submit(task);
		return result;
	}
}

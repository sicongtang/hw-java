package com.linkedin.bobby.pattern.future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Host {
	private ExecutorService service = Executors.newCachedThreadPool();
	
	public Future<Data> putRequest(Data data){
		DataRequest request = new DataRequest("data request");
		request.setData(data);
		Future<Data> result = service.submit(new FutureTask<Data>(request));
		
		return result;
	}
}

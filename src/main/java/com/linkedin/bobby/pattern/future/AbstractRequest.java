package com.linkedin.bobby.pattern.future;

public abstract class AbstractRequest<T> implements Request<T>{
	protected String requestName;
	public AbstractRequest(String requestName){
		this.requestName = requestName;
	}
}

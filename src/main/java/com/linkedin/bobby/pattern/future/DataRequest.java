package com.linkedin.bobby.pattern.future;

public class DataRequest extends AbstractRequest<Data> {
	private Data data;
	
	public DataRequest(String requestName) {
		super(requestName);
	}

	@Override
	public Data invoke() {
		data.setStatus("start");
		return data;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	
}

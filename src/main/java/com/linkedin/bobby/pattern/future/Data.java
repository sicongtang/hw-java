package com.linkedin.bobby.pattern.future;

public class Data {
	private String dataName;
	private String status;
	
	public Data(){
		
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String toString(){
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("[dataName:");
		strBuff.append(dataName);
		strBuff.append(",status:");
		strBuff.append(status);
		strBuff.append("]");
		return strBuff.toString();
	}
	
}

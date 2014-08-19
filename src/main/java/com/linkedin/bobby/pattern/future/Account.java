package com.linkedin.bobby.pattern.future;

public class Account extends Entity{
	protected String salesPerson;
	
	@Override
	public Account invoke() {
		entityStatus = "invoked";
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return this;
	}

	@Override
	public String getEntityType() {
		return entityName  + "|" + entityStatus;
	}

	public String getSalesPerson() {
		return salesPerson;
	}

	public void setSalesPerson(String salesPerson) {
		this.salesPerson = salesPerson;
	}
	
	public String toString(){
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("[entityName:");
		strBuff.append(entityName);
		strBuff.append(",entityStatus:");
		strBuff.append(entityStatus);
		strBuff.append(",salesPerson:");
		strBuff.append(salesPerson);
		strBuff.append("]");
		return strBuff.toString();
	}
	
}

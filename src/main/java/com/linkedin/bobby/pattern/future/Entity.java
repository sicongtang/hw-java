package com.linkedin.bobby.pattern.future;

public abstract class Entity implements Request<Entity>{
	protected String entityName;
	protected String entityStatus;
	public abstract String getEntityType();
	
	
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getEntityStatus() {
		return entityStatus;
	}
	public void setEntityStatus(String entityStatus) {
		this.entityStatus = entityStatus;
	}
	
	@Override
	public Entity invoke() {
		return this;
	}
	
}

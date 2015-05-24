package com.sicongtang.module.penaltybox.guava;

public class StatisticsInfo {
	private Long count;

	// private Date createDate;

	public StatisticsInfo() {
		this.count = 0L;
	}

	public void increaseCount() {
		this.count++;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
	
	@Override
	public String toString() {
		return count.toString();
	}

}

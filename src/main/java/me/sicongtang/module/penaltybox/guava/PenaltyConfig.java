package me.sicongtang.module.penaltybox.guava;

import java.util.ArrayList;
import java.util.List;

public class PenaltyConfig {
	private long cacheMaxSize;
	private long cacheExpireSecs;
	private long threshold;
	private List<String> excludeKeys;
	private boolean disabledFlag;
	private long printInterval;
	private long penaltyMaxSize;
	private long penaltyExpireSecs;

	public PenaltyConfig() {
		this.cacheMaxSize = 5; // default as 10000 size
		this.cacheExpireSecs = 30; // default as 1 hour - 60 * 60 * 1
		
		this.threshold = 1; // default as 20 access - 20
		this.excludeKeys = new ArrayList<String>();
		this.disabledFlag = false;
		
		this.printInterval = 10; // default as 5 mins - 60 * 5
		this.penaltyMaxSize = 5; // default as 5000 size
		this.penaltyExpireSecs = 60; // default as 2 hour - 60 * 60 * 1
	}

	public boolean isDisabled() {
		return disabledFlag;
	}

	public long getCacheMaxSize() {
		return cacheMaxSize;
	}

	public long getCacheExpireSecs() {
		return cacheExpireSecs;
	}

	public long getThreshold() {
		return threshold;
	}

	public List<String> getExcludeKeys() {
		return excludeKeys;
	}

	public long getPrintInterval() {
		return printInterval;
	}

	public long getPenaltyMaxSize() {
		return penaltyMaxSize;
	}

	public long getPenaltyExpireSecs() {
		return penaltyExpireSecs;
	}


}

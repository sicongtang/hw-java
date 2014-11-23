package me.sicongtang.module.penaltybox.guava;

import java.util.ArrayList;
import java.util.List;

public class PenaltyConfig {
	private long maxSize;
	private long expireSecs;
	private long threshold;
	private List<String> excludeKeys;
	private boolean disabledFlag;
	private long printInterval;

	public PenaltyConfig() {
		// default as 10000 size
		this.maxSize = 5;
		// default as 1 hour - 60 * 60 * 1
		this.expireSecs = 30;
		// default as 20 access - 20
		this.threshold = 1;
		this.excludeKeys = new ArrayList<String>();
		this.disabledFlag = false;
		// default as 5 mins - 60 * 5
		this.printInterval = 10;
	}

	public boolean isDisabled() {
		return disabledFlag;
	}

	public long getMaxSize() {
		return maxSize;
	}

	public long getExpireSecs() {
		return expireSecs;
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
}

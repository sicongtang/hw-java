package me.sicongtang.module.penaltybox.guava;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class PenaltyBox {

	private static Logger logger = Logger.getLogger(PenaltyBox.class);

	private static PenaltyBox box = new PenaltyBox();

	private static PenaltyConfig config;

	private static LoadingCache<String, Long> cache;

	private PenaltyBox() {
		config = new PenaltyConfig();

		cache = CacheBuilder.newBuilder().maximumSize(config.getMaxSize())
				.expireAfterWrite(config.getExpireSecs(), TimeUnit.SECONDS).build(new CacheLoader<String, Long>() {
					public Long load(String key) throws Exception {
						return 0L;
					}
				});

		logger.info("Initialized penalty box[maxSize=" + config.getMaxSize() + ", expireSecs=" + config.getExpireSecs()
				+ "]");

		Thread thread = new Thread(new StatThread());
		logger.info("Start statistics thread in order to print penalty items.");
		thread.start();

	}

	public static PenaltyBox getInstance() {
		return box;
	}

	public LoadingCache<String, Long> getLoadingCache() {
		return cache;
	}

	private synchronized void record(String key) {
		Long count;
		try {
			count = cache.get(key);
			count = count + 1;
			cache.put(key, count);
		} catch (ExecutionException e) {
			logger.error("Encounter error while geting key from cache.");

		}
	}

	public boolean isPenalty(String key) {
		boolean bool = false;

		// if the function is disabled
		if (config.isDisabled()) {
			return bool;
		}

		// if key in the exclude list, will return false
		if (config.getExcludeKeys().contains(key)) {
			return bool;
		}

		record(key);

		// get count
		Long count = null;
		try {
			count = cache.get(key);
		} catch (ExecutionException e) {
			logger.error("Encounter error while geting key from cache.");
		}

		// check if great than threshold
		if (count > config.getThreshold()) {
			bool = true;
		}

		return bool;
	}

	private class StatThread implements Runnable {

		@Override
		public void run() {
			StringBuilder builder = new StringBuilder();
			while (true) {
				Set<Entry<String, Long>> entrySet = cache.asMap().entrySet();
				logger.info("The total number of penalty box element is " + entrySet.size());
				int count = 0;

				builder.delete(0, builder.length());

				for (Entry<String, Long> entry : entrySet) {
					if (entry.getValue() > config.getThreshold()) {
						count++;
						builder.append(entry.getKey());
						builder.append("=");
						builder.append(entry.getValue());
						builder.append("|");
					}
				}

				logger.info("The number of penalty box element that exceeds threshold[=" + config.getThreshold()
						+ "] is " + count);

				if (builder.length() > 0) {
					logger.info("Try to print key-value pairs that exceeds threshold, " + builder.toString());
				} else {
					logger.info("No key-value pairs that exceeds threshold");
				}

				try {
					TimeUnit.SECONDS.sleep(config.getPrintInterval());
				} catch (InterruptedException e) {
					//
				}

			}

		}
	}
}

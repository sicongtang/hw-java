package me.sicongtang.module.penaltybox.guava;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class PenaltyBox {

	private static Logger logger = Logger.getLogger(PenaltyBox.class);

	private static PenaltyBox box = new PenaltyBox();

	private static PenaltyConfig config;

	private static LoadingCache<String, StatisticsInfo> cache;

	private static LoadingCache<String, StatisticsInfo> penaltyCache;

	private PenaltyBox() {
		config = new PenaltyConfig();

		cache = CacheBuilder.newBuilder().maximumSize(config.getCacheMaxSize())
				.expireAfterWrite(config.getCacheExpireSecs(), TimeUnit.SECONDS)
				.build(new CacheLoader<String, StatisticsInfo>() {
					public StatisticsInfo load(String key) throws Exception {
						return new StatisticsInfo();
					}
				});

		penaltyCache = CacheBuilder.newBuilder().maximumSize(config.getPenaltyMaxSize())
				.expireAfterWrite(config.getPenaltyExpireSecs(), TimeUnit.SECONDS)
				.build(new CacheLoader<String, StatisticsInfo>() {
					public StatisticsInfo load(String key) throws Exception {
						return new StatisticsInfo();
					}
				});

		logger.info("Initialized penalty box cache[maxSize=" + config.getCacheMaxSize() + ", expireSecs="
				+ config.getCacheExpireSecs() + "], penalty cache[maxSize=" + config.getPenaltyMaxSize()
				+ ", expireSecs=" + config.getPenaltyExpireSecs() + "]");

		Thread thread = new Thread(new StatThread());
		logger.info("Start statistics thread in order to print penalty items");
		thread.start();

	}

	public static PenaltyBox getInstance() {
		return box;
	}

	public LoadingCache<String, StatisticsInfo> getCache() {
		return cache;
	}

	public LoadingCache<String, StatisticsInfo> getPenaltyCache() {
		return penaltyCache;
	}

	private synchronized StatisticsInfo record(String key) {
		StatisticsInfo info = null;

		// check penalty cache
		info = penaltyCache.getIfPresent(key);
		if (info != null) {
			info.increaseCount();

			return info;
		}

		// check cache
		info = cache.getIfPresent(key);
		if (info == null) {
			info = new StatisticsInfo();
		}
		info.increaseCount();

		if (info.getCount() <= config.getThreshold()) {
			cache.put(key, info);
		} else {
			penaltyCache.put(key, info);
			cache.invalidate(key);
		}

		return info;
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

		StatisticsInfo info = null;
		info = record(key);

		// check if great than threshold
		if (info.getCount() > config.getThreshold()) {
			bool = true;
		}

		return bool;
	}

	private class StatThread implements Runnable {

		@Override
		public void run() {
			StringBuilder builder = new StringBuilder();
			while (true) {
				Set<Entry<String, StatisticsInfo>> entrySet = penaltyCache.asMap().entrySet();
				logger.info("The total number of penalty box element is " + entrySet.size());
				int count = 0;

				builder.delete(0, builder.length());

				for (Entry<String, StatisticsInfo> entry : entrySet) {
					// if (entry.getValue().getCount() > config.getThreshold())
					// {
					count++;
					builder.append(entry.getKey());
					builder.append("=");
					builder.append(entry.getValue());
					builder.append("|");
					// }
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

package com.sicongtang.utils.guava.basic.caches;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

/**
 * Generally, the Guava caching utilities are applicable whenever:
 * 
 * You are willing to spend some memory to improve speed.
 * 
 * You expect that keys will sometimes get queried more than once.
 * 
 * Your cache will not need to store more data than what would fit in RAM.
 * (Guava caches are local to a single run of your application. They do not
 * store data in files, or on outside servers. If this does not fit your needs,
 * consider a tool like Memcached.)
 * 
 * @author BobbyTang
 *
 */
public class GuavaCaches {
	private static Logger logger = Logger.getLogger(GuavaCaches.class);

	public static void main(String[] args) throws Exception {
		String ip1 = "192.168.1.1";
		LoadingCache<String, CacheValue> cacheMap = CacheBuilder.newBuilder().maximumSize(10)
				.expireAfterWrite(3, TimeUnit.SECONDS).removalListener(new RemovalListener<String, CacheValue>() {
					@Override
					public void onRemoval(RemovalNotification<String, CacheValue> notification) {
						logger.info("Key: " + notification.getKey() + ", Value: " + notification.getValue());
					}

				}).build(new CacheLoader<String, CacheValue>() {
					// call cache.get if absent, this will return result of load
					// method
					public CacheValue load(String key) throws Exception {
						return null;
					}
				});

		//CacheValue ip1Value = cacheMap.getIfPresent(ip1);
		//logger.info("ip1Value: " + ip1Value);
		//logger.info("KeySet: " + cacheMap.asMap().keySet());
		CacheValue value = new CacheValue();
		value.setCount(1L);
		cacheMap.put(ip1, value);
		
		while(true) {
			CacheValue val2 = cacheMap.get(ip1);
			val2.setCount(val2.getCount() + 1);
			cacheMap.put(ip1, val2);
			logger.info("Print val2.getCount = " + val2.getCount() );
		}
		
		//cacheMap.put(ip1, 5);
		// cacheMap.invalidate(ip1);

		//cacheMap.put(ip1, cacheMap.get(ip1) + 1);
		
		
		

		// cacheMap.put("192.168.1.2", 10);
		// cacheMap.put("192.168.1.3", 10);

		

		// cacheMap.put("192.168.1.4", 10);
		// cacheMap.put("192.168.1.5", 10);

//		if (true) {
//			logger.info("KeySet: " + cacheMap.asMap().keySet());
//			logger.info(cacheMap.asMap().get(ip1));
//			Thread.currentThread().sleep(5000);
//			logger.info("KeySet: " + cacheMap.asMap().keySet());
//			Thread.currentThread().sleep(5000);
//			logger.info("KeySet: " + cacheMap.asMap().keySet());
//			// wait forever
//			synchronized (GuavaCaches.class) {
//				while (true) {
//					try {
//						GuavaCaches.class.wait();
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}

	}

}

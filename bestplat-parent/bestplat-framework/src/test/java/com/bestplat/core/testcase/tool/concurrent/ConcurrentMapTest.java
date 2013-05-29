package com.bestplat.core.testcase.tool.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.bestplat.framework.log.Logs;

/**
 * 
 * @author lujijiang
 * 
 */
public class ConcurrentMapTest {
	public static void main(String[] args) {
		final Map<Integer, String> map = new HashMap<Integer, String>();
		// final Map<Integer, String> map = Collections
		// .synchronizedMap(new HashMap<Integer, String>());
		new Thread() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Logs.info("size:{}", map.size());
				}
			}
		}.start();
		final Random random = new Random();
		ExecutorService es = Executors.newFixedThreadPool(1000);
		for (int i = 0; i < 10000000; i++) {
			es.execute(new Runnable() {
				public void run() {
					map.remove(random.nextInt(100));
					map.put(random.nextInt(100), UUID.randomUUID().toString());
				}
			});
		}
		es.shutdown();

	}
}

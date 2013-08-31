package com.bestplat.core.testcase.tool.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ThreadTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(10);
		final Future<Object> handler = executor.submit(new Callable<Object>() {
			public Object call() throws Exception {
				for (int i = 0; i < 100; i++) {
					System.out.println(i);
					TimeUnit.SECONDS.sleep(1);
				}
				return null;
			}
		});
		executor.submit(new Runnable() {
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				handler.cancel(true);
			}
		});
		executor.shutdown();
	}

}

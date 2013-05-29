package com.bestplat.core.testcase.tool.concurrent;

import java.util.Date;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TimerTest {
	static void doJob(int i) {
		System.out.println(i + ",run:" + new Date());
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
		}
		// throw new RuntimeException();
	}

	static class MyTask extends java.util.TimerTask {
		int i = 0;
		/**
		 * 声明一个线程池，最多开5个线程去执行任务
		 */
		private ExecutorService es = Executors.newFixedThreadPool(5);

		public void run() {
			final int k = i++;
			System.err.println(k);
			es.execute(new Runnable() {
				public void run() {
					doJob(k);
				}
			});
			// doJob();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Timer timer = new Timer();
		// timer.schedule(new MyTask(), 0, 1000);
		timer.scheduleAtFixedRate(new MyTask(), 0, 1000);
	}
}

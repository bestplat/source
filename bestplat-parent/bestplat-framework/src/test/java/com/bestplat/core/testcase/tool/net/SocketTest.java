package com.bestplat.core.testcase.tool.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.IOUtils;

public class SocketTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final int port = 10111;
		new Thread() {
			public void run() {
				ExecutorService es = Executors.newFixedThreadPool(100);
				try {
					ServerSocket ss = new ServerSocket(port);
					while (true) {
						final Socket s = ss.accept();
						abstract class SocketRunnable implements Runnable {
							Socket s;

							public SocketRunnable(Socket s) {
								this.s = s;
							}
						}
						es.execute(new SocketRunnable(s) {
							public void run() {
								try {
									System.out.println(IOUtils.toString(s
											.getInputStream()));
									IOUtils.write("this is a test message!",
											s.getOutputStream());

								} catch (Exception e) {
									e.printStackTrace();
								} finally {
									try {
										s.close();
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							}
						});
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
		ExecutorService es = Executors.newFixedThreadPool(50);
		for (int i = 0; i < 200; i++) {
			final int index = i;
			es.execute(new Runnable() {
				public void run() {
					try {
						Socket s = new Socket("localhost", port);
						try {
							IOUtils.write(index + ":"
									+ Thread.currentThread().toString(),
									s.getOutputStream());
						} finally {
							s.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

}

package com.sicongtang.module.penaltybox.guava;

import com.sicongtang.module.penaltybox.guava.PenaltyBox;

public class PenaltyBoxExpireTest {

	public static void main(String[] args) {
		final PenaltyBox box = PenaltyBox.getInstance();

		String[] ips = { "191.168.1.1", "191.168.1.2", "191.168.1.3", "191.168.1.4", "191.168.1.5" };
		for (final String ip : ips) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < 20; i++) {
						box.isPenalty(ip);

						try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					System.out.println("=====1=====");
					
					try {
						Thread.sleep(60000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					System.out.println("=====2=====");
					
					for (int i = 0; i < 1; i++) {
						box.isPenalty(ip);

						try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

				}
			});
			thread.start();
		}

	}
}

package me.sicongtang.module.penaltybox.guava;


public class PenaltyBoxExpireTest {

	public static void main(String[] args) {
		final PenaltyBox box = PenaltyBox.getInstance();

		String[] ips = { "191.168.1.1", "191.168.1.2", "191.168.1.3", "191.168.1.4", "191.168.1.5" };
		for(final String ip: ips) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					while(true){
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

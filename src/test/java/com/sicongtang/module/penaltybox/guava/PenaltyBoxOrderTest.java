package com.sicongtang.module.penaltybox.guava;

import com.sicongtang.module.penaltybox.guava.PenaltyBox;

public class PenaltyBoxOrderTest {

	public static void main(String[] args) {
		PenaltyBox box = PenaltyBox.getInstance();

		String[] ips = { "191.168.1.1", "191.168.1.2", "191.168.1.3", "191.168.1.4", "191.168.1.5", "191.168.1.2",
				"191.168.1.6", "191.168.1.1", "191.168.1.1" };

		for (String ip : ips) {
			boolean bool = box.isPenalty(ip);
			System.out.println(ip + ":" + bool);
		}
	}

}

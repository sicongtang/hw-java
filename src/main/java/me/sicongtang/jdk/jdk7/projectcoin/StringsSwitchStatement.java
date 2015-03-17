package me.sicongtang.jdk.jdk7.projectcoin;

public class StringsSwitchStatement {
	public int monthNameToDays(String s, int year) {
		switch (s) {
		case "April":
		case "June":
		case "September":
		case "November":
			return 30;
		case "January":
		case "February":
			return 40;
		default:
			return 0;
		}
	}
}

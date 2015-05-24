package com.sicongtang.jdk.tutorial.jmx.hello;

public interface HelloWorldMBean {
	void setGreeting(String greeting);

	String getGreeting();

	void printGreeting();
}

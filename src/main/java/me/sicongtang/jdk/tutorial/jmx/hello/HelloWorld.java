package me.sicongtang.jdk.tutorial.jmx.hello;

public class HelloWorld implements HelloWorldMBean {
	private String greeting = null;

	public HelloWorld() {
		this.greeting = "Hello World! I am a Standard MBean";
	}

	public HelloWorld(String greeting) {
		this.greeting = greeting;
	}

	@Override
	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}

	@Override
	public String getGreeting() {
		return greeting;
	}

	@Override
	public void printGreeting() {
		System.out.println(greeting);
	}

}

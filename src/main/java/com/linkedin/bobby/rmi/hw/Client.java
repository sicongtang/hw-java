package com.linkedin.bobby.rmi.hw;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
	
	public static void main(String[] args) throws Exception {
		Registry reg = LocateRegistry.getRegistry(2001);
		Hello hello = (Hello)reg.lookup("Hello");
		String result = hello.sayHello();
		System.out.println(">>>>>" + result);
	}
}

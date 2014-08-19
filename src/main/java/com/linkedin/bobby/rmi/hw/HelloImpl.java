package com.linkedin.bobby.rmi.hw;

import java.rmi.RemoteException;

public class HelloImpl implements Hello {
	
	@Override
	public String sayHello() throws RemoteException {
		System.out.println("HelloImpl.sayHello()");
		return "hello client";
	}
}

package com.linkedin.bobby.rmi.hw;

import java.rmi.AlreadyBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {

	public static void main(String[] args) {

		//In this policy, it contains the codeBase information.
		//Note that notice carefully about the format of codeBase(capitalize B) string.
		System.setProperty ("java.security.policy", "file:///Users/BobbyTang/Documents/Java/workspace/Multi-threadingDesign/src/main/resources/rmipolicy/rmihwserver.policy");
		
		System.out.println("RMI server started");

		// Create and install a security manager
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
			System.out.println("Security manager installed.");
		} else
			System.out.println("Security manager already exists.");

		Hello hello = new HelloImpl();
		// Hello2Impl hello2 = new Hello2Impl();
		int regPort = 1099;

		try {
			Hello stub = (Hello) UnicastRemoteObject.exportObject(hello, 10001);
			// Hello2 stub2 = (Hello2) UnicastRemoteObject.exportObject(hello2,
			// 10002);

			// LocateRegistry.createRegistry(regPort);

			Registry reg = LocateRegistry.getRegistry(regPort);
			reg.bind(Hello.class.getSimpleName(), stub); // or rebind
			// reg.bind(Hello2.class.getSimpleName(), stub2);

			System.out.println("RMI Server is ready...");

		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		}
	}
}

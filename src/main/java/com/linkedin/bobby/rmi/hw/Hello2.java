package com.linkedin.bobby.rmi.hw;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hello2 extends Remote{
	void sayHello2() throws RemoteException;// make sure this method must throw the remoteexception, otherwise can not export the stub
}

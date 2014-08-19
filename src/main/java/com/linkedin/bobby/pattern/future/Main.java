package com.linkedin.bobby.pattern.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Main {
	public static void main(String[] args) {
		//extension 1
		Host host = new Host();
		Data data = new Data();
		data.setDataName("data name 1");
		Future<Data> result = host.putRequest(data);
		try {
			Data dataRes = result.get();
			System.out.println("dataRes = " + dataRes);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		
		//extension 2
		Host2 host2 = new Host2();
		Account account = new Account();
		account.setEntityName("washaccount");
		account.setEntityStatus("pending");
		account.setSalesPerson("bobby");
		Future<Account> result2 =  host2.putRequest(account);
		try {
			Account accountRes = result2.get();
			System.out.println("accountRes = " + accountRes);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
	}
}

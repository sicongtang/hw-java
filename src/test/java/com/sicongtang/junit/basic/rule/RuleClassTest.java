package com.sicongtang.junit.basic.rule;

import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
//@SuiteClasses({A.class, B.class, C.class})
public class RuleClassTest {

	//public static Server myServer = new Server();

	@ClassRule
	public static ExternalResource resource = new ExternalResource() {
		@Override
		protected void before() throws Throwable {
			//myServer.connect();
		};

		@Override
		protected void after() {
			//myServer.disconnect();
		};
	};
}

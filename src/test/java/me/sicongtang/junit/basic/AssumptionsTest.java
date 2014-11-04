package me.sicongtang.junit.basic;

import static org.junit.Assert.assertThat;
import static org.junit.Assume.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.File;

import org.junit.Test;

public class AssumptionsTest {

	@Test
	public void filenameIncludesUsername() {
		System.out.println("AssumptionsTest.filenameIncludesUsername() 1 ");
		assumeThat(File.separatorChar, is('\\'));
		System.out.println("AssumptionsTest.filenameIncludesUsername() 2 ");
		//assertThat(new User("optimus").configFileName(), is("configfiles/optimus.cfg"));
	}

	@Test
	public void correctBehaviorWhenFilenameIsNull() {
		//assumeTrue(bugFixed("13356")); // bugFixed is not included in JUnit
		//assertThat(parse(null), is(new NullDocument()));
	}

}

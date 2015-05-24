package com.sicongtang.module.columnparser;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.sicongtang.module.columnparser.ISingleColumnParser;
import com.sicongtang.module.columnparser.SingleColumnTxtParser;
import com.sicongtang.module.columnparser.SingleColumnXlsParser;

/**
 * 
 * @author BobbyTang
 *
 */
public class SingeColumnParserTest {
	
	private File txtFile = new File("/Users/BobbyTang/Tmp/test.txt");
	private File xlsFile = new File("/Users/BobbyTang/Tmp/test2.xlsx");

	@Test
	public void testParserTxtCount() {
		ISingleColumnParser parser = new SingleColumnTxtParser();
		int count = parser.parseFileCount(txtFile);
		System.out.println(count);
		Assert.assertTrue(count == 11);
	}

	@Test
	public void testParserTxt() {
		ISingleColumnParser parser = new SingleColumnTxtParser();
		List<String> list  = parser.parseFile(txtFile, 10, 5);
		Assert.assertNotNull(list);
		System.out.println(list);
	}
	
	@Test
	public void testParserXlsCount() {
		ISingleColumnParser parser = new SingleColumnXlsParser();
		int count = parser.parseFileCount(xlsFile);
		System.out.println("VerifCodeParserTest.testParserXlsCount()");
		System.out.println(count);
	}
	
	@Test
	public void testParserXls() {
		ISingleColumnParser parser = new SingleColumnXlsParser();
		List<String> list = parser.parseFile(xlsFile, 10, 5);
		Assert.assertNotNull(list);
		System.out.println(list);
	}
	
}

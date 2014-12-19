package me.sicongtang.apache.poi.hssf.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;

public class XLSUtilsTest {

	private String fileName = "/Users/BobbyTang/Tmp/test.xlsx";

	@Test
	public void testIterateFirstColumn() {
		Sheet sheet = XLSUtils.loadSheetByFileName(fileName, 0);
		final List<String> cellValueList = new ArrayList<String>();
		XLSUtils.iterateFirstColumn(sheet, new XLSHandler() {

			@Override
			public void processCell(Cell c) {
				String cellValue = c.getStringCellValue();
				cellValueList.add(cellValue);
			}
		});

		for (String cellValue : cellValueList) {
			System.out.println(cellValue);
		}
		
	}

}

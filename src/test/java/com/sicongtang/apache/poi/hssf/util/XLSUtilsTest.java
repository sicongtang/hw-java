package com.sicongtang.apache.poi.hssf.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;

import com.sicongtang.apache.poi.hssf.util.SheetWrapper;
import com.sicongtang.apache.poi.hssf.util.XLSHandler;
import com.sicongtang.apache.poi.hssf.util.XLSUtils;

public class XLSUtilsTest {

	private String fileName = "/Users/BobbyTang/Tmp/test.xlsx";

	@Test
	public void testIterateFirstColumn() {
		Sheet rawSheet = XLSUtils.loadSheetByFileName(fileName, 0);
		SheetWrapper wrapper = new SheetWrapper(rawSheet);
		
		final List<String> cellValueList = new ArrayList<String>();
		XLSUtils.iteratorCellByRow(wrapper, new XLSHandler() {

			@Override
			public void processCell(Cell c) {
//				String cellValue = c.getStringCellValue();
//				cellValueList.add(cellValue);
			}
			
			@Override
			public void processCellByRow(Row row) {
				Iterator<Cell> iter = row.iterator();
				while(iter.hasNext()) {
					Cell c = iter.next();
					System.out.println(c.getStringCellValue());
				}
			}
		});

		for (String cellValue : cellValueList) {
			System.out.println(cellValue);
		}
		
	}

}

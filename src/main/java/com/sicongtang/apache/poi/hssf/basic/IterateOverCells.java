package com.sicongtang.apache.poi.hssf.basic;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.sicongtang.apache.poi.hssf.util.XLSUtils;

public class IterateOverCells {
	private static Logger logger = Logger.getLogger(IterateOverCells.class);
	private static int MY_MINIMUM_COLUMN_COUNT = 1;
	
	public void iterate(Sheet sheet) {
		// Decide which rows to process
		int rowStart = Math.min(15, sheet.getFirstRowNum());
		int rowEnd = Math.min(1400, sheet.getLastRowNum());

		logger.info("rowStart = " + rowStart);
		logger.info("rowEnd = " + rowEnd);
		
		for (int rowNum = rowStart; rowNum <= rowEnd; rowNum++) {
			logger.info("===============================");
			Row r = sheet.getRow(rowNum);

			int lastColumn = Math.max(r.getLastCellNum(), MY_MINIMUM_COLUMN_COUNT);

			for (int cn = 0; cn < lastColumn; cn++) {
				Cell c = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
				if (c == null) {
					// The spreadsheet is empty in this cell
				} else {
					// Do something useful with the cell's contents
					logger.info(c.getStringCellValue());
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Workbook book = XLSUtils.loadWorkbookByFileName("/tmp/test.xlsx");
		Sheet sheet = book.getSheetAt(0);
		IterateOverCells ioc = new IterateOverCells();
		ioc.iterate(sheet);
	}
}

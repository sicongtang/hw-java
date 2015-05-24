package com.sicongtang.apache.poi.hssf.basic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ReadingRewritingWorkbooks {
	private static Logger logger = Logger.getLogger(ReadingRewritingWorkbooks.class);
	public void readAndRewriteXLS(String fileName) throws Exception{
		int rowIdx = 0;
		//InputStream inp = new FileInputStream("workbook.xls"); //workbook.xlsx
		InputStream inp = this.getClass().getResourceAsStream(fileName);

		Workbook wb = WorkbookFactory.create(inp);
		Sheet sheet = wb.getSheetAt(0);
		Row row = sheet.getRow(rowIdx);
		Cell cell = row.getCell(0);
		
		logger.info(rowIdx + " >> " + cell.getStringCellValue());
		
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue("bbbbb");

		//Write the output to a file
		FileOutputStream fileOut = new FileOutputStream(fileName);
		wb.write(fileOut);
		fileOut.close();
	}

	public static void main(String[] args) throws Exception{
		ReadingRewritingWorkbooks rrw = new ReadingRewritingWorkbooks();
		rrw.readAndRewriteXLS("/tmp/test.xlsx");
	}
}

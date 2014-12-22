package me.sicongtang.apache.poi.hssf.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class XLSUtils {
	private static Logger logger = Logger.getLogger(XLSUtils.class);

	public static Workbook loadWorkbookByFileName(String fileName) {
		InputStream is = null;
		Workbook wb = null;
		try {
			is = new FileInputStream(fileName);
			wb = WorkbookFactory.create(is);
		} catch (Exception e) {
			logger.error("Encounter error while loading workbook from file", e);
		}

		return wb;
	}

	public static Workbook loadWorkbookByFile(File file) {
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(file);
		} catch (Exception e) {
			logger.error("Encounter error while loading workbook from file", e);
		}
		return wb;
	}

	public static Sheet loadSheetByFileName(String fileName, int sheetIdx) {
		Sheet sheet = null;

		Workbook book = loadWorkbookByFileName(fileName);
		if (book != null) {
			sheet = book.getSheetAt(sheetIdx);
		}

		return sheet;
	}

	public static Sheet loadSheetByFile(File file, int sheetIdx) {
		Sheet sheet = null;

		Workbook book = loadWorkbookByFile(file);
		if (book != null) {
			sheet = book.getSheetAt(sheetIdx);
		}

		return sheet;
	}

	public static void iterateAll(SheetWrapper wrapper, XLSHandler handler) {
		Sheet rawSheet = wrapper.getRawSheet();
		// Decide which rows to process
		int rowStart = rawSheet.getFirstRowNum();
		int rowEnd = rawSheet.getLastRowNum();

		for (int rowNum = rowStart; rowNum <= rowEnd; rowNum++) {
			Row r = rawSheet.getRow(rowNum);

			int lastColumn = r.getLastCellNum();

			for (int cn = 0; cn < lastColumn; cn++) {
				Cell c = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);

				handler.processCell(c);
			}
		}
	}

	public static void iteratorCellByRow(SheetWrapper wrapper, XLSHandler handler) {
		Sheet rawSheet = wrapper.getRawSheet();
		// Decide which rows to process
		int rowStart = wrapper.getRowStartIndex();
		int rowEnd = rowStart + wrapper.getRowLimit() ;

		for (int i = rowStart; i < rowEnd; i++) {
			Row r = rawSheet.getRow(i);

			handler.processCellByRow(r);
		}
	}

}

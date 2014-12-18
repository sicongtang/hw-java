package me.sicongtang.apache.poi.hssf.util;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.log4j.Logger;
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
}

package com.sicongtang.module.columnparser;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.sicongtang.apache.poi.hssf.util.SheetWrapper;
import com.sicongtang.apache.poi.hssf.util.XLSHandler;
import com.sicongtang.apache.poi.hssf.util.XLSUtils;

/**
 * 
 * @author BobbyTang
 *
 */
public class SingleColumnXlsParser implements ISingleColumnParser {
	private static Logger logger = Logger.getLogger(SingleColumnXlsParser.class);

	public int parseFileCount(File vcodeFile) {
		int count = 0;

		Sheet sheet = XLSUtils.loadSheetByFile(vcodeFile, 0);
		count = sheet.getLastRowNum() + 1;
		
		logger.info("The total line number of this file is " + count);

		return count;
	}

	public List<String> parseFile(File vcodeFile, int startIndex, int limit) {
		final List<String> stringList = new ArrayList<String>();

		// default as zero
		Sheet sheet = XLSUtils.loadSheetByFile(vcodeFile, 0);

		final SheetWrapper wrapper = new SheetWrapper(sheet);
		wrapper.setRowStartIndex(startIndex);
		wrapper.setRowLimit(limit);
		wrapper.setColumnStartIndex(0);
		wrapper.setColumnLimit(1);

		XLSUtils.iteratorCellByRow(wrapper, new XLSHandler() {

			@Override
			public void processCell(Cell c) {
				// No Need
			}

			@Override
			public void processCellByRow(Row row) {
				Iterator<Cell> iter = row.cellIterator();
				int columnCount = 1;
				while (iter.hasNext()) {
					Cell c = iter.next();
					int columnIndex = c.getColumnIndex();
					if (columnIndex >= wrapper.getColumnStartIndex() && columnCount <= wrapper.getColumnLimit()) {
						
						columnCount++;

						// Handle qualified cell
						String cellValue = null;
						
						if(c.getCellType() == Cell.CELL_TYPE_STRING) {
							cellValue = c.getStringCellValue();
						}else if(c.getCellType() == Cell.CELL_TYPE_NUMERIC) {
							double numCellValueDou = c.getNumericCellValue();
							int numCellValueInt = (int)numCellValueDou;
							
							cellValue = String.valueOf(numCellValueInt);
						}
						
						if (cellValue != null) {
							cellValue = cellValue.trim();
							if (!"".equals(cellValue)) {
								stringList.add(cellValue);
							}
						}

					} else if (columnIndex >= wrapper.getColumnStartIndex()) {
						break;
					}

				}
			}

		});

		return stringList;
	}
}

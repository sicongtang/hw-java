package me.sicongtang.apache.poi.hssf.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * 
 * @author BobbyTang
 *
 */
public interface XLSHandler {

	void processCell(Cell cell);
	void processCellByRow(Row row);

}
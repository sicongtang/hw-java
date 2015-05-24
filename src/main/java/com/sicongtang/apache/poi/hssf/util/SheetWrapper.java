package com.sicongtang.apache.poi.hssf.util;


import org.apache.poi.ss.usermodel.Sheet;

/**
 * 
 * @author BobbyTang
 *
 */
public class SheetWrapper {
	private Sheet rawSheet;
	private int rowStartIndex;
	private int rowLimit;
	private int columnStartIndex;
	private int columnLimit;
	
	public SheetWrapper(Sheet rawSheet) {
		this.rawSheet = rawSheet;
	}
	
	public int getRowStartIndex() {
		return rowStartIndex;
	}
	public Sheet getRawSheet() {
		return rawSheet;
	}
	public void setRawSheet(Sheet rawSheet) {
		this.rawSheet = rawSheet;
	}
	public void setRowStartIndex(int rowStartIndex) {
		this.rowStartIndex = rowStartIndex;
	}
	public int getRowLimit() {
		return rowLimit;
	}
	public void setRowLimit(int rowLimit) {
		this.rowLimit = rowLimit;
	}
	public int getColumnStartIndex() {
		return columnStartIndex;
	}
	public void setColumnStartIndex(int columnStartIndex) {
		this.columnStartIndex = columnStartIndex;
	}
	public int getColumnLimit() {
		return columnLimit;
	}
	public void setColumnLimit(int columnLimit) {
		this.columnLimit = columnLimit;
	}
	
}
package jexcel.excelextractor;

import java.io.IOException;

@SuppressWarnings("serial")
public class InvalidExcelSheetException extends IOException {
	public InvalidExcelSheetException() {
		super(); 
	}
	public InvalidExcelSheetException(String msg) {
		super(msg); 
	}
}
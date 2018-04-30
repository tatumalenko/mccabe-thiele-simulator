package jexcel.excelextractor;

import java.io.FileNotFoundException;

@SuppressWarnings("serial")
public class InvalidExcelFileException extends FileNotFoundException {
	public InvalidExcelFileException() {
		super(); 
	}
	public InvalidExcelFileException(String msg) {
		super(msg); 
	}
}

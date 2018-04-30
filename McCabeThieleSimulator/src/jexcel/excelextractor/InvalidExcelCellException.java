package jexcel.excelextractor;

import validation.InvalidArgumentException;

@SuppressWarnings("serial")
public class InvalidExcelCellException extends InvalidArgumentException {
	public InvalidExcelCellException() {
		super(); 
	}
	public InvalidExcelCellException(String msg) {
		super(msg); 
	}
}

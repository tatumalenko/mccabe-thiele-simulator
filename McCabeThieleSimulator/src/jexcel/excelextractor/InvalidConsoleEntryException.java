package jexcel.excelextractor;

import java.io.IOException;

@SuppressWarnings("serial")
public class InvalidConsoleEntryException extends IOException {
	public InvalidConsoleEntryException() {
		super(); 
	}
	public InvalidConsoleEntryException(String msg) {
		super(msg); 
	}
}

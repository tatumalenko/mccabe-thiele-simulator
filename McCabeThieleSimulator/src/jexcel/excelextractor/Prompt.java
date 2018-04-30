package jexcel.excelextractor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;

public interface Prompt { 
	public void prompt (String filepath, int sheetNum) throws BiffException, IOException, WriteException, FileNotFoundException;
	public int prompt (Scanner scan) throws BiffException, IOException, WriteException, FileNotFoundException;
}

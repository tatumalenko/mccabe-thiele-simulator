package bootstrap;

import java.text.DecimalFormat;
import process.unitoperation.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import java.util.Scanner;

import costing.CostingException;
import costing.ModuleCosting;
import jexcel.excelextractor.*;
import jexcel.excelwriter.ExcelWriter;

public class Main {

	public static void main(String [] args) throws BiffException, IOException, 
	WriteException, FileNotFoundException, CostingException {

		ExcelExtractor ee = new ExcelExtractor();
		boolean redoMain = false;
		String answer = "";
		Scanner userInput = new Scanner(System.in);
		int sheetNum;

		do {		
			readTextFromStream("Resources/Image4.txt");
			readTextFromStream("Resources/Title2.txt");

			try {
				sheetNum = ee.prompt(userInput);
				DistillationColumn column = new DistillationColumn(ee);
				OutputValues(userInput, column, sheetNum);
				System.out.println("\n-----------------------------------------------"
						+ "--------------------");
				System.out.println("\tANALYZE A NEW VENTURE?");
				System.out.println("-----------------------------------------------"
						+ "--------------------");
				System.out.println("Would you like to analyze another venture?");
				System.out.print("(Y)es or (N)o: ");
				answer = userInput.nextLine();
				System.out.println("-----------------------------------------------"
						+ "--------------------");
				if (answer.equalsIgnoreCase("y"))
					redoMain = true;
				else if (answer.equalsIgnoreCase("n")) {
					redoMain = false;
				} else redoMain = getValidEntry(userInput);
			} catch (FileNotFoundException e) {
				System.out.println("\n-----------------------------------------------"
						+ "--------------------");
				System.out.println("\tINVALID FILE!");
				System.out.println("-----------------------------------------------"
						+ "--------------------");
				System.out.println("That file was incorrectly entered or does "
						+ "not exist! \nWould you like to try again?");
				System.out.print("(Y)es or (N)o: ");
				answer = userInput.nextLine();
				System.out.println("-----------------------------------------------"
						+ "--------------------");
				if (answer.equalsIgnoreCase("y"))
					redoMain = true;
				else if (answer.equalsIgnoreCase("n")) {
					redoMain = false;
				} else redoMain = getValidEntry(userInput);
			} catch (InvalidExcelSheetException e) {
				System.out.println("\n-----------------------------------------------"
						+ "--------------------");
				System.out.println("\tSHEET NUMBER TOO LARGE!");
				System.out.println("-----------------------------------------------"
						+ "--------------------");
				System.out.println("Sheet number is larger than the total sheets "
						+ "this file contains!"
						+ "\nWould you like to return to "
						+ " the main menu?");
				System.out.print("(Y)es or (N)o: ");
				answer = userInput.nextLine();
				System.out.println("-----------------------------------------------"
						+ "--------------------");
				if (answer.equalsIgnoreCase("y"))
					redoMain = true;
				else if (answer.equalsIgnoreCase("n")) {
					redoMain = false;
				} else redoMain = getValidEntry(userInput);
			} catch (InvalidConsoleEntryException e) {
				System.out.println("\n-----------------------------------------------"
						+ "--------------------");
				System.out.println("\tINVALID INPUT!");
				System.out.println("-----------------------------------------------"
						+ "--------------------");
				System.out.println("Sheet number entered is invalid! Make sure "
						+ "you enter an integer value! ");
				System.out.println("\nWould you like to return to "
						+ "the main menu?");
				System.out.print("(Y)es or (N)o: ");
				answer = userInput.nextLine();
				System.out.println("-----------------------------------------------"
						+ "--------------------");
				if (answer.equalsIgnoreCase("y"))
					redoMain = true;
				else if (answer.equalsIgnoreCase("n")) {
					redoMain = false;
				} else redoMain = getValidEntry(userInput);
			} catch (InvalidExcelCellException e) {
				System.out.println("-----------------------------------------------"
						+ "--------------------");
				System.out.println("\tINVALID SHEET NUMBER");
				System.out.println("-----------------------------------------------"
						+ "--------------------");
				System.out.println("Sheet number entered appears to contain "
						+ "invalid data! \nPlease refer to the user manual to "
						+ "ensure proper placement of requirement data! \nWould "
						+ "you like to return to the main menu?");
				System.out.print("(Y)es or (N)o: ");
				answer = userInput.nextLine();
				if (answer.equalsIgnoreCase("y"))
					redoMain = true;
				else if (answer.equalsIgnoreCase("n")) {
					redoMain = false;
				} else redoMain = getValidEntry(userInput);
			} catch (BiffException  e) {
				System.out.println("\n-----------------------------------------------"
						+ "--------------------");
				System.out.println("\tBROKEN OR CORRUPT SHEET!");
				System.out.println("-----------------------------------------------"
						+ "--------------------");
				System.out.println("File appears to be broken or corrupt or "
						+ "sheet does not exist. Please try another file!");
				System.out.println("\nWould you like to return to "
						+ "the main menu?");
				System.out.print("(Y)es or (N)o: ");
				answer = userInput.nextLine();
				System.out.println("-----------------------------------------------"
						+ "--------------------");
				if (answer.equalsIgnoreCase("y"))
					redoMain = true;
				else if (answer.equalsIgnoreCase("n")) {
					redoMain = false;
				} else redoMain = getValidEntry(userInput);
			} catch (CostingException e) {
				System.out.println("\n-----------------------------------------------"
						+ "--------------------");
				System.out.println("\tINVALID VENTURE DATA FOR COSTING CORRELATIONS!");
				System.out.println("-----------------------------------------------"
						+ "--------------------");
				System.out.println(e.getMessage());
				System.out.println("\nWould " 
						+ "you like to return to the main menu?");
				System.out.print("(Y)es or (N)o: ");
				answer = userInput.nextLine();
				System.out.println("-----------------------------------------------"
						+ "--------------------");
				if (answer.equalsIgnoreCase("y"))
					redoMain = true;
				else if (answer.equalsIgnoreCase("n")) {
					redoMain = false;
				}
			}
		} while (redoMain);
		readTextFromStream("Resources/Sad.txt");
		readTextFromStream("Resources/Title.txt");
	}	

	private static boolean getValidEntry(Scanner userInput) {
		boolean validEntry = false;
		boolean redoMain = false;
		String answer = "";
		do {
			System.out.println("\n-----------------------------------------------"
					+ "--------------------");
			System.out.println("\tINVALID ENTRY!");
			System.out.println("-----------------------------------------------"
					+ "--------------------");
			System.out.println("Invalid entry! Would you like to return to the "
					+ "main menu?");
			System.out.print("(Y)es or (N)o: ");
			answer = userInput.nextLine();
			System.out.println("-----------------------------------------------"
					+ "--------------------");
			if (answer.equalsIgnoreCase("Y")) {
				validEntry = true;
				redoMain = true;
			}
			else if (answer.equalsIgnoreCase("N")) {
				validEntry = true;
				redoMain = false;
			} else {
				validEntry = false;
			}
		} while (!validEntry);
		return redoMain;
	}

	private static void OutputValues (Scanner scan, DistillationColumn column, 
			int sheetNum) throws CostingException, BiffException, WriteException, 
	FileNotFoundException, IOException {
		DecimalFormat currency = new DecimalFormat("$#,###.00");
		String answer = "";
		boolean doExport = false;

		System.out.println("\n-----------------------------------------------"
				+ "--------------------");
		System.out.println("\tSHORT VENTURE RESULT SUMMARY:");
		System.out.println("-----------------------------------------------"
				+ "--------------------");
		System.out.println("The number of trays is " + column.getNumberOfTrays());
		System.out.println("The total grassroots is " + currency.format((
				new ModuleCosting().calcGrassRootTotal(column))));
		System.out.println("The yearly profit based on production is " + 
				currency.format(column.calcProfit()) + "/year");
		System.out.println("The total profit is " + currency.format(
				column.calcProfit() - new ModuleCosting().calcGrassRootTotal(
						column))+" over a one year payback period.");
		System.out.println("-----------------------------------------------"
				+ "--------------------");

		System.out.println("\n-----------------------------------------------"
				+ "--------------------");
		System.out.println("\tEXPORT TO EXCEL?");
		System.out.println("-----------------------------------------------"
				+ "--------------------");
		System.out.println("Would you like to export these results to an Excel "
				+ "file? We'll even \nexport the flow rates and the various costs associated with "
				+ "both \nthe column and trays!");
		System.out.print("(Y)es or (N)o: ");
		answer = scan.nextLine();
		System.out.println("-----------------------------------------------"
				+ "--------------------");
		if (answer.equalsIgnoreCase("y"))
			doExport = true;
		else if (answer.equalsIgnoreCase("n")) {
			doExport = false;
		} else doExport = getValidEntry(scan);

		if (doExport)
			callExcelWriter(scan, column, sheetNum);
	}

	private static void callExcelWriter (Scanner scan, DistillationColumn col, 
			int sheetNum) throws BiffException, WriteException, 
	FileNotFoundException, IOException, CostingException {
		String filepath;
		File excelFile;

		try {
			System.out.println("\n-----------------------------------------------"
					+ "--------------------");
			System.out.println("\tCHOOSE EXCEL OUTPUT FILE");
			System.out.println("-----------------------------------------------"
					+ "--------------------");
			System.out.println("Please enter the name of the excel file you "
					+ "would like to output \nresults to and ensure extension .xls "
					+ "is included.");
			System.out.println("Also make sure that the file is located inside "
					+ "the current path \nas shown below or enter a "
					+ "file location with its full path.");
			System.out.println("\nCurrent path: " 
					+ System.getProperty("user.dir") + "/");
			System.out.print("File name: ");
			filepath = scan.nextLine();
			System.out.println("-----------------------------------------------"
					+ "--------------------");
			excelFile = new File(filepath);
		} catch (Exception e) {
			throw e;
		}
		if (excelFile.exists())
			ExcelWriter.outputToExcelFile(filepath, filepath, sheetNum, col);
		else {
			System.out.println("\n-----------------------------------------------"
					+ "--------------------");
			System.out.println("\tINVALID EXCEL FILE! BUT WE GOT YOU COVERED.");
			System.out.println("-----------------------------------------------"
					+ "--------------------");
			System.out.println("File entered does not appear to exist, "
					+ "creating new excel file \ninstead and "
					+ "appending results to first sheet!");
			ExcelWriter.outputToExcelFile(filepath, filepath, sheetNum, col);
			System.out.println("-----------------------------------------------"
					+ "--------------------");
		}
	}
	private static void readTextFromStream(String filepath) {
		try {
			InputStream is =
					Main.class.getClassLoader().getResourceAsStream(filepath);
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(is, "UTF-8"));

			String line;

			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

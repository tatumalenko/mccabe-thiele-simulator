package jexcel.excelextractor;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.NumberCell;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import validation.InvalidArgumentException;
import validation.OutOfRangeArgumentException;
import validation.Validate;

public class ExcelExtractor implements Prompt {

	// --------------------------------------------------
	// INSTANCE VARIABLES
	// --------------------------------------------------

	private double[] eqX;
	private double[] eqY;

	private double feedFlowRate;
	private double[] feedFraction;
	private double[] distillateFraction;
	private double[] bottomsFraction;
	private double[] heatCapacity;
	private double[] normalBP;
	private double latentHeat;
	private double costRaw;
	private double saleDistillate;
	private double saleBottoms;
	private double diameter;
	private double length;
	private double gaugePressure;
	private double refluxRatio;
	private double temperature;
	private String mocColumn;
	private String mocTrays;
	private String trayType;

	// --------------------------------------------------
	// CONSTRUCTOR(S)
	// --------------------------------------------------

	public ExcelExtractor() {

		this.feedFraction = new double[] { 0.0 };
		this.distillateFraction = new double[] { 0.0 };
		this.bottomsFraction = new double[] { 0.0 };
		this.heatCapacity = new double[] { 0.0 };
		this.normalBP = new double[] { 0.0 };

		this.feedFlowRate = 0;
		this.latentHeat = 0;
		this.costRaw = 0;
		this.saleDistillate = 0;
		this.saleBottoms = 0;
		this.diameter = 0;
		this.length = 0;
		this.gaugePressure = 0;
		this.refluxRatio = 0;
		this.temperature = 0;
		this.mocColumn = null;
		this.mocTrays = null;
		this.trayType = null;
	} // end of ExcelExtractor empty constructor

	public ExcelExtractor(double[] eqX, double feedFlowRate, double latentHeat, double costRaw, double saleDistillate,
			double saleBottoms, double diameter, double length, double gaugePressure, double refluxRatio,
			double temperature, String mocColumn, String mocTrays, String trayType) {
		setEqX(eqX);
		setEqY(eqY);

		setFeedFraction(this.feedFraction);
		setDistillateFraction(this.distillateFraction);
		setBottomsFraction(this.bottomsFraction);
		setHeatCapacity(this.heatCapacity);
		setNormalBP(this.normalBP);

		this.feedFlowRate = feedFlowRate;
		this.latentHeat = latentHeat;
		this.costRaw = costRaw;
		this.saleDistillate = saleDistillate;
		this.saleBottoms = saleBottoms;
		this.diameter = diameter;
		this.length = length;
		this.gaugePressure = gaugePressure;
		this.refluxRatio = refluxRatio;
		this.temperature = temperature;
		this.mocColumn = mocColumn;
		this.mocTrays = mocTrays;
		this.trayType = trayType;
	} // end of ExcelExtractor loaded constructor

	public ExcelExtractor(ExcelExtractor ee) {
		this(ee.eqX, ee.feedFlowRate, ee.latentHeat, ee.costRaw, ee.saleDistillate, ee.saleBottoms, ee.diameter,
				ee.length, ee.gaugePressure, ee.refluxRatio, ee.temperature, ee.mocColumn, ee.mocTrays, ee.trayType);
	} // end of ExcelExtractor copy constructor

	// --------------------------------------------------
	// ACCESSOR AND MUTATOR METHOD(S)
	// --------------------------------------------------

	// accessor and mutator methods for eqX[]
	public double[] getEqX() {
		double copyEqX[] = new double[this.eqX.length];
		for (int i = 0; i < this.eqX.length; i++)
			copyEqX[i] = this.eqX[i];
		return copyEqX;
	}

	public void setEqX(double[] eqX) {
		if (!Validate.isLessThan1(eqX) || !Validate.isNonNegative(eqX))
			throw new OutOfRangeArgumentException("Liquid equilibrium fraction", Arrays.toString(eqX), 0.0, 1.0);
		this.eqX = new double[this.eqX.length];
		for (int i = 0; i < this.eqX.length; i++)
			this.eqX[i] = eqX[i];
	}

	// accessor and mutator methods for eqY[]
	public double[] getEqY() {
		double copyEqY[] = new double[this.eqY.length];
		for (int i = 0; i < this.eqY.length; i++)
			copyEqY[i] = this.eqY[i];
		return copyEqY;
	}

	public void setEqY(double[] eqY) {
		if (!Validate.isLessThan1(eqY) || !Validate.isNonNegative(eqX))
			throw new OutOfRangeArgumentException("Vapor equilibrium fraction", Arrays.toString(eqY), 0.0, 1.0);
		this.eqY = new double[this.eqY.length];
		for (int i = 0; i < this.eqY.length; i++) {
			this.eqY[i] = eqY[i];
		}
	}

	// accessor and mutator for feedFlowRate
	public double getFeedFlowRate() {
		return this.feedFlowRate;
	}

	public void setFeedFlowRate(double feedFlowRate) {
		if (!Validate.isNonNegative(feedFlowRate))
			throw new InvalidArgumentException("feed flow rate", feedFlowRate, "non negative");
		this.feedFlowRate = feedFlowRate;
	}

	// accessor and mutator for feedFraction[]
	public double[] getFeedFraction() {
		double[] copyFeedFraction = new double[this.feedFraction.length];
		for (int i = 0; i < this.feedFraction.length; i++)
			copyFeedFraction[i] = this.feedFraction[i];
		return copyFeedFraction;
	}

	public void setFeedFraction(double[] feedFraction) {
		if (!Validate.isLessThan1(feedFraction) || !Validate.isNonNegative(feedFraction))
			throw new OutOfRangeArgumentException("feed fraction", Arrays.toString(feedFraction), 0.0, 1.0);
		this.feedFraction = new double[this.feedFraction.length];
		for (int i = 0; i < this.feedFraction.length; i++)
			this.feedFraction[i] = feedFraction[i];
	}

	// accessor and mutator methods for distillateFraction[]
	public double[] getDistillateFraction() {
		double[] copyDistillateFraction = new double[this.distillateFraction.length];
		for (int i = 0; i < this.distillateFraction.length; i++)
			copyDistillateFraction[i] = this.distillateFraction[i];
		return copyDistillateFraction;
	}

	public void setDistillateFraction(double[] distillateFraction) {
		if (!Validate.isLessThan1(distillateFraction) || !Validate.isNonNegative(distillateFraction))
			throw new OutOfRangeArgumentException("Distillate fraction", Arrays.toString(distillateFraction), 0.0, 1.0);
		this.distillateFraction = new double[this.distillateFraction.length];
		for (int i = 0; i < this.distillateFraction.length; i++)
			this.distillateFraction[i] = distillateFraction[i];
	}

	// accessor and mutator methods for bottomsFraction[]
	public double[] getBottomsFraction() {
		double[] copyBottomsFraction = new double[this.bottomsFraction.length];
		for (int i = 0; i < this.bottomsFraction.length; i++)
			copyBottomsFraction[i] = this.bottomsFraction[i];
		return copyBottomsFraction;
	}

	public void setBottomsFraction(double[] bottomsFraction) {
		if (!Validate.isLessThan1(bottomsFraction) || !Validate.isNonNegative(bottomsFraction))
			throw new InvalidExcelCellException("Bottoms fraction data out of bounds\nRange required: 0 to 1\n");
		this.bottomsFraction = new double[this.bottomsFraction.length];
		for (int i = 0; i < this.bottomsFraction.length; i++)
			this.bottomsFraction[i] = bottomsFraction[i];
	}

	// accessor and mutator methods for heatCapacity[]
	public double[] getHeatCapacity() {
		double[] copyHeatCapacity = new double[this.heatCapacity.length];
		for (int i = 0; i < this.heatCapacity.length; i++)
			copyHeatCapacity[i] = this.heatCapacity[i];
		return copyHeatCapacity;
	}

	public void setHeatCapacity(double[] heatCapacity) {
		if (!Validate.isNonNegative(heatCapacity))
			throw new InvalidArgumentException("heat capacity", Arrays.toString(heatCapacity), "non negative");
		this.heatCapacity = new double[this.heatCapacity.length];
		for (int i = 0; i < this.heatCapacity.length; i++)
			this.heatCapacity[i] = heatCapacity[i];
	}

	// accessor and mutator methods for normalBP[]
	public double[] getNormalBP() {
		double[] copyNormalBP = new double[this.normalBP.length];
		for (int i = 0; i < this.normalBP.length; i++)
			copyNormalBP[i] = this.normalBP[i];
		return copyNormalBP;
	}

	public void setNormalBP(double[] normalBP) {
		if (!Validate.isNonNegative(normalBP))
			throw new InvalidArgumentException("normal BP", Arrays.toString(normalBP), "non negative");
		this.normalBP = new double[this.normalBP.length];
		for (int i = 0; i < this.normalBP.length; i++)
			this.normalBP[i] = normalBP[i];
	}

	// accessor and mutator methods for latentHeat
	public double getLatentHeat() {
		return this.latentHeat;
	}

	public void setLatentHeat(double latentHeat) {
		if (!Validate.isNonNegative(latentHeat))
			throw new InvalidArgumentException("latent heat", latentHeat, "non negative");
		this.latentHeat = latentHeat;
	}

	// accessor and mutator methods for costRaw
	public double getCostRaw() {
		return this.costRaw;
	}

	public void setCostRaw(double costRaw) {
		if (!Validate.isNonNegative(costRaw))
			throw new InvalidArgumentException("feed cost", costRaw, "non negative");
		this.costRaw = costRaw;
	}

	// accessor and mutator methods for saleDistillate
	public double getSaleDistillate() {
		return this.saleDistillate;
	}

	public void setSaleDistillate(double saleDistillate) {
		if (!Validate.isNonNegative(saleDistillate))
			throw new InvalidArgumentException("distillate price", saleDistillate, "non negative");
		this.saleDistillate = saleDistillate;
	}

	// accessor and mutator methods for saleBottoms
	public double getSaleBottoms() {
		return this.saleBottoms;
	}

	public void setSaleBottoms(double saleBottoms) {
		if (!Validate.isNonNegative(saleBottoms))
			throw new InvalidArgumentException("bottoms price", saleBottoms, "non negative");
		this.saleBottoms = saleBottoms;
	}

	// accessor and mutator methods for diameter
	public double getDiameter() {
		return this.diameter;
	}

	public void setDiameter(double diameter) {
		if (!Validate.isNonNegative(diameter))
			throw new InvalidArgumentException("diameter", diameter, "non negative");
		this.diameter = diameter;
	}

	// accessor and mutator methods for length
	public double getLength() {
		return this.length;
	}

	public void setLength(double length) {
		if (!Validate.isNonNegative(length))
			throw new InvalidArgumentException("length", length, "non negative");
		this.length = length;
	}

	// accessor and mutator methods for gaugePressure
	public double getGaugePressure() {
		return this.gaugePressure;
	}

	public void setGaugePressure(double gaugePressure) {
		if (!Validate.isNonNegative(gaugePressure))
			throw new InvalidArgumentException("gauge pressure", gaugePressure, "non negative");
		this.gaugePressure = gaugePressure;
	}

	// accessor and mutator methods for refluxRatio
	public double getRefluxRatio() {
		return this.refluxRatio;
	}

	public void setRefluxRatio(double refluxRatio) {
		if (!Validate.isNonNegative(refluxRatio))
			throw new InvalidArgumentException("reflux ratio", refluxRatio, "non negative");
		this.refluxRatio = refluxRatio;
	}

	// accessor and mutator methods for temperature
	public double getTemperature() {
		return this.temperature;
	}

	public void setTemperature(double temperature) {
		if (!Validate.isNonNegative(temperature))
			throw new InvalidArgumentException("temperature", temperature, "non negative");
		this.temperature = temperature;
	}

	// accessor and mutator methods for mocColumn
	public String getMocColumn() {
		return this.mocColumn;
	}

	public void setMocColumn(String mocColumn) {
		this.mocColumn = mocColumn;
	}

	// accessor and mutator methods for mocTrays
	public String getMocTrays() {
		return this.mocTrays;
	}

	public void setMocTrays(String mocTrays) {
		this.mocTrays = mocTrays;
	}

	// accessor and mutator methods for trayType
	public String getTrayType() {
		return this.trayType;
	}

	public void setTrayType(String trayType) {
		this.trayType = trayType;
	}

	// --------------------------------------------------
	// EXCEL EXTRACTION METHODS
	// --------------------------------------------------
	// method to extract equilibrium data from excel
	public void extractEq(String workbook, int eqSheet)
			throws BiffException, IOException, WriteException, FileNotFoundException {
		Workbook venture = Workbook.getWorkbook(new File(workbook)); // instantiate
		// Workbook
		// object
		// venture
		Sheet vEq = venture.getSheet(eqSheet);

		this.eqX = new double[vEq.getColumn(5).length - 4];
		this.eqY = new double[vEq.getColumn(5).length - 4];

		// NumberCell nc = null;
		double[] eqX = new double[this.eqX.length];
		double[] eqY = new double[this.eqY.length];

		// check for data type and range for x and y equilibrium mole fraction
		// assign to instance variables eqX[] and eqY[] if acceptable
		for (int i = 0; i < vEq.getColumn(6).length - 4; i++) {

			Cell cell1 = vEq.getCell(5, i + 4);

			if (cell1.getType() != CellType.NUMBER)
				throw new InvalidExcelCellException(
						"Invalid data type for liquid equilibrium mole fraction\nRequired: numeric\n");
			eqX[i] = ((NumberCell) cell1).getValue();

			Cell cell2 = vEq.getCell(6, i + 4);

			if (cell2.getType() != CellType.NUMBER)
				throw new InvalidExcelCellException(
						"Invalid data type for vapour equilibrium mole fraction\nRequired: numeric\n");
			eqY[i] = ((NumberCell) cell2).getValue();

		}
		this.setEqX(eqX);
		this.setEqY(eqY);
	} // end of extractEq method

	// method to extract venture data from excel
	public void extractVentureData(String workbook, int propSheet)
			throws BiffException, IOException, WriteException, FileNotFoundException {

		Workbook venture = Workbook.getWorkbook(new File(workbook));
		Sheet vData = venture.getSheet(propSheet);

		this.feedFraction = new double[2];
		this.distillateFraction = new double[2];
		this.bottomsFraction = new double[2];
		this.heatCapacity = new double[2];
		this.normalBP = new double[2];

		Cell b3 = vData.getCell(1, 2);
		if (b3.getType() != CellType.NUMBER)
			throw new InvalidExcelCellException("Invalid data type for feed flow rate\nRequired: numeric\n");
		this.setFeedFlowRate(((NumberCell) b3).getValue());

		// check for data type and range for feed component mole fraction
		// assign to instance variable feedFraction[] if acceptable
		double[] feedFraction = new double[this.feedFraction.length];
		for (int i = 0; i < this.feedFraction.length; i++) {
			Cell cell = vData.getCell(i + 1, 5);

			if (cell.getType() != CellType.NUMBER)
				throw new InvalidExcelCellException(
						"Invalid data type for component feed fraction\nRequired: numeric\n");
			feedFraction[i] = ((NumberCell) cell).getValue();
		}
		this.setFeedFraction(feedFraction);

		// check for data type and range for distillate mole fraction
		// assign to instance variable distillateFraction[] if acceptable
		double[] distillateFraction = new double[this.distillateFraction.length];
		for (int i = 0; i < this.distillateFraction.length; i++) {
			Cell cell = vData.getCell(i + 1, 6);
			if (cell.getType() != CellType.NUMBER)
				throw new InvalidExcelCellException(
						"Invalid data type for component distillate fraction\nRequired: numeric\n");
			distillateFraction[i] = ((NumberCell) cell).getValue();
		}
		this.setDistillateFraction(distillateFraction);

		// check for data type and range for bottoms mole fraction
		// assign to instance variable bottomsFraction[] if acceptable
		double[] bottomsFraction = new double[this.bottomsFraction.length];
		for (int i = 0; i < this.bottomsFraction.length; i++) {
			Cell cell = vData.getCell(i + 1, 7);
			if (cell.getType() != CellType.NUMBER)
				throw new InvalidExcelCellException(
						"Invalid data type for component bottoms fraction\nRequired: numeric\n");
			bottomsFraction[i] = ((NumberCell) cell).getValue();
		}
		this.setBottomsFraction(bottomsFraction);

		// check for data type and range for component heat capacity
		// assign to instance variable heatCapacity[] if acceptable
		double[] heatCapacity = new double[this.heatCapacity.length];
		for (int i = 0; i < 2; i++) {
			Cell cell = vData.getCell(i + 1, 10);
			if (cell.getType() != CellType.NUMBER)
				throw new InvalidExcelCellException(
						"Invalid data type for component heat capacity\nRequired: numeric\n");
			heatCapacity[i] = ((NumberCell) cell).getValue();
		}
		this.setHeatCapacity(heatCapacity);

		// check for data type and range for component normal boiling point
		// assign to instance variable normalBP[] if acceptable
		double[] normalBP = new double[this.normalBP.length];
		for (int i = 0; i < this.normalBP.length; i++) {
			Cell cell = vData.getCell(i + 1, 11);
			if (cell.getType() != CellType.NUMBER)
				throw new InvalidExcelCellException(
						"Invalid data type for component normal boiling point\nRequired: numeric\n");
			normalBP[i] = ((NumberCell) cell).getValue();
		}
		this.setNormalBP(normalBP);

		// check for data type and range for feed latent heat
		// assign to instance variable latentHeat if acceptable
		Cell b12 = vData.getCell(1, 12);
		if (b12.getType() != CellType.NUMBER)
			throw new InvalidExcelCellException("Invalid data type for latent heat\nRequired: numeric\n");
		this.setLatentHeat(((NumberCell) b12).getValue());

		// check for data type and range for column diameter
		// assign to instance variable diameter if acceptable
		Cell b15 = vData.getCell(1, 14);
		if (b15.getType() != CellType.NUMBER)
			throw new InvalidExcelCellException("Invalid data type for column diameter\nRequired: numeric\n");
		this.setDiameter(((NumberCell) b15).getValue());

		// check for data type and range for column length
		// assign to instance variable length if acceptable
		Cell b16 = vData.getCell(1, 15);
		if (b16.getType() != CellType.NUMBER)
			throw new InvalidExcelCellException("Invalid data type for column length\nRequired: numeric\n");
		this.setLength(((NumberCell) b16).getValue());

		// check for data type and range for column pressure
		// assign to instance gaugePressure if acceptable
		Cell b17 = vData.getCell(1, 16);
		if (b17.getType() != CellType.NUMBER)
			throw new InvalidExcelCellException("Invalid data type for column pressure\nRequired: numeric\n");
		this.setGaugePressure(((NumberCell) b17).getValue());

		// check for data type and range for reflux ratio
		// assign to instance variable refluxRatio if acceptable
		Cell b18 = vData.getCell(1, 17);
		if (b18.getType() != CellType.NUMBER)
			throw new InvalidExcelCellException("Invalid data type for reflux ratio\nRequired: numeric\n");
		this.setRefluxRatio(((NumberCell) b18).getValue());

		// check for data type and range for column temperature
		// assign to instance variable temperature if acceptable
		Cell b19 = vData.getCell(1, 18);
		if (b19.getType() != CellType.NUMBER)
			throw new InvalidExcelCellException("Invalid data type for temperature\nRequired: numeric\n");
		this.setTemperature(((NumberCell) b19).getValue());

		// check for data type for column material of construction
		// assign to instance variable mocColumn if acceptable
		Cell b20 = vData.getCell(1, 19);
		if (b20.getType() != CellType.LABEL)
			throw new InvalidExcelCellException(
					"Invalid data type for column material of construction\nRequired: String\n");
		this.setMocColumn(b20.getContents());

		// check for data type for tray material of construction
		// assign to instance variable mocTrays if acceptable
		Cell b21 = vData.getCell(1, 20);
		if (b21.getType() != CellType.LABEL)
			throw new InvalidExcelCellException(
					"Invalid data type for tray material of construction\nRequired: String\n");
		this.setMocTrays(b21.getContents());

		// check for data type for tray type
		// assign to instance variable trayType if acceptable
		Cell b22 = vData.getCell(1, 21);
		if (b22.getType() != CellType.LABEL)
			throw new InvalidExcelCellException("Invalid data type for tray type\nRequired: String\n");
		this.setTrayType(b22.getContents());

		// check for data type and range for raw material cost
		// assign to instance variable costRaw if acceptable
		Cell b24 = vData.getCell(1, 23);
		if (b24.getType() != CellType.NUMBER)
			throw new InvalidExcelCellException("Invalid data type for raw material cost\nRequired: numeric\n");
		this.setCostRaw(((NumberCell) b24).getValue());

		// check for data type and range for distillate sale price
		// assign to instance variable saleDistillate if acceptable
		Cell b25 = vData.getCell(1, 24);
		if (b25.getType() != CellType.NUMBER)
			throw new InvalidExcelCellException("Invalid data type for distillate sale price\nRequired: numeric\n");
		this.setSaleDistillate((((NumberCell) b25).getValue()));

		// check for data type and range for bottoms sale price
		// assign to instance variable saleBottoms if acceptable
		Cell b26 = vData.getCell(1, 25);
		if (b26.getType() != CellType.NUMBER)
			throw new InvalidExcelCellException("Invalid data type for bottoms sale price\nRequired: numeric\n");
		this.setSaleBottoms((((NumberCell) b26).getValue()));

	} // end of extractVentureData method

	// --------------------------------------------------
	// PROMPT INTERFACE METHODS
	// --------------------------------------------------
	@Override
	public int prompt(Scanner in) throws BiffException, IOException, WriteException, FileNotFoundException {

		String filepath;
		String buffer;
		File excelFile;
		int sheetNum;

		try {
			System.out.print(
					"\nPlease enter the name of the excel file you would like to analyze \nincluding the .xls extension. ");
			System.out.print(
					"\nDo not have this .xls file open while running the code! May result\nin read/write conflicts! ");
			System.out.print(
					"\n\nAlso make sure that the file is located inside the current path\nas shown below, or enter the full");
			System.out.println("path where the file is located.");
			System.out.println("\nCurrent path: " + System.getProperty("user.dir") + "/");
			System.out.println("\n\t\t*** EXAMPLES ***");
			System.out.println("To use current path, enter: 'Venture.xls' (without quotes)");
			System.out.println("To use new path, enter: '/Users/username/Desktop/Venture.xls' (without quotes)");
			
			System.out.print("\nFile name: ");
			filepath = in.nextLine();
			excelFile = new File(filepath);
		} catch (Exception e) {
			throw e;
		}
		if (!excelFile.exists())
			throw new InvalidExcelFileException("File does not exist!"); // exception throw if the file DNE

		System.out.println("\nPlease enter the sheet index for the venture specific data");
		System.out.print("Sheet index: ");
		try {
			buffer = in.nextLine(); // User input to specify the excel sheet they would like analyzed
			if (!Validate.isType(buffer, "int"))
				throw new InvalidConsoleEntryException();
			sheetNum = Integer.parseInt(buffer);
			this.extractVentureData(filepath, sheetNum - 1);
			this.extractEq(filepath, sheetNum - 1);
		} catch (IndexOutOfBoundsException e) {
			throw new InvalidExcelSheetException();
		}
		return sheetNum;
	}

	@Override
	public void prompt(String filepath, int sheetNum) throws BiffException, IOException, WriteException {

		File excelFile = new File(filepath);

		if (excelFile.exists()) {
			Workbook venture = Workbook.getWorkbook(new File(filepath));

			int numSheets = venture.getNumberOfSheets();

			if (sheetNum <= numSheets) {
				this.extractVentureData(filepath, sheetNum - 1);
				this.extractEq(filepath, sheetNum - 1);
			} else
				throw new InvalidExcelSheetException();
		} else
			throw new InvalidExcelFileException();
	}
	// --------------------------------------------------
	// CLONE METHOD
	// --------------------------------------------------

	@Override
	public ExcelExtractor clone() {
		return new ExcelExtractor(this);
	}

} // end of excel extractor class

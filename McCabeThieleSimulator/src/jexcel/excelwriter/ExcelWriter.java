package jexcel.excelwriter;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import costing.CostingException;
import costing.ModuleCosting;

import java.io.FileNotFoundException;

import jxl.Workbook;

import jxl.read.biff.BiffException;

import jxl.write.Label;

import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import process.unitoperation.DistillationColumn;

public class ExcelWriter {

	public static void outputToExcelFile(String srcpath, String despath, int wkbVentureSheet, DistillationColumn col) throws BiffException, IOException, WriteException, FileNotFoundException, CostingException  {
		DecimalFormat currency = new DecimalFormat("$#,###.00");
		DecimalFormat df = new DecimalFormat("####.##");
		DecimalFormat idf = new DecimalFormat("####");
		Workbook srcwkb;
		WritableWorkbook deswkb;
		WritableSheet wks;
		try {
			srcwkb = Workbook.getWorkbook(new File(srcpath));
			deswkb = Workbook.createWorkbook(new File(despath), srcwkb);
			wks = deswkb.createSheet("Sheet " + (srcwkb.getNumberOfSheets()+1) + "-" 
					+ srcwkb.getSheetNames()[wkbVentureSheet-1] + " Results",srcwkb.getNumberOfSheets());
			wks.addCell(new Label(1, 1, "Sheet Results - " + srcwkb.getSheetNames()[wkbVentureSheet-1]));

			//------------------------------------------------------------------------------------
			//number of trays
			wks.addCell(new Label(1, 2, "Number of Trays"));
			wks.addCell(new Label(2, 3, idf.format(col.getNumberOfTrays())));

			//distillate flow rate
			wks.addCell(new Label(1, 4, "Distillate Molar Flow Rate"));
			wks.addCell(new Label(2, 5, df.format(col.calcFlowRates()[1])));
			wks.addCell(new Label(3, 5, "kmol/h"));

			//bottoms flow rate
			wks.addCell(new Label(1, 6, "Bottoms Molar Flow Rate"));
			wks.addCell(new Label(2, 7, df.format(col.calcFlowRates()[2])));
			wks.addCell(new Label(3, 7, "kmol/h"));

			//Purchase cost of column and trays
			wks.addCell(new Label(1, 9, "Purchase Cost"));
			wks.addCell(new Label(1, 10, "Column:"));
			wks.addCell(new Label(2, 10, currency.format(((new ModuleCosting()).calcCapitalPurchase(col)))));
			wks.addCell(new Label(1, 11, "Trays"));
			wks.addCell(new Label(2, 11, currency.format(((new ModuleCosting()).calcCapitalPurchase(col.getTrays())))));

			//Bare module cost of column and trays
			wks.addCell(new Label(1, 12, "Bare Module Cost"));
			wks.addCell(new Label(1, 13, "Column"));
			wks.addCell(new Label(2, 13, currency.format(((new ModuleCosting()).calcBareModule(col)[1]))));
			wks.addCell(new Label(1, 14, "Trays"));
			wks.addCell(new Label(2, 14, currency.format(((new ModuleCosting()).calcBareModule(col.getTrays())[1]))));

			//Total module cost
			wks.addCell(new Label(1, 16, "Total Module Cost"));
			wks.addCell(new Label(2, 17, currency.format(1.18*((new ModuleCosting()).calcBareModule(col)[1]+ (new ModuleCosting()).calcBareModule(col.getTrays())[1]))));

			//Grassroots
			wks.addCell(new Label(1, 19, "Grassroots Cost"));
			wks.addCell(new Label(2, 20, currency.format(((new ModuleCosting()).calcGrassRootTotal(col)))));

			//Profit 
			wks.addCell(new Label(1, 22, "The yearly profit based on production"));
			wks.addCell(new Label(2, 23, currency.format(col.calcProfit())));
			wks.addCell(new Label(3, 23, "/year"));

			//Profit based on one year PBP
			wks.addCell(new Label(1, 25, "The total profit based on a one year payback period"));
			wks.addCell(new Label(2, 26, currency.format(col.calcProfit() - (new ModuleCosting()).calcGrassRootTotal(col))));
			//------------------------------------------------------------------------------------

			deswkb.write(); 
			deswkb.close();
			srcwkb.close();
		} catch (FileNotFoundException e) {
			deswkb = Workbook.createWorkbook(new File(srcpath));
			wks = deswkb.createSheet("Output Results",0);
			wks.addCell(new Label(1, 1, "Results"));

			//------------------------------------------------------------------------------------
			//number of trays
			wks.addCell(new Label(1, 2, "Number of Trays"));
			wks.addCell(new Label(2, 3, idf.format(col.getNumberOfTrays())));

			//distillate flow rate
			wks.addCell(new Label(1, 4, "Distillate Molar Flow Rate"));
			wks.addCell(new Label(2, 5, df.format(col.calcFlowRates()[1])));
			wks.addCell(new Label(3, 5, "kmol/h"));

			//bottoms flow rate
			wks.addCell(new Label(1, 6, "Bottoms Molar Flow Rate"));
			wks.addCell(new Label(2, 7, df.format(col.calcFlowRates()[2])));
			wks.addCell(new Label(3, 7, "kmol/h"));

			//Purchase cost of column and trays
			wks.addCell(new Label(1, 9, "Purchase Cost"));
			wks.addCell(new Label(1, 10, "Column:"));
			wks.addCell(new Label(2, 10, currency.format(((new ModuleCosting()).calcCapitalPurchase(col)))));
			wks.addCell(new Label(1, 11, "Trays"));
			wks.addCell(new Label(2, 11, currency.format(((new ModuleCosting()).calcCapitalPurchase(col.getTrays())))));

			//Bare module cost of column and trays
			wks.addCell(new Label(1, 12, "Bare Module Cost"));
			wks.addCell(new Label(1, 13, "Column"));
			wks.addCell(new Label(2, 13, currency.format(((new ModuleCosting()).calcBareModule(col)[1]))));
			wks.addCell(new Label(1, 14, "Trays"));
			wks.addCell(new Label(2, 14, currency.format(((new ModuleCosting()).calcBareModule(col.getTrays())[1]))));

			//Total module cost
			wks.addCell(new Label(1, 16, "Total Module Cost"));
			wks.addCell(new Label(2, 17, currency.format(1.18*((new ModuleCosting()).calcBareModule(col)[1]+ (new ModuleCosting()).calcBareModule(col.getTrays())[1]))));

			//Grassroots
			wks.addCell(new Label(1, 19, "Grassroots Cost"));
			wks.addCell(new Label(2, 20, currency.format(((new ModuleCosting()).calcGrassRootTotal(col)))));

			//Profit 
			wks.addCell(new Label(1, 22, "The yearly profit based on production"));
			wks.addCell(new Label(2, 23, currency.format(col.calcProfit())));
			wks.addCell(new Label(3, 23, "/year"));

			//Profit based on one year PBP
			wks.addCell(new Label(1, 25, "The total profit based on a one year payback period"));
			wks.addCell(new Label(2, 26, currency.format(col.calcProfit() - (new ModuleCosting()).calcGrassRootTotal(col))));
			//------------------------------------------------------------------------------------

			deswkb.write(); 
			deswkb.close();
		}



	}
}

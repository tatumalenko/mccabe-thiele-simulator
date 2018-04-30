package process.unitoperation;

import jexcel.excelextractor.*;
import validation.*;

public class Tray {
	//--------------------------------------------------
	//	INSTANCE VARIABLES
	//--------------------------------------------------
	private String material;
	private double diameter; 

	//--------------------------------------------------
	//	CONSTRUCTOR(S)
	//--------------------------------------------------
	public Tray() {}// end of empty constructor

	public Tray(String material) {
		this.setMaterial(material);
	}// end of overloaded constructor

	public Tray(String material, double diameter) {
		this.material = material;
		this.diameter = diameter;
	}// end of second overloaded constructor

	public Tray(ExcelExtractor ee) {
		this.setMaterial(ee);
		this.setDiameter(ee);
	}// end of ExcelExtractor overloaded constructor

	public Tray(Tray tray) {
		this(tray.material, tray.diameter);
	}// end of copy constructor

	//--------------------------------------------------
	//	ACCESSORS AND MUTATORS
	//--------------------------------------------------
	// material accessor and mutator
	public String getMaterial() 				{return this.material;}
	public void setMaterial(String material) 	{this.material = material;}
	public void setMaterial(ExcelExtractor ee) 	{this.material = ee.getMocTrays();} 

	// diameter accessor and mutator
	public double getDiameter() 				{return this.diameter;}
	public void setDiameter(double diameter) {
		if (Validate.isNonNegative(diameter))
			this.diameter = diameter;
		else throw new InvalidArgumentException();
	}
	public void setDiameter(ExcelExtractor ee) 	{this.diameter = ee.getDiameter();}

	//--------------------------------------------------
	//	CLONE METHOD
	//--------------------------------------------------
	//clone method
	public Tray clone() {
		return new Tray(this);
	}
}// end of Tray class

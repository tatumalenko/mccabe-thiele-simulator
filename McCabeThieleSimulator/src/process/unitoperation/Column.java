package process.unitoperation;

import process.flow.*;
import validation.*;

public class Column {

	//--------------------------------------------------
	//	INSTANCE VARIABLES(S)
	//--------------------------------------------------
	private double diameter;
	private double length;
	private double gaugePressure;
	private String material;
	private Stream[] streams;

	//--------------------------------------------------
	//	CONSTRUCTOR(S)
	//--------------------------------------------------
	public Column() {
		this.diameter = 0.0;
		this.length = 0.0;
		this.gaugePressure = 0.0;
		this.material = "";
		this.streams = null;
	} //end of empty constructor

	public Column(double diameter, double length, double gaugePressure, String material, Stream[] streams) {
		this.setDiameter(diameter);
		this.setLength(length);
		this.setGaugePressure(gaugePressure);
		this.setMaterial(material);
		this.setStreams(streams);

	} //end of overloaded constructor

	public Column(Column column) {
		this(column.diameter, column.length, column.gaugePressure, column.material, column.streams);
	} //end of copy constructor

	//--------------------------------------------------
	//	ACCESSOR(S) and/or MUTATOR(S)
	//--------------------------------------------------
	// diameter accessor and mutator
	public double getDiameter() 						{ return this.diameter; }// end of diameter getter
	public void setDiameter(double diameter) { 
		if(Validate.isNonNegative(diameter)) {
			this.diameter = diameter;
		} else throw new InvalidArgumentException();
	}// end of diameter setter

	// length accessor and mutator
	public double getLength()							{ return this.length; }// end of length getter
	public void setLength(double length) { 
		if(Validate.isNonNegative(length)) {
			this.length = length;
		} else throw new InvalidArgumentException();
	}// end of length setter
	
	public double getGaugePressure() 					{ return this.gaugePressure; } //end of gaugePressure getter
	public void setGaugePressure(double gaugePressure){ 
		if(Validate.isNonNegative(gaugePressure)) {
			this.gaugePressure = gaugePressure;
		} else throw new InvalidArgumentException();
	}// end of gaugePressure setter
	
	// material accessor and mutator
	public String getMaterial()							{ return this.material; } //end of material getter
	public void setMaterial(String material)			{ this.material = material; } //end of material setter

	// Stream array accessor and mutator
	public Stream[] getStreams() {
		Stream[] copy = new Stream[this.streams.length];
		for (int i = 0; i < this.streams.length; i++) 
			copy[i] = this.streams[i].clone();
		return copy;
	} //end of streams getter
	public void setStreams(Stream[] streams) {
		this.streams = new Stream[streams.length];
		for (int i = 0; i < this.streams.length; i++)
			this.streams[i] = streams[i].clone();
	} //end of streams setter

	//--------------------------------------------------
	//	OTHER METHOD(S)
	//--------------------------------------------------
	// this method calculates the flow rate of the a specific stream in kg/hr
	public double[] calcFlowRates() {
		this.streams[2].setFlowRate(this.streams[0].getFlowRate()*(this.streams[0].getCompFraction()[0]
				- this.streams[1].getCompFraction()[0])/(this.streams[2].getCompFraction()[0]
						- this.streams[1].getCompFraction()[0]));
		this.streams[1].setFlowRate(this.streams[0].getFlowRate() - this.streams[2].getFlowRate());
		return new double[]{this.streams[0].getFlowRate(), this.streams[1].getFlowRate(), this.streams[2].getFlowRate()};
	} // end of calcFlowRates method

	// this method calculates the profit of the distillation column strictly based on feed cost, and distillate and bottoms price
	public double calcProfit() {
		return (this.streams[1].getUnitCost()*this.calcFlowRates()[1]
				+ this.streams[2].getUnitCost()*this.calcFlowRates()[2] 
						- this.streams[0].getUnitCost()*this.streams[0].getFlowRate())*24*365;
	} // end of calcProft method

	//--------------------------------------------------
	//	CLONE METHOD
	//--------------------------------------------------
	public Column clone() {
		return new Column(this);
	} // end of clone method

} //end of Column class

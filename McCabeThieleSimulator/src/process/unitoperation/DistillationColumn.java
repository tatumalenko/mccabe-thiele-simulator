package process.unitoperation;

import java.util.Arrays;

import jexcel.excelextractor.ExcelExtractor;
import numerical.analysis.*;
import numerical.interpolation.*;
import process.flow.*;
import validation.*;

public class DistillationColumn extends Column implements McCabeThiele{

	//--------------------------------------------------
	//	INSTANCE VARIABLES
	//--------------------------------------------------
	private double refluxRatio;
	private LinearFunction[] lines;
	private Tray[] trays;
	private int numberOfTrays;

	//--------------------------------------------------
	//	CONSTRUCTOR(S)
	//--------------------------------------------------
	public DistillationColumn() {
		super();
		this.refluxRatio = 0.0;
		this.numberOfTrays = 0;
		this.trays = null;
		this.lines = null;
	} //end of empty constructor

	public DistillationColumn(double diameter, double length, double gaugePressure, String material, Stream[] streams, double refluxRatio, LinearFunction[] lines, Tray[] trays, int numberOfTrays) {
		super(diameter, length, gaugePressure, material, streams);
		this.setRefluxRatio(refluxRatio);
		this.setTrays(trays);
		this.setLines(lines);
		this.setNumberOfTrays(numberOfTrays);
	} //end of default constructor

	public DistillationColumn(ExcelExtractor ee) {
		super(ee.getDiameter(), ee.getLength(), ee.getGaugePressure(), ee.getMocColumn(), new Stream[] {
				new FeedStream(ee), new DistillateStream(ee), new BottomsStream(ee)});
		this.setRefluxRatio(ee.getRefluxRatio());
		this.setLines(new LinearFunction[]{new EnrichingLine(this), new QLine(this), new StrippingLine()}); //cannot simultaneously call setLines and StrippingLine(this) initially since lines[0] and lines[0] are null and hence StrippingLine.calcSlope will crash
		this.setLines(new LinearFunction[]{new EnrichingLine(this), new QLine(this), new StrippingLine(this)});

		if (Arrays.asList("sieve tray","sieve","sieve trays").contains(ee.getTrayType().toLowerCase())) {
			this.trays = new SieveTray[this.calcNumberOfTrays(new PchipInterpolator().interpolate(ee.getEqX(),ee.getEqY()))];
			for (int i = 0; i < this.trays.length; i++)
				this.trays[i] = new SieveTray(ee);
		}
		this.setNumberOfTrays(this.calcNumberOfTrays(new PchipInterpolator().interpolate(ee.getEqX(),ee.getEqY())));
	} //end of overloaded constructor

	public DistillationColumn(DistillationColumn column) {
		this(column.getDiameter(), column.getLength(), column.getGaugePressure(), column.getMaterial(),
				column.getStreams(), column.getRefluxRatio(), column.getLines(), column.getTrays(), column.getNumberOfTrays());
	} //end of copy constructor

	//--------------------------------------------------
	//	ACCESSORS AND MUTATORS
	//--------------------------------------------------
	public double getRefluxRatio()						{ return this.refluxRatio; } //end of default getter
	public void setRefluxRatio(double refluxRatio) { 
		if (Validate.isNonNegative(refluxRatio))
			this.refluxRatio = refluxRatio;
		else throw new InvalidArgumentException();
		} //end of default setter

	public int getNumberOfTrays()						{ return this.numberOfTrays; } //end of default getter
	public void setNumberOfTrays(int numberOfTrays)	{ 
		if (Validate.isNonNegative(numberOfTrays))
			this.numberOfTrays = numberOfTrays; 
		else throw new InvalidArgumentException();
	} //end of default setter

	public Tray[] getTrays() {
		Tray[] copy = new Tray[this.trays.length];
		for (int i = 0; i < this.trays.length; i++)
			copy[i] = this.trays[i];
		return copy;
	} //end of default getter
	public void setTrays(Tray[] trays) {
		this.trays = new Tray[trays.length];
		for (int i = 0; i < this.trays.length; i++)
			this.trays[i] = trays[i].clone();
	} //end of default setter

	public LinearFunction[] getLines() {
		LinearFunction[] copy = new LinearFunction[this.lines.length];
		for (int i = 0; i < this.lines.length; i++) 
			copy[i] = this.lines[i].clone();
		return copy;
	} //end of default getter
	public void setLines(LinearFunction[] lines) {
		this.lines = new LinearFunction[lines.length];
		for (int i = 0; i < this.lines.length; i++)
			this.lines[i] = lines[i].clone();
	} //end of default setter

	//--------------------------------------------------
	//	OTHER METHOD(S)
	//--------------------------------------------------
	/** This method calculates the number of trays by using the McCabe-Thiele
	 * method by stepping between the binary equilibrium curve calculated
	 * using the spline function and the stripping and enriching lines.
	 */
	public int calcNumberOfTrays(Function equilibrium) {
		double x = getStreams()[2].getCompFraction()[0]; 
		double y = x;
		double yIntersection = LinearFunction.calcIntersection(this.getLines()[0],this.getLines()[1])[1]; 

		int nTrays = 0;

		do {
			y = equilibrium.calcY(x);

			if (y >= this.getStreams()[1].getCompFraction()[0]) {x = y;}
			else if (y >= yIntersection) 						{x = lines[0].calcX(y);} //EnrichingLine
			else 												{x = lines[2].calcX(y);} //StrippingLine

			nTrays++;
		} while (x <= this.getStreams()[1].getCompFraction()[0]);

		return nTrays;
	}

	//--------------------------------------------------
	//	CLONE METHOD
	//--------------------------------------------------
	public DistillationColumn clone() {
		return new DistillationColumn(this);
	} //end of clone method

} //end of DistillationColumn class

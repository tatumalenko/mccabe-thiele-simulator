package process.unitoperation;
import process.flow.Stream;

import numerical.analysis.LinearFunction;

// child class of LinearFunction and implements OperatingLine interface
public class QLine extends LinearFunction implements OperatingLine {

	//--------------------------------------------------
	//	CONSTRUCTOR(S)
	//--------------------------------------------------
	public QLine(DistillationColumn column) {
		super();
		setSlope(calcSlope(column));
		setIntercept(calcIntercept(column));
	}
	public QLine() {
		super();
	}
	public QLine(Stream[] streams) {
		super();

	}

	public QLine(QLine ol) {
		super(ol);
	}

	//--------------------------------------------------
	//	OTHER METHOD(S)
	//--------------------------------------------------
	// calculate slope method overriding calcSlope in OperatingLine
	public double calcSlope(DistillationColumn column) {
		return column.getStreams()[0].calcQ()/(column.getStreams()[0].calcQ() - 1);
	}
	// calculate intercept method overriding calcIntercept in OperatingLine
	public double calcIntercept(DistillationColumn column) {
		return -column.getStreams()[0].getCompFraction()[0]/(column.getStreams()[0].calcQ() - 1);
	}
	// calculate slope method overriding calcSlope in OperatingLine
	public double calcSlope(Stream[] streams) {
		return streams[0].calcQ()/(streams[0].calcQ() - 1);
	}
	// calculate intercept method overriding calcIntercept in OperatingLine
	public double calcIntercept(Stream[] streams) {
		return -streams[0].getCompFraction()[0]/(streams[0].calcQ() - 1);
	}

	//--------------------------------------------------
	//	CLONE METHOD
	//--------------------------------------------------
	public QLine clone() {
		return new QLine(this);
	}
}

package process.unitoperation;

import numerical.analysis.*;
import process.flow.Stream;

/**Child class of LinearFunction and implements OperatingLine interface
 * 
 */
public class StrippingLine extends LinearFunction implements OperatingLine {

	//--------------------------------------------------
	//	CONSTRUCTOR(S)
	//--------------------------------------------------
	public StrippingLine() {
		super();
	}
	public StrippingLine(StrippingLine ol) {
		super(ol);
	}
	public StrippingLine(DistillationColumn column) {
		super();
		setSlope(calcSlope(column));
		setIntercept(calcIntercept(column));
	}
	public StrippingLine(Stream[] streams, LinearFunction[] lines) {
		super();
		setSlope(calcSlope(streams, lines));
		setIntercept(calcIntercept(streams, lines));
	}
	//--------------------------------------------------
	//	OTHER METHOD(S)
	//--------------------------------------------------
	//column.getLines()[] = {EnrichingLine, QLine, StrippingLine}
	public double calcSlope(DistillationColumn column) {
		return (calcIntersection(column.getLines()[0],column.getLines()[1])[1] - column.getStreams()[2].getCompFraction()[0])
				/(calcIntersection(column.getLines()[0],column.getLines()[1])[0] - column.getStreams()[2].getCompFraction()[0]);
	}
	public double calcIntercept(DistillationColumn column) {
		return calcIntersection(column.getLines()[0],column.getLines()[1])[1] - 
				this.getSlope()*calcIntersection(column.getLines()[0],column.getLines()[1])[0];
	}
	public double calcSlope(Stream[] streams, LinearFunction[] lines) {
		return (calcIntersection(lines[0],lines[1])[1] - streams[2].getCompFraction()[0])
				/(calcIntersection(lines[0],lines[1])[0] - streams[2].getCompFraction()[0]);
	}
	public double calcIntercept(Stream[] streams, LinearFunction[] lines) {
		return calcIntersection(lines[0],lines[1])[1] - 
				this.getSlope()*calcIntersection(lines[0],lines[1])[0];
	}
	
	//--------------------------------------------------
	//	CLONE METHOD
	//--------------------------------------------------
	public StrippingLine clone() {
		return new StrippingLine(this);
	}
}

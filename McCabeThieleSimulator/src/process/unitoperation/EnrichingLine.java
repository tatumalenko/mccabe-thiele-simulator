package process.unitoperation;

import numerical.analysis.LinearFunction;
import process.flow.Stream;

/**EnrichingLine as a child of LinearFunction and implements the interface OperatingLine
 * 
 */
public class EnrichingLine extends LinearFunction implements OperatingLine {

	//--------------------------------------------------
	//	CONSTRUCTOR(S)
	//--------------------------------------------------
	public EnrichingLine() {
		super();
	} //end of empty
	
	public EnrichingLine(EnrichingLine ol) {
		super(ol);
	} //end default constructor
	
	public EnrichingLine(DistillationColumn column) {
		/**Check that column indeed has assigned streams 
	     * for calcSlope and calcIntercept else NULLERROR!
		 */
		super();
		setSlope(calcSlope(column));
		setIntercept(calcIntercept(column));
	} //end of overloaded constructor

	public EnrichingLine(Stream[] streams, double refluxRatio) {
		super();
		setSlope(calcSlope(refluxRatio));
		setIntercept(calcIntercept(streams, refluxRatio));
	} //end of overloaded constructor
	//--------------------------------------------------
	//	OTHER METHOD(S)
	//--------------------------------------------------
	// method for slope calculation
	public double calcSlope(DistillationColumn column) {
		return (column.getRefluxRatio()/(column.getRefluxRatio() + 1));
	}
	// method for y-intercept calculation
	public double calcIntercept(DistillationColumn column) {
		return column.getStreams()[1].getCompFraction()[0]/(column.getRefluxRatio() + 1);
	}
	// method for slope calculation
	public double calcSlope(double refluxRatio) {
		return (refluxRatio/(refluxRatio + 1));
	}
	// method for y-intercept calculation
	public double calcIntercept(Stream[] streams, double refluxRatio) {
		return streams[1].getCompFraction()[0]/(refluxRatio + 1);
	}

	//--------------------------------------------------
	//	CLONE METHOD
	//--------------------------------------------------
	public EnrichingLine clone() {
		return new EnrichingLine(this);
	}
}

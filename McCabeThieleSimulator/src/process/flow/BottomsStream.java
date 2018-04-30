package process.flow;

import jexcel.excelextractor.*;

public class BottomsStream extends Stream
{
	//--------------------------------------------------
	//	CONSTRUCTOR(S)
	//--------------------------------------------------
	public BottomsStream() {
		super();
	}
	public BottomsStream(double flowRate, double[] compFraction, double temperature, double latentHeat, double[] compBP, double[] compCP, double unitCost) {
		super(flowRate, compFraction, temperature, latentHeat, compBP, compCP, unitCost);
	} // end of BottomsStream empty constructor

	public BottomsStream(ExcelExtractor ee) {
		super(0.0, ee.getBottomsFraction(), ee.getTemperature(), ee.getLatentHeat(), ee.getNormalBP(), ee.getHeatCapacity(), ee.getSaleBottoms());
	} //end of BottomsStream constructor

	public BottomsStream(BottomsStream bs) {
		this(bs.getFlowRate(), bs.getCompFraction(), bs.getTemperature(), bs.getLatentHeat(), bs.getCompBP(), bs.getCompCP(), bs.getUnitCost());
	} // end of BottomsStream copy constructor

	//--------------------------------------------------
	//	OTHER METHOD(S)
	//--------------------------------------------------
	public double calcQ() {
		return -9999.0; // return dummy value, q is not required for bottoms stream
	} 

	//--------------------------------------------------
	//	CLONE METHOD
	//--------------------------------------------------
	public BottomsStream clone() {
		return new BottomsStream(this);
	}
}

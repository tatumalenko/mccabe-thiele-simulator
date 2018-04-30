package process.flow;

import jexcel.excelextractor.*;

public class DistillateStream extends Stream {
	//--------------------------------------------------
	//	CONSTRUCTOR(S)
	//--------------------------------------------------
	public DistillateStream() {
		super();
	}
	public DistillateStream(double flowRate, double[] compFraction, double temperature, double latentHeat, double[] compBP, double[] compCP, double unitCost) {
		super(flowRate, compFraction, temperature, latentHeat, compBP, compCP, unitCost);
	} // end of Distillate constructor

	public DistillateStream(ExcelExtractor ee) {
		super(0.0, ee.getDistillateFraction(), ee.getTemperature(), ee.getLatentHeat(), ee.getNormalBP(), ee.getHeatCapacity(), ee.getSaleDistillate());
	} //end of FeedStream constructor

	public DistillateStream(DistillateStream ds) {
		this(ds.getFlowRate(), ds.getCompFraction(), ds.getTemperature(), ds.getLatentHeat(), ds.getCompBP(), ds.getCompCP(), ds.getUnitCost());
	} // end of DistillateStream copy constructor

	//--------------------------------------------------
	//	OTHER METHOD(S)
	//--------------------------------------------------
	public double calcQ() {
		return -9999; // return dummy value, no q is needed for distillate stream
	}

	//--------------------------------------------------
	//	CLONE METHOD
	//--------------------------------------------------

	public DistillateStream clone() {
		return new DistillateStream(this);
	}
}

package process.flow;

import jexcel.excelextractor.*;

public class FeedStream extends Stream
{
	//--------------------------------------------------
	//	CONSTRUCTOR(S)
	//--------------------------------------------------
	public FeedStream() {
		super();
	} //end of empty constructor 
	
	public FeedStream(double flowRate, double[] compFraction, double temperature, double latentHeat, double[] compBP, double[] compCP, double unitCost) {
		super(flowRate, compFraction, temperature, latentHeat, compBP, compCP, unitCost);
	}
	
	public FeedStream(ExcelExtractor ee) {
		super(ee.getFeedFlowRate(), ee.getFeedFraction(), ee.getTemperature(), ee.getLatentHeat(), ee.getNormalBP(), ee.getHeatCapacity(), ee.getCostRaw());
	} //end of default constructor

	public FeedStream(FeedStream fs) {
		this(fs.getFlowRate(), fs.getCompFraction(), fs.getTemperature(), fs.getLatentHeat(), fs.getCompBP(), fs.getCompCP(), fs.getUnitCost());
	} // end of copy constructor

	//--------------------------------------------------
	//	CALC METHOD(S)
	//--------------------------------------------------
	public double calcQ() {
		return (getLatentHeat()+calcStreamCP()*(calcStreamBP()-getTemperature()))/getLatentHeat();
	}

	//--------------------------------------------------
	//	CLONE METHOD
	//--------------------------------------------------
	public FeedStream clone() {
		return new FeedStream(this);
	} //end of clone method
} // end of FeedStream class

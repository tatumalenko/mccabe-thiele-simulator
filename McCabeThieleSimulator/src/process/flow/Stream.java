package process.flow;

import validation.*;

public abstract class Stream {
	//--------------------------------------------------
	//	INSTANCE VARIABLE(S)
	//--------------------------------------------------
	protected double flowRate;
	protected double[] compFraction;
	protected double[] compBP;
	protected double[] compCP;
	protected double temperature;
	protected double latentHeat;
	protected double unitCost;

	//--------------------------------------------------
	//	CONSTRUCTOR(S)
	//--------------------------------------------------
	public Stream() {
		this.setFlowRate(0.0);
		this.setCompFraction(null);
		this.setCompCP(null);
		this.setCompBP(null);
		this.setTemperature(0.0);
		this.setLatentHeat(0.0);
		this.setUnitCost(0.0);
	} // end of empty constructor

	public Stream(double flowRate, double[] compFraction, double temperature, double latentHeat, double[] compBP, double[] compCP, double unitCost) {
		this.setFlowRate(flowRate);
		this.setCompFraction(compFraction);
		this.setCompBP(compBP);
		this.setCompCP(compCP);
		this.setTemperature(temperature);
		this.setLatentHeat(latentHeat);
		this.setUnitCost(unitCost);
	} // end of default constructor

	public Stream(Stream stream) {
		this(stream.flowRate, stream.compFraction, stream.temperature, stream.latentHeat, stream.compBP, stream.compCP, stream.unitCost);
	} //end of copy constructor

	//--------------------------------------------------
	//	ACCESSOR AND MUTATOR METHOD(S)
	//--------------------------------------------------
	public double getFlowRate() {
		return this.flowRate;
	} //end of default getter
	public void setFlowRate(double flowRate) throws InvalidArgumentException {
		if (Validate.isNonNegative(flowRate))
			this.flowRate = flowRate;
		else
			throw new InvalidArgumentException();
	} //end of default setter

	public double [] getCompFraction() {
		double[] copyCompFraction = new double[this.compFraction.length];
		for (int i=0; i<this.compFraction.length; i++)
			copyCompFraction[i] = this.compFraction[i];
		return copyCompFraction;
	} //end of default getter
	public void setCompFraction(double[] compFraction) {
		if (Validate.isNonNegative(compFraction) && Validate.isLessThan1(compFraction)) {
			this.compFraction = new double[compFraction.length];
			for (int i = 0; i < compFraction.length; i++)
				this.compFraction[i] = compFraction[i];
		} else throw new InvalidArgumentException();
	} //end of default setter

	public double getTemperature() {
		return this.temperature;
	} //end of default getter
	public void setTemperature(double temperature) {
		if (Validate.isNonNegative(temperature))
			this.temperature = temperature;
		else
			throw new InvalidArgumentException();
	} //end of default setter

	public double getLatentHeat() {
		return this.latentHeat;
	} //end of default getter
	public void setLatentHeat(double latentHeat) {
		if (Validate.isNonNegative(latentHeat))
			this.latentHeat = latentHeat;
		else
			throw new InvalidArgumentException();
	} //end of default setter

	public double[] getCompBP() {
		double[] copy = new double[this.compBP.length];
		for (int i=0; i<this.compBP.length; i++)
			copy[i] = this.compBP[i];
		return copy;
	} //end of default getter
	public void setCompBP(double[] compBP) {
		if (Validate.isNonNegative(compBP)) {
			this.compBP = new double[compBP.length];
			for (int i = 0; i < compBP.length; i++) 
				this.compBP[i] = compBP[i];
		} else throw new InvalidArgumentException();
	} //end of default setter

	public double[] getCompCP() {
		double[] copy = new double[this.compCP.length];
		for (int i=0; i<this.compCP.length; i++) {
			copy[i] = this.compCP[i];
		}
		return copy;

	} //end of default getter
	public void setCompCP(double[] compCP) {
		if (Validate.isNonNegative(compCP)) {
			this.compCP = new double[compCP.length];
			for (int i = 0; i < compCP.length; i++) 
				this.compCP[i] = compCP[i];
		} else throw new InvalidArgumentException();
	} //end of default setter

	public double getUnitCost() {
		return this.unitCost; 
	} //end of default getter
	public void setUnitCost(double unitCost) {
		if (Validate.isNonNegative(unitCost))
			this.unitCost = unitCost;
		else
			throw new InvalidArgumentException();
	} //end of default setter

	//--------------------------------------------------
	//	OTHER METHODS
	//--------------------------------------------------
	public double calcStreamBP() {
		double sum = 0.;
		for (int i = 0; i< this.compFraction.length; i++)
			sum = sum + this.compFraction[i]*this.compBP[i];
		return sum;
	}   
	public double calcStreamCP() {
		double sum = 0.;
		for (int i = 0; i< this.compFraction.length; i++)
			sum = sum + this.compFraction[i]*this.compCP[i];
		return sum;
	}
	public abstract double calcQ();

	//--------------------------------------------------
	//	CLONE METHOD
	//--------------------------------------------------
	public abstract Stream clone(); //end of abstract clone signature

} // end of abstract stream parent class


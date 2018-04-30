package process.unitoperation;

// interface implemented in EnrichingLine, QLine and StrippingLine
interface OperatingLine {

	//--------------------------------------------------
	//	OTHER METHOD(S)
	//--------------------------------------------------
	// method for slope and y-intercept calculation to be implemented
	double calcSlope(DistillationColumn column);
	double calcIntercept(DistillationColumn column);
}

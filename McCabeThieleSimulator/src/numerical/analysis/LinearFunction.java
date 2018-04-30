package numerical.analysis;

import validation.InvalidArgumentException;
import validation.Validate;

/** This class aims to implement the Function interface
 *  methods as well as incorporate various instance
 *  variables which are akin to linear functions 
 *  (such as slope and intercept) and implement additional
 *  methods such as calculating the intersection point of
 *  two LinearFunction objects.
 */
public class LinearFunction implements Function {

	//--------------------------------------------------
	//	INSTANCE VARIABLE(S)
	//--------------------------------------------------
	private double slope;		// every LinearFunction class must have a slope IV
	private double intercept;	// every LinearFunction class must have an intercept IV

	//--------------------------------------------------
	//	CONSTRUCTOR(S)
	//--------------------------------------------------
	// empty constructor
	public LinearFunction() {
		this.setSlope(0.);
		this.setIntercept(0.);
	}
	// standard constructor
	public LinearFunction(double slope, double intercept) {
		this.setSlope(slope);
		this.setIntercept(intercept);
	}
	// copy constructor
	public LinearFunction(LinearFunction lf) {
		this(lf.slope, lf.intercept); // uses standard constructor to assign IVs
	}

	//--------------------------------------------------
	//	ACCESSOR(S) AND MUTATOR(S) METHOD(S)
	//--------------------------------------------------
	// accessor and mutator methods for slope IV
	public double getSlope() {
		Validate.checkNotNull(this.slope);
		return this.slope;
	}
	public void setSlope(double slope) {
		Validate.checkNotNull(slope);
		this.slope = slope;
	}
	// accessor and mutator methods for intercept IV
	public double getIntercept() {
		Validate.checkNotNull(this.intercept);
		return this.intercept;
	}
	public void setIntercept(double intercept) {
		Validate.checkNotNull(intercept);
		this.intercept = intercept;
	}

	//--------------------------------------------------
	//	OTHER METHOD(S)
	//--------------------------------------------------
	// implemented calcY methods for double x and double[] x arguments
	public double calcY(double x) {
		Validate.checkNotNull(x);
		return this.getSlope()*x + this.getIntercept();
	}
	public double[] calcY(double[] x) {
		Validate.checkNotNull(x);
		double[] y = new double[x.length];
		for(int i = 0; i < x.length; i++)
			y[i] = this.calcY(x[i]);
		return y;
	}

	/* uniquely defined calcX method to solve for the x value of the 
	 * LinearFunction given a double y and double[] y 
	 */
	public double calcX(double y) {
		Validate.checkNotNull(y);
		if (this.getSlope() == 0.0)
			throw new ArithmeticException("Division by zero error! Cannot have slope value of zero.");
		return (y - this.getIntercept())/this.getSlope();
	}
	public double[] calcX(double[] y) {
		Validate.checkNotNull(y);
		double[] x = new double[y.length];
		for(int i = 0; i < y.length; i++)
			x[i] = this.calcX(y[i]);
		return x;
	}

	/* static method to calculate the intersection of two LinearFunction 
	 * instances and returning a vector [x, y] of this intersection point
	 */
	public static double[] calcIntersection(LinearFunction lf1, LinearFunction lf2) {
		Validate.checkNotNull(lf1);
		Validate.checkNotNull(lf2);
		double[] point = new double[2];
		if ((lf2.getSlope() - lf1.getSlope()) == 0)
				throw new InvalidArgumentException("Division by zero error! Cannot "
						+ "have denominator value of zero when calculating "
						+ "intersection point!.");
		point[0] = (lf1.getIntercept() - lf2.getIntercept())/
				(lf2.getSlope() - lf1.getSlope());
		point[1] = lf2.getSlope()*point[0] + lf2.getIntercept();
		return point;
	}

	//--------------------------------------------------
	//	CLONE METHOD
	//--------------------------------------------------
	@Override
	public LinearFunction clone() {
		return new LinearFunction(this);
	}
}



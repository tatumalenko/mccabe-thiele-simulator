package numerical.analysis;

import validation.EmptyArrayArgumentException;
import validation.*;

/** This class aims to extend the concept of a function
 *  through the Function interface to polynomial
 *  functions along with additional instance variables
 *  and methods.
 */
public class PolynomialFunction implements Function {

	//--------------------------------------------------
	//	INSTANCE VARIABLE(S)
	//--------------------------------------------------
	private final double coefficients[];

	//--------------------------------------------------
	//	CONSTRUCTOR(S)
	//--------------------------------------------------
	// empty constructor
	public PolynomialFunction() {
		this.coefficients = null;
	}
	// standard constructor
	public PolynomialFunction(double c[]) throws EmptyArrayArgumentException, NullArgumentException {
		Validate.checkNotNull(c);
		Validate.checkNotEmpty(c);
		int n = c.length;
		while ((n > 1) && (c[n - 1] == 0)) {
			--n;
		}
		this.coefficients = new double[n];
		System.arraycopy(c, 0, this.coefficients, 0, n);
	}
	// copy constructor
	public PolynomialFunction(PolynomialFunction pf) {
		this(pf.coefficients);
	}

	//--------------------------------------------------
	//	ACCESSOR(S) AND MUTATOR(S) METHOD(S)
	//--------------------------------------------------
	public int getDegree() {
		return this.coefficients.length - 1;
	}

	public double[] getCoefficients() {
		return this.coefficients;
	}

	//--------------------------------------------------
	//	OTHER METHOD(S)
	//--------------------------------------------------
	public double calcY(double x) {
		Validate.checkNotNull(x);
		return evaluate(coefficients, x);	
	}
	public double[] calcY(double[] x) {
		Validate.checkNotNull(x);
		Validate.checkNotEmpty(x);
		double[] copy = new double[x.length];
		for (int i = 0; i < x.length; i++)
			copy[i] = calcY(x[i]);
		return copy;
	}

	private static double evaluate(double[] coefficients, double argument) throws EmptyArrayArgumentException, NullArgumentException {
		Validate.checkNotNull(coefficients);
		Validate.checkNotEmpty(coefficients);
		int n = coefficients.length;
		double result = coefficients[n - 1];
		for (int j = n - 2; j >= 0; j--) {
			result = argument*result + coefficients[j];
		}
		return result;
	}

	@Override
	public String toString() {
		String s = "";
		boolean first = true;
		double[] coefficients = this.getCoefficients();

		for (int j = 0; j < coefficients.length; j++) {
			if (coefficients[j] == 0.0) 
				;
			else {
				if 		(coefficients[j] < 0.0)
					s += "- " + String.format("%.2f", Math.abs(coefficients[j]));
				else if (coefficients[j] > 0.0 && !first)
					s += "+ " + String.format("%.2f", Math.abs(coefficients[j]));
				else if (coefficients[j] > 0.0 && first)
					s += String.format("%.2f", Math.abs(coefficients[j]));

				if (j != 0 && j != 1)
					s += String.format(" x^%d ", j);
				else if (j == 1) 
					s += " x ";
				else
					s += " ";
				first = false;
			}
		}
		return s;
	}

	//--------------------------------------------------
	//	CLONE METHOD
	//--------------------------------------------------
	@Override
	public PolynomialFunction clone() {
		return new PolynomialFunction(this);
	}
}

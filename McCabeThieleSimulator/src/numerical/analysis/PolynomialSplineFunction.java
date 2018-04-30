package numerical.analysis;

import java.util.*;

import validation.InvalidArgumentException;
import validation.NullArgumentException;
import validation.OutOfRangeArgumentException;
import validation.Validate;

/** This class aims to extend the concept of a function
 *  through the Function interface to polynomial spline
 *  functions along with additional instance variables
 *  and methods. This class also uses composition by
 *  having an array of class type PolynomialFunction.
 */
public class PolynomialSplineFunction extends PolynomialFunction {

	//--------------------------------------------------
	//	INSTANCE VARIABLE(S)
	//--------------------------------------------------
	/* Spline segment interval delimiters (knots).
	 * Size is n + 1 for n segments.
	 */
	private final double knots[];
	/* The polynomial functions that make up the spline.  The first element
	 * determines the value of the spline over the first subinterval, the
	 * second over the second, etc. Spline function values are determined by
	 * evaluating these functions at (x - knot[i]) where i is the
	 * knot segment to which x belongs.
	 */
	private final PolynomialFunction polynomials[];
	/* Number of spline segments. It is equal to the number of polynomials and
	 * to the number of partition points - 1.
	 */
	private final int n;

	//--------------------------------------------------
	//	CONSTRUCTOR(S)
	//--------------------------------------------------
	/* Construct a polynomial spline function with the given segment delimiters
	 * and interpolating polynomials.
	 * The constructor copies both arrays and assigns the copies to the knots
	 * and polynomials properties, respectively.
	 */
	public PolynomialSplineFunction() {
		this.n = 0;
		this.knots = null;
		this.polynomials = new PolynomialFunction[1];
	}
	public PolynomialSplineFunction(double knots[], PolynomialFunction polynomials[]) 
		throws NullArgumentException, InvalidArgumentException {
		if (knots == null || polynomials == null) {
			throw new NullArgumentException();
		}
		if (knots.length < 2) {
			throw new InvalidArgumentException("Number of knots too small! You entered: " + knots.length + ". Required: >= 2");
		}
		if (knots.length - 1 != polynomials.length) {
			throw new InvalidArgumentException("Dimension mismatch! Number of knots - 1 should be equal to "
					+ "number of polynomials. You entered number of knots-1: " + (knots.length - 1) + "and number of polynomials:" + polynomials.length);
		}

		this.n = knots.length - 1;
		this.knots = new double[n + 1];
		System.arraycopy(knots, 0, this.knots, 0, n + 1);
		
		this.polynomials = new PolynomialFunction[n];
		System.arraycopy(polynomials, 0, this.polynomials, 0, n);
	}
	public PolynomialSplineFunction(PolynomialSplineFunction pf) {
		this(pf.knots, pf.polynomials);
	}

	//--------------------------------------------------
	//	ACCESSOR(S) AND MUTATOR(S) METHOD(S)
	//--------------------------------------------------
	/* Get the number of spline segments.
	 * It is also the number of polynomials and the number of knot points - 1.
	 */
	public int getN() {
		return n;
	}

	/* Get a copy of the interpolating polynomials array.
	 * It returns a fresh copy of the array. Changes made to the copy will
	 * not affect the polynomials property.
	 */
	public PolynomialFunction[] getPolynomials() {
		PolynomialFunction p[] = new PolynomialFunction[n];
		System.arraycopy(polynomials, 0, p, 0, n);
		return p;
	}

	/* Get an array copy of the knot points.
	 * It returns a fresh copy of the array. Changes made to the copy
	 * will not affect the knots property.
	 */
	public double[] getKnots() {
		double out[] = new double[n + 1];
		System.arraycopy(knots, 0, out, 0, n + 1);
		return out;
	}

	//--------------------------------------------------
	//	OTHER METHOD(S)
	//--------------------------------------------------
	/* Implement the calcY methods enforced from the Function interface
	 * for both double x and double[] x argument types
	 */
	public double calcY(double x) {
		Validate.checkNotNull(x);
		if (x < knots[0] || x > knots[n]) {
			throw new OutOfRangeArgumentException("calcY argument", x, knots[0], knots[n]);
		}
		int i = Arrays.binarySearch(knots, x);
		if (i < 0) {
			i = -i - 2;
		}
		// This will handle the case where x is the last knot value
		// There are only n-1 polynomials, so if x is the last knot
		// then we will use the last polynomial to calculate the value.
		if ( i >= this.polynomials.length ) {
			i--;
		}
		return this.polynomials[i].calcY(x - knots[i]);
	}
	public double[] calcY(double[] x) {
		Validate.checkNotNull(x);
		Validate.checkNotEmpty(x);
		double[] v = new double[x.length];
		for (int i = 0; i < v.length; i++) 
			v[i] = calcY(x[i]);
		return v;
	}

	// Indicates whether a point is within the interpolation range.
	public boolean isValidPoint(double x) {
		Validate.checkNotNull(x);
		if (x < knots[0] || x > knots[n]) 
			return false;
		else
			return true;
	}

	/* Implement overridden method toString to handle the use of 
	 * System.out.prinln() when argument is of this class type
	 */
	@Override
	public String toString() {  	
		String s = "";
		PolynomialFunction[] polynomials = this.getPolynomials();
		for (int i = 0; i < polynomials.length; i++) {
			s = s + "\n";
			s = s + polynomials[i].toString();
		}		
		return s;
	}
	// Alternative toString method which selectively converts only selected polynomial
	public String toString(int iPolynomial) {  	
		PolynomialFunction[] polynomials = this.getPolynomials();
		return polynomials[iPolynomial].toString();
	}

	//--------------------------------------------------
	//	CLONE METHOD
	//--------------------------------------------------
	@Override
	public PolynomialSplineFunction clone() {
		return new PolynomialSplineFunction(this);
	}
}

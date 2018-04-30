package numerical.interpolation;

import numerical.analysis.*;
import validation.EmptyArrayArgumentException;
import validation.InvalidArgumentException;
import validation.NullArgumentException;
import validation.Validate;

/** This class aims to implement the shape-preserving cubic
 *  Hermite interpolant heavily inspired by the MATLAB
 *  implemented function 'pchip' (for more information
 *  refer to: https://www.mathworks.com/moler/interp.pdf)
 *  enforced by the Interpolator  interface (through the 
 *  interpolate method) which will then return a
 *  PolynomialSplineFunction class type.
 */
public class PchipInterpolator implements Interpolator
{
	//--------------------------------------------------
	//	CONSTRUCTOR(S)
	//--------------------------------------------------
	// empty constructor
	public PchipInterpolator() {
	}
	// copy constructor
	public PchipInterpolator(PchipInterpolator pci) {
		this();
	}

	//--------------------------------------------------
	//	OTHER METHOD(S)
	//--------------------------------------------------
	// implementation of the interpolate method from Interpolator interface
	public PolynomialSplineFunction interpolate(double x[], double y[]) throws EmptyArrayArgumentException, NullArgumentException, InvalidArgumentException {
		Validate.checkNotNull(x);
		Validate.checkNotNull(y);
		Validate.checkNotEmpty(x);
		Validate.checkNotEmpty(y);
		
		if (x.length != y.length) {
			throw new InvalidArgumentException("Dimension mismatch. Both data arrays x and y must be the same length!");
		}

		if (x.length < 3) {
			throw new InvalidArgumentException("Minimum number of data points: 3. You entered: " + x.length);
		}
		// number of data points
		final int n = x.length;

		// number of intervals. The number of data points is n + 1
		final int numberOfIntervals = n - 1;

		// differences between knot points of x[] and y[]
		double[] h = new double[numberOfIntervals];
		double[] hy = new double[numberOfIntervals];
		h = Maths.diff(x);
		hy = Maths.diff(y);

		// element-by-element division of diff(x) and diff(y)
		final double[] del = new double[numberOfIntervals];
		for (int i = 0; i < numberOfIntervals; i++) {
			del[i] = hy[i]/h[i];
		}

		// compute slopes
		double[] slopes = pchipslopes(x, y, h, del);

		// compute piecewise Hermite interpolant to those values and slopes
		final double[] dzzdx = new double[numberOfIntervals];
		final double[] dzdxdx = new double[numberOfIntervals];
		for (int i = 0; i < numberOfIntervals; i++) {
			dzzdx[i] = (del[i] - slopes[i])/h[i];
		}
		for (int i = 0; i < numberOfIntervals; i++) {
			dzdxdx[i] = (slopes[i + 1] - del[i])/h[i];
		}

		//
		final PolynomialFunction polynomials[] = new PolynomialFunction[numberOfIntervals];
		final double coefficients[] = new double[4];
		for (int i = 0; i < numberOfIntervals; i++) {
			coefficients[0] = y[i];
			coefficients[1] = slopes[i];
			coefficients[2] = 2*dzzdx[i] - dzdxdx[i];
			coefficients[3] = (dzdxdx[i] - dzzdx[i])/h[i];
			polynomials[i] = new PolynomialFunction(coefficients);
		}

		return new PolynomialSplineFunction(x, polynomials);
	}

	// internal private method that is used exclusively in the interpolate method
	private double[] pchipslopes(double[] x, double[] y, double[] h, double[] del) {

		// start slope algorithm portion
		int n = x.length;
		int numberOfIntervals = n - 1;

		final double[] slopes = new double[n];

		// special case n = 2, use linear interpolation
		if (n == 2) { 
			for (int i = 0; i < numberOfIntervals; i++)
				slopes[i] = del[0];
			return slopes;
		}

		// find indexes that have del(k-1) and del(k) when they have the same sign
		int[] k = new int[numberOfIntervals - 1];
		int kindex = 0;
		for (int i = 0; i < k.length; i++) {
			if (del[i]*del[i+1] > 0) {
				k[kindex] = i;
				if (i != k.length - 1)
					kindex++;
			}
		}

		// redim k[] to length of kindex + 1
		int[] temp = new int[kindex + 1];
		System.arraycopy(k, 0, temp, 0, temp.length);
		k = temp;

		// compute slopes at interior points
		double[] hs = new double[k.length];
		double[] w1 = new double[k.length];
		double[] w2 = new double[k.length];
		double[] dmax = new double[k.length];
		double[] dmin = new double[k.length];
		for (int i : k) {
			hs[i] = h[i] + h[i + 1];
			w1[i] = (h[i] + hs[i])/(3*hs[i]);
			w2[i] = (h[i + 1] + hs[i])/(3*hs[i]);
			dmax[i] = Math.max(Math.abs(del[i]), Math.abs(del[i + 1]));
			dmin[i] = Math.min(Math.abs(del[i]), Math.abs(del[i + 1]));
			slopes[i + 1] = dmin[i]/(w1[i]*del[i]/dmax[i] + w2[i]*del[i + 1]/dmax[i]);
		}

		// compute slopes at end points
		slopes[0] = ((2*h[0]+h[1])*del[0] - h[0]*del[1])/(h[0]+h[1]);
		if (Math.signum(slopes[0]) != Math.signum(del[0])) 
			slopes[0] = 0.0; 
		else if ((Math.signum(del[0]) != Math.signum(del[1])) && (Math.abs(slopes[0]) > Math.abs(3*del[0])))
			slopes[0] = 3*del[0]; 

		slopes[n - 1] = ((2*h[n - 2] + h[n - 3])*del[n - 2] - h[n - 2]*del[n - 3])/(h[n - 2]+h[n - 3]);
		if ((Math.signum(slopes[n - 1]) != Math.signum(del[n - 2])))
			slopes[n - 1] = 0;
		else if ((Math.signum(del[n - 2]) != Math.signum(del[n - 3])) && (Math.abs(slopes[n - 1]) > Math.abs(3*del[n - 2])))
			slopes[n - 1] = 3*del[n - 2];

		return slopes;
	}

	//--------------------------------------------------
	//	CLONE METHOD
	//--------------------------------------------------
	@Override
	public PchipInterpolator clone() {
		return new PchipInterpolator(this);
	}
}

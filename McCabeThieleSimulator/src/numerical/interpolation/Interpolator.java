package numerical.interpolation;

import numerical.analysis.*;

/** This interface aims to define the signature required 
 *  for each interpolator classes which will implement their
 *  own interpolation algorithm through the interpolate
 *  method (SplineInterpolator and PchipInterpolator).
 */
public interface Interpolator 
{
	//--------------------------------------------------
	//	OTHER METHOD(S)
	//--------------------------------------------------
	/* Method that uses the x and y array vectors and implements the appropriate 
	 * interpolation algorithms in the implemented classes.
	 */
	Function interpolate(double xval[], double yval[]);
	//throws MathIllegalArgumentException, DimensionMismatchException;
}

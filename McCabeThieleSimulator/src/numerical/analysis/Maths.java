package numerical.analysis;

import validation.Validate;

/** This class implements various static methods useful  
 *  for the various numerical package classes
 */
public class Maths 
{
	//--------------------------------------------------
	//	OTHER METHOD(S)
	//--------------------------------------------------
	public static void toString(double[] x){
		String s = "[";
		for (int i = 0; i < x.length; i++) {
			if 		(i == 0)
				s += String.format("%.2f", x[i]);
			else
				s += String.format(", %.2f", x[i]);	
		}
		s += "]";
		System.out.println(s);
	}

	// sqrt(a^2 + b^2) without under/overflow
	public static double hypot(double a, double b) {
		double r;
		if (Math.abs(a) > Math.abs(b)) {
			r = b/a;
			r = Math.abs(a)*Math.sqrt(1+r*r);
		} else if (b != 0) {
			r = a/b;
			r = Math.abs(b)*Math.sqrt(1+r*r);
		} else {
			r = 0.0;
		}
		return r;
	}

	/* method to calculate the difference between adjacent
	 * elements within an array
	 */
	public static double[] diff(double[] x) {
		Validate.checkNotNull(x);
		Validate.checkNotEmpty(x);
		double[] y = new double[x.length];
		for (int i = 0; i < x.length - 1; i++){
			y[i] = x[i + 1] - x[i];
		}
		return y;
	}

	/* method to calculate the difference between adjacent
	 * elements within each column (difference between
	 * elements in the first dimension, i.e. between rows)
	 */
	public static double[][] diff(double[][] x) { // throws raggedArrayException{
		Validate.checkNotNull(x);
		int rows = x.length;
		int cols = x[0].length;
		double[][] y = new double[rows - 1][cols];
		for (int j = 0; j < cols; j++) {
			Validate.checkNotEmpty(x[j]);
			for (int i = 0; i < rows - 1; i++) {
				y[i][j] = x[i + 1][j] - x[i][j];
			}
		}
		return y;
	}

	/* method to calculate the product of each element
	 * within an array
	 */
	public static double prod(double[] x) { // throws arrayTooSmallException
		Validate.checkNotNull(x);
		Validate.checkNotEmpty(x);
		double y = x[0];
		for (int i = 1; i < x.length; i++) {
			y = y*x[i];
		}
		return y;
	}

	/* method to calculate the product of each element
	 * along both dimensions of the array
	 */
	public static double[] prod(double[][] x) { // throws raggedArrayException
		Validate.checkNotNull(x);
		int rows = x.length;
		int cols = x[0].length;
		double[] y = new double[cols];

		for (int j = 0; j < cols; j++) {
			Validate.checkNotEmpty(x[j]);
			y[j] = x[0][j];
		}
		for (int j = 0; j < cols; j++) {
			for (int i = 1; i < rows; i++) {
				y[j] = y[j]*x[i][j];
			}
		}
		return y;
	}
}
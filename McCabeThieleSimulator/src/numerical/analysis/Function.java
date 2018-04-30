package numerical.analysis;

/** This class aims to design the data structure 
 *  behind the concept of a Function as an interface
 *  simply because it only requires two methods and 
 *  has no instance variables.
 */
public interface Function {
	double calcY(double x);
	double[] calcY(double[] x);
}

package validation;

@SuppressWarnings("serial")
public class OutOfRangeArgumentException extends InvalidArgumentException {
	public OutOfRangeArgumentException() {
		super(); 
		this.printStackTrace();
	}
	public OutOfRangeArgumentException(String msg) {
		super(msg); 
		this.printStackTrace();
	}
	public OutOfRangeArgumentException(String variable, double wrong, double low, double high) {
		super("Wrong value of " + variable + " entered!\n" + "Entered value: " + wrong + ".\nPermitted range: " + low + " - " + high + " "); 
		this.printStackTrace();
	}
	public OutOfRangeArgumentException(String variable, String wrong, double low, double high) {
		super("Wrong value of " + variable + " entered!\n" + "Entered value: " + wrong + ".\nPermitted range: " + low + " - " + high + " "); 
		this.printStackTrace();
	}
}

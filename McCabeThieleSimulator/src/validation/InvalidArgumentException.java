package validation;

@SuppressWarnings("serial")
public class InvalidArgumentException extends IllegalArgumentException {
	public InvalidArgumentException() {
		super(); 
	}
	public InvalidArgumentException(String msg) {
		super(msg); 
	}
	public InvalidArgumentException(String variable, double wrong, String correct) {
		super("Wrong value of " + variable + " entered!\n" + "Entered value: " + wrong + ".\nPermitted: " + correct); 
	}
	public InvalidArgumentException(String variable, String wrong, String correct) {
		super("Wrong value of " + variable + " entered!\n" + "Entered value: " + wrong + ".\nPermitted: " + correct); 
	}
}

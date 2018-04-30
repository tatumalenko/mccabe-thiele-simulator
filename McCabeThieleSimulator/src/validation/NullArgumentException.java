package validation;

@SuppressWarnings("serial")
public class NullArgumentException extends InvalidArgumentException {
	public NullArgumentException() {
		super(); 
		this.printStackTrace();
	}
	public NullArgumentException(String msg) {
		super(msg); 
		this.printStackTrace();
	}
}

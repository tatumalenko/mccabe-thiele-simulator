package validation;

@SuppressWarnings("serial")
public class EmptyArrayArgumentException extends InvalidArgumentException {
	public EmptyArrayArgumentException() {
		super(); 
		this.printStackTrace();
	}
	public EmptyArrayArgumentException(String msg) {
		super(msg); 
		this.printStackTrace();
	}
}

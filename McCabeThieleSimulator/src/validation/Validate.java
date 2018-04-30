package validation;

public class Validate {
	
	public static void checkNotNull(Object value) {
		if (!isNotNull(value))
			throw new NullArgumentException();
	}
	
	public static void checkNotEmpty(double[] c) {
		if (c.length == 0)
			throw new EmptyArrayArgumentException();
	}
	
	public static boolean isNotNull(Object value) {
		if (value != null)
			return true;
		else return false;
	}
	public static boolean isType(String str, String type) {
		try {
			if (type.equalsIgnoreCase("float"))
				Float.parseFloat(str);
			else if (type.equalsIgnoreCase("int"))
				Integer.parseInt(str);
			else if (type.equalsIgnoreCase("double")) 
				Double.parseDouble(str);
		return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	public static boolean isNonNegative(double value) {
		if (value < 0)
			return false;
		else return true;
	}

	public static boolean isNonNegative(double[] value) {
		for (int i = 0; i < value.length; i++) {
			if (!isNonNegative(value[i]))
				return false;
		}
		return true;
	}

	public static boolean isLessThan1(double value) {
		if (value > 1)
			return false;
		else return true;
	}
	
	public static boolean isLessThan1(double[] value) {
		for (int i = 0; i < value.length; i++) {
			if (!isLessThan1(value[i]))
				return false;
		}
		return true;
	}
}

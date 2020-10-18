package Exceptions;
/** Exception to handle the invalid input of a date into the console
 * @author 19076935 */
@SuppressWarnings("serial")
public class InvalidDateFormatException extends Exception {
	/** Default constructor with standard message
	 * @author 19076935 */
	public InvalidDateFormatException() {
		super("Invalid date format entered");
	}

}

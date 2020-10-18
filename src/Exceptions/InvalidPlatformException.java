package Exceptions;
/** Exception to handle the invalid input of a platform into the console
 * @author 19076935 */
@SuppressWarnings("serial")
public class InvalidPlatformException extends Exception {
	/** Default constructor with standard message
	 * @author 19076935 */
	public InvalidPlatformException() {
		super("Invalid platform name");
	}
}

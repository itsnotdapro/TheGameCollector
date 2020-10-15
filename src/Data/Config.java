package Data;

import java.io.IOException;
import java.io.Serializable;

/** Interface that enables an object to be read and written to a file, to be saved over sessions
 * @author 19076935 */
public interface Config extends Serializable {
	public final String path = "data/file.bin";
	
	/** Method to write the object to a file
	 * @throws IOException if any IOException occurs
	 * @author 19076935 */
	public void write() throws IOException;
	
	/** Method to read the object from a file
	 * @throws IOException if any IOException occurs
	 * @author 19076935 */
	public void read() throws IOException;
	
	/** Method to clear an object's associated file
	 * @return If the data was successfully cleared
	 * @author 19076935 */
	public boolean clear();
}

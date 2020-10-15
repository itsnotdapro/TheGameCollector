package Data;

import java.io.IOException;
import java.io.Serializable;

/** Interface that enables an object to be read and written to a file, to be saved over sessions
 * @author 19076935 */
public interface Config extends Serializable {
	public final String path = "data/file.bin";
	
	public void write() throws IOException;
	public void read() throws IOException;
	public boolean clear();
}

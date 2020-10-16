package Exceptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** Class that logs an error to a file
 * @author 19076935 */
public class Log {
	/** Prints an error string to a file on disk
	 * @param e The string to write
	 * @author 19076935 */
	public Log(String e) {
		try {
			File log = new File("data/logs/" + new SimpleDateFormat("hh.mm.ss dd.MM.yyyy").format(new Date()) + ".txt");
			if (!log.exists()) { 
				log.createNewFile(); 
			}
			FileOutputStream stream = new FileOutputStream(log);
			for (char c : e.toCharArray()) {
				stream.write(c);
			}
			
			stream.close();
			System.err.println("Error encountered, see log for more details");
		} catch (IOException ex) { ex.printStackTrace(); }
	}

}

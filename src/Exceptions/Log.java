package Exceptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class Log {
	public Log(String e) {
		try {
			File log = new File("data/logs/" + new Date() + ".txt");
			if (!log.exists()) { log.createNewFile(); }
			FileOutputStream stream = new FileOutputStream(log);
			for (char c : e.toCharArray()) {
				stream.write(c);
			}
			
			stream.close();
			System.err.println("Error encountered, see log for more details");
		} catch (IOException ex) { }
	}

}

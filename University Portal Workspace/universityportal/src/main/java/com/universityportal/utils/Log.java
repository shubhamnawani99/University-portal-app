package com.universityportal.utils;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.universityportal.ui.UniversityPortalMain;

/**
 * Singleton class to log info to a file
 * 
 * @author Sunmeet
 */
public class Log {
	private static volatile Log instance = null;
	
	private Logger logger;
	
	private Log() {
		try {

			logger = Logger.getLogger(UniversityPortalMain.class.getName());
			FileHandler handler = new FileHandler("jdbc.log", true);
			logger.addHandler(handler);
			logger.setUseParentHandlers(false);
			SimpleFormatter formatter = new SimpleFormatter();
			handler.setFormatter(formatter);

		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return an instance of the logger
	 */
	public static Logger getLogger() {
		if(instance == null) {
			synchronized (Log.class) {
				if(instance == null)
					instance = new Log();
			}
		}
		return instance.logger;
	}
}


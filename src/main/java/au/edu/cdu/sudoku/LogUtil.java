package au.edu.cdu.sudoku;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * an util for log
 * 
 * 
 */
public class LogUtil {

	/**X
	 * get log according to class
	 * 
	 * @param clazz
	 *            , class
	 * @return logger
	 */
	public static Logger getLogger(@SuppressWarnings("rawtypes") Class clazz) {
		Logger log = LogManager.getLogger(clazz);
		log = setLog(log);
		return log;
	}

	/**
	 * set log
	 * 
	 * @param log
	 */
	private static Logger setLog(Logger log) {

		return log;
	}

	/**
	 * get root logger
	 * 
	 * @return logger
	 */
	public static Logger getLogger() {
		Logger log = LogManager.getRootLogger();
		log = setLog(log);
		return log;
	}

}

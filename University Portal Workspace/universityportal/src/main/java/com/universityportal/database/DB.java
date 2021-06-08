package com.universityportal.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

import com.universityportal.exceptions.DatabaseException;
import com.universityportal.exceptions.FileReadException;

/**
 * Singleton class to connect to database
 * 
 * @author Sunmeet
 */
public class DB {

	/**
	 * reference to the DB object
	 */
	private static volatile DB instance = null;
	private Connection connection = null;

	private static final String PROPERTIES_PATH = "src/main/resources/config.properties";
	private static final ResourceBundle bundle = ResourceBundle.getBundle("Bundle");

	/**
	 * Private constructor to restrict creation of objects
	 * @throws FileReadException config.properties file read exceptions
	 * @throws DatabaseException database connectivity exception
	 */
	private DB() throws FileReadException, DatabaseException {


		try(FileInputStream propsFileStream = new FileInputStream(PROPERTIES_PATH)) {
			Properties props = new Properties();
			
			props.load(propsFileStream);

			String url = props.getProperty("db.url");
			String user = props.getProperty("db.user");
			String password = props.getProperty("db.password");

			connect(url, user, password);

		} catch (IOException e) {
			throw new FileReadException(bundle.getString("file_read_exception")
					+ PROPERTIES_PATH);
		}
	}

	
	/**
	 * Creates a connection to the database
	 * @param url database URL
	 * @param user database user name
	 * @param password database user password
	 * @throws DatabaseException database connectivity exception
	 */
	private void connect(String url, String user, String password) throws DatabaseException {
		try {
			this.connection = DriverManager.getConnection(url, user, password);

		} catch (SQLException e) {
			throw new DatabaseException(bundle.getString("sql_connection_exception"), e.getErrorCode());
		}
	}

	
	/**
	 * handles the creation of only a single object across all references
	 * @return DB object
	 * @throws FileReadException config.properties file read exceptions
	 * @throws DatabaseException database connectivity exception
	 */
	public static DB getInstance() throws FileReadException, DatabaseException {
		if (instance == null) {
			synchronized (DB.class) {
				if (instance == null)
					instance = new DB();
			}
		}
		return instance;
	}

	/**
	 * @return the Connection object for communicating with the database
	 */
	public Connection getConnection() {
		return connection;
	}
}

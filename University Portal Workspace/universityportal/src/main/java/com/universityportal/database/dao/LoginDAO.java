package com.universityportal.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;

import com.universityportal.bean.Admin;
import com.universityportal.bean.Student;
import com.universityportal.database.DB;
import com.universityportal.exceptions.DatabaseException;
import com.universityportal.exceptions.FileReadException;
import com.universityportal.utils.Log;

/**
 * DAO class for administrator and student login
 * 
 * @author Sunmeet
 */
public class LoginDAO {

	public final Connection dbConn;
	private static final ResourceBundle bundle = ResourceBundle.getBundle("Bundle");

	/**
	 * Public constructor to create a studentDAO object and connect to database
	 * 
	 * @throws FileReadException config.properties file read exceptions
	 * @throws DatabaseException database connectivity exception
	 */
	public LoginDAO() throws FileReadException, DatabaseException {
		dbConn = DB.getInstance().getConnection();
	}

	/**
	 * Validates student credential from the database.
	 * 
	 * @param username Student ID
	 * @param password password of the student
	 * @return a valid student object if credentials are valid or else null
	 */
	public Student validateStudentCredentials(String username, String password) {
		String validateStudentquery = "SELECT student_id, branch_id, first_name, last_name, phone_no, parent_no, blood_grp FROM student WHERE student_id=? AND password=?;";
		try (PreparedStatement validateStudentStmt = dbConn.prepareStatement(validateStudentquery)) {
			validateStudentStmt.setString(1, username);
			validateStudentStmt.setString(2, password);

			ResultSet rs = validateStudentStmt.executeQuery();

			if (rs.isBeforeFirst()) {
				String log = String.format(bundle.getString("student_login_success"), username);
				Log.getLogger().log(Level.INFO, log);
				return parseStudent(rs);
			} else {
				logInvalidCredentials(username);
				return null;
			}
		} catch (SQLException e) {
			logInvalidCredentials(username);
			return null;
		}
	}

	/**
	 * Parses a result set into student object
	 * 
	 * @param rs ResultSet to be parsed
	 * @return student object
	 * @throws SQLException if result set is invalid or column label doesn't match
	 */
	private Student parseStudent(ResultSet rs) throws SQLException {
		Student s = new Student();
		rs.next();
		s.setId(rs.getString("student_id"));
		s.setBranchId(rs.getInt("branch_id"));
		s.setFirstName(rs.getString("first_name"));
		s.setLastName(rs.getString("last_name"));
		s.setPhoneNo(rs.getString("phone_no"));
		s.setParentNo(rs.getString("parent_no"));
		s.setBloodGrp(rs.getString("blood_grp"));
		return s;
	}
	
	/**
	 * Validates administrator credential from the database.
	 * 
	 * @param username Administrator id
	 * @param password password of the administrator
	 * @return a valid administrator object if credentials are valid or else null
	 */
	public Admin validateAdminCredentials(String username, String password) {
		String validateStudentquery = "SELECT admin_id FROM admin WHERE admin_id=? AND password=?;";
		try (PreparedStatement validateStudentStmt = dbConn.prepareStatement(validateStudentquery)) {
			validateStudentStmt.setString(1, username);
			validateStudentStmt.setString(2, password);

			ResultSet rs = validateStudentStmt.executeQuery();

			if (rs.isBeforeFirst()) {
				String log = String.format(bundle.getString("admin_login_success"), username);
				Log.getLogger().log(Level.INFO, log);
				return parseAdmin(rs);
			} else {
				logInvalidCredentials(username);
				return null;
			}
		} catch (SQLException e) {
			logInvalidCredentials(username);
			return null;
		}
	}

	/**
	 * Parses a result set into administrator object
	 * 
	 * @param rs ResultSet to be parsed
	 * @return admin object
	 * @throws SQLException if result set is invalid or column label doesn't match
	 */
	private Admin parseAdmin(ResultSet rs) throws SQLException {
		Admin a = new Admin();
		rs.next();
		a.setId(rs.getString("admin_id"));
		return a;
	}
	
	/**
	 * Logs invalid login attempts with username.
	 * @param username id of the user
	 */
	private void logInvalidCredentials(String username) {
		String log = String.format(bundle.getString("invalid_credentials_log"), username);
		Log.getLogger().log(Level.SEVERE, log);
	}
}

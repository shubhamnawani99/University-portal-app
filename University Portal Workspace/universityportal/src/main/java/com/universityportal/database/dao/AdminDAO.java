/**
 * 
 */
package com.universityportal.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;

import com.universityportal.bean.Marks;
import com.universityportal.database.DB;
import com.universityportal.exceptions.DatabaseException;
import com.universityportal.exceptions.FileReadException;
import com.universityportal.utils.Log;

/**
 * @author whoami, Shubham Nawani
 *
 */
public class AdminDAO {

	private Connection conn;
	private PreparedStatement statement;
	private static final ResourceBundle bundle = ResourceBundle.getBundle("Bundle");

	public boolean insertRecord(ArrayList<Marks> list) throws DatabaseException, FileReadException {

		try {
			conn = DB.getInstance().getConnection();
			int confirmInsertionInMarks = 0;
			int confirmInsertionInMarksEnrollment = 0;
			// to insert into marks table
			for (Marks obj : list) {

				String query = "Insert into marks(marks_id,semester,marks,subject_id) values (?,?,?,?);";

				statement = conn.prepareStatement(query);
				statement.setInt(1, obj.getMarkID());
				statement.setInt(2, obj.getSems());
				statement.setInt(3, obj.getMarks());
				statement.setString(4, obj.getSubjectID());
				confirmInsertionInMarks = statement.executeUpdate();

			}

			// to insert into marks enrollment
			for (Marks obj : list) {

				String sql = "Insert into marks_enrollment values (?, ?);";
				statement = conn.prepareStatement(sql);
				statement.setInt(1, obj.getMarkID());
				statement.setString(2, obj.getStudentID());
				confirmInsertionInMarksEnrollment = statement.executeUpdate();

			}
			if ((confirmInsertionInMarks > 0) && (confirmInsertionInMarksEnrollment > 0)) {
				return true;
			}

		} catch (SQLException e) {
			Log.getLogger().log(Level.SEVERE, e.getMessage());
			System.out.println(e.getMessage());
		}
		return false;

	}

}

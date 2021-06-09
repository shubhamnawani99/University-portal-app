/**
 * 
 */
package com.universityportal.database.dao;

import java.io.IOException;
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
 * @author whoami
 *
 */
public class AdminDAO {
	
	private Connection conn;
	private PreparedStatement statement;
	private static final ResourceBundle bundle = ResourceBundle.getBundle("Bundle");
	
	public boolean insertRecord(ArrayList<Marks> list) throws DatabaseException, FileReadException {

		try {
			conn = DB.getInstance().getConnection();
			int confirm = 0;
			int confirm1=0;
			// to insert into marks table
			for(Marks obj: list) {
				
				String query = "Insert into marks (semester,marks,subject_id) values (?,?,(select subject_id from subject where subject_id = ?));";
				statement = conn.prepareStatement(query);
				//statement.setInt(1, obj.getMarkID());
				statement.setInt(1, obj.getSems());
				statement.setInt(2, obj.getMarks());
				statement.setString(3, obj.getSubjectID());
				confirm=statement.executeUpdate();
				
			}

			// to insert into marks enrollment
			for(Marks obj: list) {
				
				String sql = "Insert into marks_enrollment values ((select marks_id from marks where marks_id = ?),(select student_id from student where student_id = ?));";
				statement = conn.prepareStatement(sql);
				statement.setInt(1, obj.getMarkID());
				statement.setString(2, obj.getStudentID());
				confirm1=statement.executeUpdate();
				
			}
			if((confirm > 0) && (confirm1 > 0)) {
				return true;
			}
			

		} catch (SQLException e) {
			Log.getLogger().log(Level.SEVERE, e.getMessage());
			System.out.println(e.getMessage());
		}
		return false;

	}

}

package com.universityportal.userservices.concrete;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import com.universityportal.bean.Student;
import com.universityportal.database.DB;
import com.universityportal.exceptions.DatabaseException;
import com.universityportal.exceptions.FileReadException;
import com.universityportal.userservices.interfaces.StudentService;
import com.universityportal.utils.Log;
import com.universityportal.utils.ShowOptions;

/**
 * This class implements the Student Service class
 * 
 * @author BaluBruhathi, Ajay, Shubham Nawani
 * 
 */
public class StudentServiceImpl implements StudentService {
	Student student;
	private static final ResourceBundle bundle = ResourceBundle.getBundle("Bundle");
	Scanner scanner = new Scanner(System.in);
	String backToDashBoard;

	public Student getStudent() {
		return this.student;
	}

	public StudentServiceImpl() {

	}

	/**
	 * Class Constructor
	 * 
	 * @param s student object
	 */
	public StudentServiceImpl(Student s) {
		this.student = s;
	}

	/**
	 * This method shows the student menu to the student
	 * 
	 * @param student student object
	 */
	@Override
	public void showMenu(Student student) {
		System.out.println("\t\t\t" + bundle.getString("college_name"));
		System.out.println("\t\t\t");
		int choice = ShowOptions.showOptions("Check Complete Result", "Check Semester Wise Result", "Exit");
		switch (choice) {
		case 1:
			try {
				displayTotalResult(student);
			} catch (FileReadException | DatabaseException | SQLException e) {
				Log.getLogger().log(Level.SEVERE, e.getMessage());
			}
			break;
		case 2:

			try {
				displaySemesterResult(student);
			} catch (FileReadException | DatabaseException | SQLException e) {
				Log.getLogger().log(Level.SEVERE, e.getMessage());
			}
			break;
		case 3:
			return;
		default:
			// shouldn't be called
			Log.getLogger().log(Level.SEVERE, "Show options returned invalid result");
		}
		/**
		 * @author Shubham Nawani
		 */
		System.out.println("Back to DashBoard?");
		int choiceYesNo = ShowOptions.showOptions("Yes", "No");
		switch (choiceYesNo) {
		case 1:
			showMenu(student);
			break;
		case 2:
			return;
		default:
			// shouldn't be called
			Log.getLogger().log(Level.SEVERE, "Show options returned invalid result");
		}
	}

	/**
	 * Asks the student to enter the semester number to view the semester result
	 * 
	 * @param student student object
	 * @throws FileReadException
	 * @throws DatabaseException
	 * @throws SQLException
	 */
	public void displaySemesterResult(Student student) throws FileReadException, DatabaseException, SQLException {
		int semester;
		while(true){
			try {
				System.out.println("Enter the semester to view the result");
				@SuppressWarnings("resource")
				Scanner scanner = new Scanner(System.in);
				semester = scanner.nextInt();
				if(semester > 8 || semester < 1) {
					System.out.println("Enter the correct semester value!");
					continue;
				}
				break;
			} catch (NumberFormatException | InputMismatchException e) {
				System.out.println("Enter the correct semester value!");
			}
		}
		final Connection dbConn;
		dbConn = DB.getInstance().getConnection();
		ShowOptions.showHeader("Result");
		String query1 = "select count(*),sum(m.marks) from subject s inner join marks m on s.subject_id = m.subject_id inner join marks_enrollment me on m.marks_id = me.marks_id inner join student st on me.student_id = st.student_id where st.student_id = ? and m.semester = ?;";
		try (PreparedStatement preparedStatement = dbConn.prepareStatement(query1)) {
			preparedStatement.setString(1, student.getId());
			preparedStatement.setInt(2, semester);
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				// semester details
				displayStudentSemeterDetails(resultSet, semester);
			}
		}

		String semesterResultQuery = "select s.subject_name,s.credits, m.marks from subject s inner join marks m on s.subject_id = m.subject_id inner join marks_enrollment me on m.marks_id = me.marks_id inner join student st on me.student_id = st.student_id where st.student_id = ? and m.semester = ?;";

		try (PreparedStatement preparedStatement2 = dbConn.prepareStatement(semesterResultQuery)) {
			preparedStatement2.setString(1, student.getId());
			preparedStatement2.setInt(2, semester);
			try (ResultSet rs = preparedStatement2.executeQuery();) {
				// semester marks details
				displayStudentSemesterMarks(rs);
			}
		}
	}

	/**
	 * This method takes marks as input and outputs the respective grades
	 * 
	 * @param marks
	 * @return
	 */
	public String getGrade(int marks) {
		int i;
		if (marks == 0) {
			return "Ab";
		}
		// return Not Applicable "N/A" if marks < 0
		else if (marks < 0) {
			return "NA";
		} else {
			String[] grades = { "O", "A+", "A", "B+", "B", "C", "P", "F" };
			int[] leastRange = { 85, 80, 65, 60, 50, 45, 40, 1 };

			for (i = 0; i < grades.length; i++) {
				if (marks >= leastRange[i])
					break;
			}
			return grades[i];
		}
	}

	/**
	 * This method takes marks as input and outputs respective grade points
	 * 
	 * @param marks
	 * @return
	 */
	public int getGradePoints(int marks) {
		if (marks < 0) {
			return 0;
		}
		int i;
		int[] gradePoints = { 10, 9, 8, 7, 6, 5, 4, 0 };
		int[] leastRange = { 85, 80, 65, 60, 50, 45, 40, 1 };
		for (i = 0; i < gradePoints.length; i++) {
			if (marks >= leastRange[i])
				break;
		}
		return gradePoints[i];

	}

	/**
	 * This method prints the semester details
	 * 
	 * @param resultSet
	 * @param semester
	 * @throws SQLException
	 * @throws DatabaseException
	 * @throws FileReadException
	 */
	private void displayStudentSemeterDetails(ResultSet resultSet, int semester)
			throws SQLException, DatabaseException, FileReadException {
		while (resultSet.next()) {
			System.out.println("Semester " + semester);
			int numSubjects = resultSet.getInt(1);
			int totalMarks = resultSet.getInt(2);
			System.out.println("Marks: " + totalMarks + "/" + numSubjects * 100);
			System.out.printf("Percentage: %.3f%% \n", ((float) totalMarks / (numSubjects)));
			double[] creditsAndSgpa = getSgpa(semester);
			System.out.printf("SGPA: %.3f \n", creditsAndSgpa[0]);
			System.out.println("Credits Obtained: " + (int) creditsAndSgpa[1] + "/" + (int) creditsAndSgpa[2]);
			System.out.println();
		}
	}

	/**
	 * This method prints the marks of all the subjects present in the particular
	 * semester
	 * 
	 * @param rs
	 * @throws SQLException
	 */
	private void displayStudentSemesterMarks(ResultSet rs) throws SQLException {
		System.out.println("Subject(Credits) \t\t Marks \t\t Grade Point");
		while (rs.next()) {
			System.out.println(String.format("%-32s %-17s %-12s", rs.getString(1) + "(" + rs.getInt(2) + ")",
					rs.getInt(3) + "(" + getGrade(rs.getInt(3)) + ")", getGradePoints(rs.getInt(3))));
		}
	}

	/**
	 * This method returns an array that consists of sgpa,studentcredits and total
	 * credits
	 * 
	 * @param semester
	 * @return
	 * @throws FileReadException
	 * @throws DatabaseException
	 * @throws SQLException
	 */
	public double[] getSgpa(int semester) throws FileReadException, DatabaseException, SQLException {
		final Connection dbConn;
		dbConn = DB.getInstance().getConnection();
		int totalCredits = 0;
		int studentCredits = 0;
		double sgpa = 0;
		int marks_Subjects;
		int credits_Subjects;
		double[] result = new double[3];

		String query1 = "select m.marks,s.credits from subject s inner join marks m on s.subject_id = m.subject_id inner join marks_enrollment me on m.marks_id = me.marks_id inner join student st on me.student_id = st.student_id where st.student_id = ? and m.semester = ?;";
		try (PreparedStatement preparedStatement = dbConn.prepareStatement(query1)) {
			preparedStatement.setString(1, student.getId());
			preparedStatement.setInt(2, semester);
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				while (resultSet.next()) {
					marks_Subjects = resultSet.getInt(1);
					credits_Subjects = resultSet.getInt(2);
					totalCredits += credits_Subjects;
					if (marks_Subjects >= 40) {
						studentCredits += credits_Subjects;
						sgpa += getGradePoints(marks_Subjects) * credits_Subjects;
					}
				}

			}
		}
		result[0] = sgpa / totalCredits;
		result[1] = studentCredits;
		result[2] = totalCredits;
		return result;
	}

	/***
	 * @author Ajay, Shubham
	 * @param student
	 * @throws FileReadException
	 * @throws DatabaseException
	 * @throws SQLException
	 */
	private void displayTotalResult(Student student) throws FileReadException, DatabaseException, SQLException {
		final Connection dbConn;
		dbConn = DB.getInstance().getConnection();
		ShowOptions.showHeader("Result");
		String query1 = "select branch_name,sum(marks),count(marks)*100 from subject s inner join marks m on s.subject_id = m.subject_id inner join marks_enrollment me on m.marks_id = me.marks_id inner join student st on me.student_id = st.student_id inner join branch b on b.branch_id = st.branch_id where st.student_id = ?;";
		try (PreparedStatement preparedStatement = dbConn.prepareStatement(query1)) {
			preparedStatement.setString(1, student.getId());
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				displayStudentSemetersDetails(resultSet);
			}
		}

		String totalResultQuery = "select semester,sum(marks), count(marks)*100, sum(credits) from subject s inner join marks m on s.subject_id = m.subject_id inner join marks_enrollment me on m.marks_id = me.marks_id inner join student st on me.student_id = st.student_id where st.student_id = ?  group by semester order by semester;";
		try (PreparedStatement preparedStatement2 = dbConn.prepareStatement(totalResultQuery)) {
			preparedStatement2.setString(1, student.getId());
			try (ResultSet rs = preparedStatement2.executeQuery();) {
				displayStudentSemestersMarks(rs);
			}
		}
	}

	/**
	 * @author Shubham Nawani
	 * @param rollno
	 * @return maximum semester the result exists for
	 * @throws DatabaseException
	 * @throws FileReadException
	 * @throws SQLException
	 */
	public int getMaxSemester(String rollno) throws FileReadException, DatabaseException, SQLException {
		final Connection dbConn;
		dbConn = DB.getInstance().getConnection();
		final String getMaxSemesterQuery = "select max(semester) from marks m inner join marks_enrollment me on m.marks_id = me.marks_id	inner join student st on me.student_id = st.student_id where st.student_id = ?";

		try (PreparedStatement preparedStatement = dbConn.prepareStatement(getMaxSemesterQuery)) {

			preparedStatement.setString(1, rollno);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				return resultSet.getInt(1);
			}
		}
		return 1;
	}

	/**
	 * @author Shubham Nawani
	 * @param marks
	 * @param total
	 * @return total aggregate percentage
	 */
	public double calcPercentage(int marks, int total) {
		return (((float) marks) / total) * 100;
	}

	/**
	 * @author Shubham Nawani
	 * @param studentMarks
	 * @param totalMarks
	 * @return student marks out of total marks
	 */
	public String calcTotalMarks(int studentMarks, int totalMarks) {
		return studentMarks + "/" + totalMarks;
	}

	private void displayStudentSemetersDetails(ResultSet resultSet)
			throws SQLException, DatabaseException, FileReadException {
		while (resultSet.next()) {

			String branchName = resultSet.getString(1);
			int studentMarks = resultSet.getInt(2);
			int totalMarks = resultSet.getInt(3);
			double sumPoints = 0;
			int totalCredits = 0;
			int totalStudentCredits = 0;
			int maxSemester = getMaxSemester(student.getId());

			System.out.println("Enrollment Number: " + student.getId());
			System.out.println("Student Name: " + student.getFirstName() + " " + student.getLastName());
			System.out.println("Programme:Bachelor of Technology(B.Tech.)");
			System.out.println("Branch: " + branchName);
			System.out.println();
			System.out.println("Marks: " + calcTotalMarks(studentMarks, totalMarks));
			System.out.printf("Percentage: %.3f%% \n", calcPercentage(studentMarks, totalMarks));

			for (int i = 1; i <= maxSemester; ++i) {
				double[] sgpa = getSgpa(i);
				sumPoints += sgpa[0];
				totalStudentCredits += sgpa[1];
				totalCredits += sgpa[2];
			}

			System.out.printf("CGPA: %.3f\n", sumPoints / maxSemester);
			System.out.println("Credits Obtained: " + (int) totalStudentCredits + "/" + (int) totalCredits);
		}
	}

	private void displayStudentSemestersMarks(ResultSet rs) throws SQLException, FileReadException, DatabaseException {
		System.out.println(String.format("\n%-15s %-15s %-15s %-15s", "Sem", "Marks", "Percentage", "SGPA"));
		while (rs.next()) {
			double[] sgpa = getSgpa(rs.getInt(1));
			System.out.println(String.format("%-15s %-15s %.3f%%         %-15.3f", "Semester " + rs.getInt(1),
					rs.getInt(2) + "/" + rs.getInt(3), ((float) rs.getInt(2) / rs.getInt(3)) * 100, sgpa[0]));
		}
	}

}

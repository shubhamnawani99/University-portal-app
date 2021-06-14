package com.universityportal.userservices.concrete;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.universityportal.exceptions.DatabaseException;
import com.universityportal.exceptions.FileReadException;

public class StudentServiceImplTotalResultTest {
	static StudentServiceImpl studentService;

	@BeforeClass
	public static void TestSetup() {
		studentService = new StudentServiceImpl();
	}

	@Test
	public void testGetValidMaxSemester() throws FileReadException, DatabaseException, SQLException {
		assertEquals(8, studentService.getMaxSemester("06714802717"));
	}

	@Test
	public void testGetValidMaxSemester_sem1() throws FileReadException, DatabaseException, SQLException {
		assertEquals(1, studentService.getMaxSemester("00714802717"));
	}

	@Test
	public void testGetInvalidMaxSemester() throws FileReadException, DatabaseException, SQLException {
		// user doesn't exist here
		assertEquals(0, studentService.getMaxSemester("11214802717"));
	}

	@Test
	public void testGetTotalMarks() {
		assertEquals("3792/4000", studentService.calcTotalMarks(3792, 4000));
	}

	@Test
	public void testGetTotalPercentage() {
		assertEquals(94.8, studentService.calcPercentage(3792, 4000), 0.1);
	}
}

package com.universityportal.userservices.concrete;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class StudentServiceImplSemesterResultTest {
	static StudentServiceImpl studentService;

	@BeforeClass
	public static void TestSetup() {
		studentService = new StudentServiceImpl();
	}

	@Test
	public void testGetGradeWithHighMarks() {
		assertEquals("O", studentService.getGrade(90));
	}

	@Test
	public void testGetGradeWithAverageMarks() {
		assertEquals("B+", studentService.getGrade(62));
	}

	@Test
	public void testGetGradeWithAbsent() {
		assertEquals("Ab", studentService.getGrade(0));
	}

	@Test
	public void testGetGradeWithNegativeMarks() {
		assertEquals("NA", studentService.getGrade(-1));
	}

	@Test
	public void testGetGradePointsWithHighMarks() {
		assertEquals(10, studentService.getGradePoints(92));
	}

	@Test
	public void testGetGradePointsWithLowMarks() {
		assertEquals(4, studentService.getGradePoints(41));
	}

	@Test
	public void testGetGradePointsWithAverageMarks() {
		assertEquals(7, studentService.getGradePoints(62));
	}

	@Test
	public void testGetGradePointsWithNegativeMarks() {
		assertEquals(0, studentService.getGradePoints(-1));
	}

}

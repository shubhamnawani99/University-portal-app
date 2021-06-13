package com.universityportal.userservices.concrete;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class StudentServiceImplSemesterResultTest {
	static StudentServiceImpl studentService;
	
	
	@BeforeClass
	public static void TestSetup() {
		studentService=new StudentServiceImpl();
	}
	
	
	@Test
	public void testGetGrade1(){
		assertEquals("O",studentService.getGrade(90));
	}
	
	@Test
	public void testGetGrade2(){
		assertEquals("B+",studentService.getGrade(62));
	}
	
	@Test
	public void testGetGrade3() {
		assertEquals("Ab",studentService.getGrade(0));
	}
	@Test
	public void testGetGradePoints1() {
		assertEquals(10,studentService.getGradePoints(92));
	}
	@Test
	public void testGetGradePoints2() {
		assertEquals(4,studentService.getGradePoints(41));
	}
	
	@Test
	public void testGetGradePoints3() {
		assertEquals(7,studentService.getGradePoints(62));
	}
	

}

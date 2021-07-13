package com.cognizant.student.service;

import com.cognizant.student.model.Student;
import com.cognizant.student.util.CompleteResult;
import com.cognizant.student.util.SemesterResult;

public interface IStudentService {

	public Student saveStudent(Student student);

	public SemesterResult getSemesterResult(String studentId, Integer semester);

	public CompleteResult getCompleteResult(String studentId);

}

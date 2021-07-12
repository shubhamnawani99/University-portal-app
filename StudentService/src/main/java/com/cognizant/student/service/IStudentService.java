package com.cognizant.student.service;

import com.cognizant.student.model.Student;
import com.cognizant.student.util.ResponseModel;

public interface IStudentService {

	public Student saveStudent(Student student);

	public ResponseModel getSemesterResult(String studentId, Integer semester);
}

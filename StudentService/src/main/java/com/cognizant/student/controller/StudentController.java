package com.cognizant.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.student.exception.InvalidUsernameException;
import com.cognizant.student.service.IStudentService;
import com.cognizant.student.util.AuthorizationClient;
import com.cognizant.student.util.CompleteResult;
import com.cognizant.student.util.SemesterResult;

import feign.FeignException.FeignClientException;

/***
 * REST Student Controller for student related operations
 * @author Shubham Nawani
 *
 */
@RequestMapping(value = "student")
@RestController
public class StudentController {

	@Autowired
	private IStudentService studentService;

	@Autowired
	private AuthorizationClient authorizationClient;

	@Value("${studentController.credentialsMessage}")
	private String CREDENTIALS_ERROR;
	/**
	 * @URL: http://localhost:8091/student/getSemesterResult/06714802717/2
	 * @param studentId
	 * @param semester  whose result needs to be calculated
	 * @return result of a particular semester
	 * @Expected {
				  "branch": "Computer Science and Engineering",
				  "semester": 2,
				  "marksObtained": 65,
				  "totalMarks": 100,
				  "percentage": 65,
				  "sgpa": 8,
				  "creditsObtained": 4,
				  "totalCredits": 4,
				  "resultDetails": [
				    {
				      "subjectName": "Applied Maths-II",
				      "credits": 4,
				      "marks": 65,
				      "grade": "A",
				      "gradePoint": 8
				    }
				  ]
				} 
	 * 
	 */
	@GetMapping(value = "/getSemesterResult/{studentId}/{semester}")
	public ResponseEntity<SemesterResult> getSemesterResult(
			@RequestHeader(name = "Authorization") String token,
			@PathVariable String studentId, 
			@PathVariable Integer semester) 
	{
		try {
			ResponseEntity<Boolean> validateStudent = authorizationClient.validateStudent(token, studentId);
			if (validateStudent.getBody()) {
				SemesterResult semesterResult = studentService.getSemesterResult(studentId, semester);
				return new ResponseEntity<SemesterResult>(semesterResult, HttpStatus.OK);
			}
		} catch (FeignClientException e) {
			throw new InvalidUsernameException(CREDENTIALS_ERROR);
		}
		return new ResponseEntity<SemesterResult>(HttpStatus.FORBIDDEN);
	}

	/**
	 * @URL: http://localhost:8091/student/getCompleteResult/06714802717/
	 * @param studentId
	 * @return complete result for a student
	 * 
	 */
	@GetMapping(value = "/getCompleteResult/{studentId}/")
	public ResponseEntity<CompleteResult> getCompleteResult(
			@RequestHeader(name = "Authorization") String token,
			@PathVariable String studentId) 
	{
		try {
			ResponseEntity<Boolean> validateStudent = authorizationClient.validateStudent(token, studentId);
			if (validateStudent.getBody()) {
				CompleteResult completeResult = studentService.getCompleteResult(studentId);
				return new ResponseEntity<CompleteResult>(completeResult, HttpStatus.OK);
			}
		} catch (FeignClientException e) {
			throw new InvalidUsernameException(CREDENTIALS_ERROR);
		}
		return new ResponseEntity<CompleteResult>(HttpStatus.FORBIDDEN);
	}
	
	@GetMapping(value = "/statusCheck")
	public String statusCheck() {
		return "OK";
	}
}

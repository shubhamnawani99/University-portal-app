package com.cognizant.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.student.model.Branch;
import com.cognizant.student.model.Student;
import com.cognizant.student.service.IBranchService;
import com.cognizant.student.service.IStudentService;
import com.cognizant.student.util.ResponseModel;

@RequestMapping(value = "student")
@RestController
public class StudentController {

	@Autowired
	private IStudentService studentService;

//	/**
//	 * TODO: for admin service
//	 * @URL: http://localhost:8091/student/addBranch
//	 * @Data: {"branchId":1, "branchName": "Computer Science and Engineering"}
//	 * @param branch details
//	 * @return saved branch details with response
//	 */
//	@PostMapping(value = "/addBranch")
//	public ResponseEntity<Branch> addBranch(@RequestBody Branch branch) {
//		Branch savedBranch = bservice.addBranch(branch);
//		return new ResponseEntity<Branch>(savedBranch, HttpStatus.CREATED);
//	}
//
//	/**
//	 * @URL: http://localhost:8091/student/saveStudent
//	 * @Data: {"studentId": "06714802717", "branchId": 1, "firstName": "Shubham",
//	 *        "lastName": "Nawani", "phoneNumber": "+91-9911991199", "parentNumber":
//	 *        "101", "bloodGroup": "A+"}
//	 * @param student details
//	 * @return saved student details with response
//	 */
//	@PostMapping(value = "/saveStudent")
//	public ResponseEntity<Student> saveStudentDetails(@RequestBody Student student) {
//		Student savedStudentDetails = studentService.saveStudent(student);
//		return new ResponseEntity<Student>(savedStudentDetails, HttpStatus.CREATED);
//	}

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
			    "percentage": 65.0,
			    "sgpa": 8.0,
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
	public ResponseEntity<ResponseModel> getSemesterResult(@PathVariable String studentId,
			@PathVariable Integer semester) {
		ResponseModel semesterResult = studentService.getSemesterResult(studentId, semester);
		return new ResponseEntity<ResponseModel>(semesterResult, HttpStatus.OK);
	}
}

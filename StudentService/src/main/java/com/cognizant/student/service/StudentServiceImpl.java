package com.cognizant.student.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.student.model.Marks;
import com.cognizant.student.model.Student;
import com.cognizant.student.model.Subject;
import com.cognizant.student.repository.BranchRepository;
import com.cognizant.student.repository.MarksRepository;
import com.cognizant.student.repository.StudentRepository;
import com.cognizant.student.repository.SubjectRepository;
import com.cognizant.student.util.ResponseModel;
import com.cognizant.student.util.ResultDetails;

@Service
public class StudentServiceImpl implements IStudentService {

	@Autowired
	private StudentRepository srepo;
	@Autowired
	private BranchRepository brepo;
	@Autowired
	private MarksRepository mrepo;
	@Autowired
	private SubjectRepository subrepo;

	@Override
	public Student saveStudent(Student student) {
		return srepo.save(student);
	}

	/**
	 * Method to compute the Semester result for a student
	 * 
	 * @author Shubham Nawani
	 * 
	 */
	@Override
	public ResponseModel getSemesterResult(String studentId, Integer semester) {

		// List to store all the semester result details
		List<ResultDetails> semesterResultDetails = new ArrayList<>();

		// Total Marks Obtained
		Integer marksObtained = 0;

		// Get marks of all subjects for a particular student and semester
		List<Marks> allMarksDetailsForOneSemester = mrepo.findByStudentIdAndSemester(studentId, semester);

		// Total Marks Alloted per subject
		Integer totalMarks = 100 * allMarksDetailsForOneSemester.size();

		// SGPA
		Double sgpa = 0.0;

		// Credits
		Integer totalCredits = 0;
		Integer creditsObtained = 0;

		for (Marks marks : allMarksDetailsForOneSemester) {

			// get the marks scored in a subject
			final Integer marksScored = marks.getMarks();
			marksObtained += marksScored;

			// find the subject
			final Subject subject = subrepo.findById(marks.getSubjectId()).get();

			// find the grade
			String grade = getGrade(marksScored);

			// find the getPoints
			int gradePoints = getGradePoints(marksScored);

			// calculate the SGPA
			Integer subjectCredit = subject.getCredits();
			sgpa += subjectCredit * gradePoints;

			// calculate total credits and credits obtained
			totalCredits += subjectCredit;
			if (marksScored > 40) {
				creditsObtained += subjectCredit;
			}

			// form the result details
			ResultDetails resultDetails = new ResultDetails(subject.getSubjectName(), subjectCredit, marksScored, grade,
					gradePoints);

			// add result details to the list
			semesterResultDetails.add(resultDetails);
		}

		Double percentage = (double) marksObtained * 100 / totalMarks;

		// Compute SGPA
		sgpa /= totalCredits;

		// Find branch
		Integer branchId = srepo.findById(studentId).get().getBranchId();
		String branchName = brepo.findById(branchId).get().getBranchName();

		// Create and return the responseModel
		return new ResponseModel(branchName, semester, marksObtained, totalMarks, percentage, sgpa, creditsObtained,
				totalCredits, semesterResultDetails);
	}

	/*****************************
	 * HELPER FUNCTIONS
	 ******************************/

	/**
	 * This method takes marks as input and outputs the respective grades
	 * 
	 * @param marks for a subject
	 * @return grade corresponding to the subject
	 */
	public static String getGrade(int marks) {
		String[] grades = { "O", "A+", "A", "B+", "B", "C", "P", "F", "Ab" };
		int[] leastRange = { 85, 80, 65, 60, 50, 45, 40, 1, 0 };
		for (int i = 0; i < grades.length; ++i) {
			if (marks >= leastRange[i])
				return grades[i];
		}
		return "NA";
	}

	/**
	 * This method takes marks as input and outputs respective grade points
	 * 
	 * @param marks for a subject
	 * @return grade points corresponding to the subject
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

}

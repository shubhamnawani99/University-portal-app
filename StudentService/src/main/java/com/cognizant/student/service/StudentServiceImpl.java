package com.cognizant.student.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cognizant.student.exception.InvalidSemesterException;
import com.cognizant.student.model.Marks;
import com.cognizant.student.model.Student;
import com.cognizant.student.model.Subject;
import com.cognizant.student.repository.BranchRepository;
import com.cognizant.student.repository.MarksRepository;
import com.cognizant.student.repository.StudentRepository;
import com.cognizant.student.repository.SubjectRepository;
import com.cognizant.student.util.CompleteResult;
import com.cognizant.student.util.CompleteResultDetails;
import com.cognizant.student.util.SemesterResult;
import com.cognizant.student.util.SemesterResultDetails;

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

	@Value("${studentService.invalidSemesterMessage}")
	private String INVALID_SEMESTER_VALUE_MESSAGE;

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
	public SemesterResult getSemesterResult(String studentId, Integer semester) {

		if (semester < 1 || semester > 8) {
			throw new InvalidSemesterException(INVALID_SEMESTER_VALUE_MESSAGE);
		}

		// List to store all the semester result details
		List<SemesterResultDetails> semesterResultDetails = new ArrayList<>();

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
			SemesterResultDetails resultDetails = new SemesterResultDetails(subject.getSubjectName(), subjectCredit,
					marksScored, grade, gradePoints);

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
		return new SemesterResult(branchName, semester, marksObtained, totalMarks, percentage, sgpa, creditsObtained,
				totalCredits, semesterResultDetails);
	}

	/**
	 * Method to compute the Complete result for a student
	 * 
	 * @param studentId
	 * @return
	 */
	public CompleteResult getCompleteResult(String studentId) {
		// get student name
		Student student = srepo.findById(studentId).get();
		String studentName = student.getFirstName() + " " + student.getLastName();

		// get student program and branch names
		final String programme = "Bachelors of Technology";
		String branchName = brepo.findById(student.getBranchId()).get().getBranchName();

		// Create the Complete Result Details
		List<CompleteResultDetails> completeResultDetailsList = new ArrayList<>();

		final Integer MAX_SEMESTER = getMaxSemester(studentId);
		Integer totalMarks = 0;
		Integer marksObtained = 0;
		Double percentage = 0.0;
		Double cgpa = 0.0;
		Integer creditsObtained = 0;
		Integer totalCredits = 0;

		// Get result for all semesters
		for (int semester = 1; semester <= MAX_SEMESTER; ++semester) {
			SemesterResult semesterResult = getSemesterResult(studentId, semester);

			Integer marksObtainedForSemester = semesterResult.getMarksObtained();
			marksObtained += marksObtainedForSemester;

			Integer totalMarksForSemester = semesterResult.getTotalMarks();
			totalMarks += totalMarksForSemester;

			Double percentageForSemester = semesterResult.getPercentage();
			percentage += percentageForSemester;

			Double sgpaForSemester = semesterResult.getSgpa();
			cgpa += sgpaForSemester;

			creditsObtained += semesterResult.getCreditsObtained();
			totalCredits += semesterResult.getTotalCredits();

			CompleteResultDetails completeResultDetails = new CompleteResultDetails(semester, marksObtainedForSemester,
					totalMarksForSemester, percentageForSemester, sgpaForSemester);

			completeResultDetailsList.add(completeResultDetails);
		}

		// get final percentage and CGPA
		percentage /= MAX_SEMESTER;
		cgpa /= MAX_SEMESTER;

		return new CompleteResult(studentId, studentName, programme, branchName, marksObtained, totalMarks, percentage,
				cgpa, creditsObtained, totalCredits, completeResultDetailsList);
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
	private String getGrade(int marks) {
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
	private int getGradePoints(int marks) {
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

	private Integer getMaxSemester(String studentId) {
		Optional<Integer> findMaxSemester = mrepo.findMaxSemester(studentId);
		if (findMaxSemester.isEmpty()) {
			return -1;
		}
		return findMaxSemester.get();
	}

}

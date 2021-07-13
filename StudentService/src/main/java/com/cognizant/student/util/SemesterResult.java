package com.cognizant.student.util;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SemesterResult {

	private String branch;
	private Integer semester;
	private Integer marksObtained;
	private Integer totalMarks;
	private Double percentage;
	private Double sgpa;
	private Integer creditsObtained;
	private Integer totalCredits;
	private List<SemesterResultDetails> resultDetails;
	
}

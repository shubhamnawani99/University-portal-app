package com.cognizant.student.util;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompleteResult {

	private String studentId;
	private String studentName;
	private String programme;
	private String branch;
	private Integer marksObtained;
	private Integer totalMarks;
	private Double percentage;
	private Double cgpa;
	private Integer creditsObtained;
	private Integer totalCredits;
	private List<CompleteResultDetails> completeResultDetails;
	
}

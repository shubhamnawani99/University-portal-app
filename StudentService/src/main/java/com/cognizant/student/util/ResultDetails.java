package com.cognizant.student.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultDetails {

	private String subjectName;
	private Integer credits;
	private Integer marks;
	private String grade;
	private Integer gradePoint;
}

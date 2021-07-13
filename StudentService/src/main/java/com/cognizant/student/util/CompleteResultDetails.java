package com.cognizant.student.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompleteResultDetails {

	private int semester;
	private Integer marks;
	private Integer totalMarks;
	private Double percentage;
	private Double sgpa;
}

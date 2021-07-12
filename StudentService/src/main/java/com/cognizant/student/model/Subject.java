package com.cognizant.student.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class Subject {

	@Id
	@Size(min = 8, max = 8)
	private String subjectId;
	
	@Column(nullable = false)
	@Size(max = 100)
	private String subjectName;
	
	@Column(nullable = false)
	private Integer credits;

}

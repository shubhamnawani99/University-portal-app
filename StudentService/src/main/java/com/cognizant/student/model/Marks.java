package com.cognizant.student.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Marks {

	@Id
	private Integer marksId;
	@Column
	private String studentId;
	@Column
	private Integer semester;
	@Column
	private Integer marks;
	@Column
	private String subjectId;
	
}

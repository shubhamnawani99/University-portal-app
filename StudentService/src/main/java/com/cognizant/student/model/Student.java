package com.cognizant.student.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Student {

	@Id
	private String studentId;
	@Column
	private Integer branchId;
	@Column
	private String firstName;
	@Column
	private String lastName;
	@Column(name = "phone_no")
	private String phoneNumber;
	@Column(name = "parent_no")
	private String parentNumber;
	@Column(name = "blood_grp")
	private String bloodGroup;

}

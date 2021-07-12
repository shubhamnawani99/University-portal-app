package com.cognizant.student.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Branch {

	@Id
	private int branchId;
	@Column
	private String branchName;

}

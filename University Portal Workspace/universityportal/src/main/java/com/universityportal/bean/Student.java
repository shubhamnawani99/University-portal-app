package com.universityportal.bean;

public class Student {
	
	private String id;
	private int branchId;
	private String firstName;
	private String lastName;
	private String phoneNo;
	private String parentNo;
	private String bloodGrp;

	public Student() {
		super();
	}

	public Student(String id, int branchId, String firstName, String lastName, String phoneNo,
			String parentNo, String bloodGrp) {
		super();
		this.id = id;
		this.branchId = branchId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNo = phoneNo;
		this.parentNo = parentNo;
		this.bloodGrp = bloodGrp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getParentNo() {
		return parentNo;
	}

	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}

	public String getBloodGrp() {
		return bloodGrp;
	}

	public void setBloodGrp(String bloodGrp) {
		this.bloodGrp = bloodGrp;
	}

}

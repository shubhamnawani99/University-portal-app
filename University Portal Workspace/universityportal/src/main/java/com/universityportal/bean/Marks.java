/**
 * 
 */
package com.universityportal.bean;

/**
 * @author whoami
 *
 */
public class Marks {
	
	private int markID;
	private String studentID;
	private int sems;
	private String subjectID;
	private int marks;
	
	
	
	/**
	 * @param markID
	 * @param studentID
	 * @param sems
	 * @param subjectID
	 * @param marks
	 */
	public Marks(int markID, String studentID, int sems, String subjectID, int marks) {
		super();
		this.markID = markID;
		this.studentID = studentID;
		this.sems = sems;
		this.subjectID = subjectID;
		this.marks = marks;
	}
	/**
	 * @return the markID
	 */
	public int getMarkID() {
		return markID;
	}
	/**
	 * @param markID the markID to set
	 */
	public void setMarkID(int markID) {
		this.markID = markID;
	}
	/**
	 * @return the studentID
	 */
	public String getStudentID() {
		return studentID;
	}
	/**
	 * @param studentID the studentID to set
	 */
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	/**
	 * @return the sems
	 */
	public int getSems() {
		return sems;
	}
	/**
	 * @param sems the sems to set
	 */
	public void setSems(int sems) {
		this.sems = sems;
	}
	/**
	 * @return the subjectID
	 */
	public String getSubjectID() {
		return subjectID;
	}
	/**
	 * @param subjectID the subjectID to set
	 */
	public void setSubjectID(String subjectID) {
		this.subjectID = subjectID;
	}
	/**
	 * @return the marks
	 */
	public int getMarks() {
		return marks;
	}
	/**
	 * @param marks the marks to set
	 */
	public void setMarks(int marks) {
		this.marks = marks;
	}
	
}

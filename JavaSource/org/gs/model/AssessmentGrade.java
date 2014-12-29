package org.gs.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AssessmentGrade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3396019217296408690L;
	
	public boolean isNew () {
		return this.id == 0;
	}
	
	public AssessmentGrade () {
		this.otherGrades = new ArrayList<Object>(5);
		for (int i=0; i < 5; i++) {
			this.otherGrades.add(null);
		}
	}

	public Float getTotalContiniousAssessement() {
		return totalContiniousAssessement;
	}

	public void setTotalContiniousAssessement(Float totalContiniousAssessement) {
		this.totalContiniousAssessement = totalContiniousAssessement;
	}

	public Float getFinalExamMarks() {
		return finalExamMarks;
	}

	public void setFinalExamMarks(Float finalExamMarks) {
		this.finalExamMarks = finalExamMarks;
	}

	public Float getTotalAssessmentPlusExam() {
		return totalAssessmentPlusExam;
	}

	public void setTotalAssessmentPlusExam(Float totalAssessmentPlusExam) {
		this.totalAssessmentPlusExam = totalAssessmentPlusExam;
	}

	public Float getFinalGrade() {
		return finalGrade;
	}

	public void setFinalGrade(Float finalGrade) {
		this.finalGrade = finalGrade;
	}

	public List<Float> getAssessmentGrades() {
		return assessmentGrades;
	}

	public void setAssessmentGrades(List<Float> assessmentGrades) {
		this.assessmentGrades = assessmentGrades;
	}
	
	public Registration getRegistration() {
		return registration;
	}

	public void setRegistration(Registration registration) {
		this.registration = registration;
	}

	public ClassCourse getClassCourse() {
		return classCourse;
	}

	public void setClassCourse(ClassCourse classCourse) {
		this.classCourse = classCourse;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLetterCode() {
		return letterCode;
	}

	public void setLetterCode(String letterCode) {
		this.letterCode = letterCode;
	}

	public List<Object> getOtherGrades() {
		return otherGrades;
	}

	public void setOtherGrades(List<Object> otherGrades) {
		this.otherGrades = otherGrades;
	}




	private List<Float> assessmentGrades;
	private List<Object> otherGrades;
	private Registration registration;
	private ClassCourse classCourse;
	private Float totalContiniousAssessement = null;
	private Float finalExamMarks = null;
	private Float totalAssessmentPlusExam = null;
	private Float finalGrade = null;
	private String letterCode;
	private int id;
}

package org.gs.model;

import java.sql.Date;
import java.util.List;

public class SchoolYear {

	public SchoolYear() {
		// TODO Auto-generated constructor stub
	}

	public int getSchoolYearId() {
		return schoolYearId;
	}
	public void setSchoolYearId(int schoolYearId) {
		this.schoolYearId = schoolYearId;
	}
	public String getSchoolYearTitle() {
		return schoolYearTitle;
	}
	public void setSchoolYearTitle(String schoolYearTitle) {
		this.schoolYearTitle = schoolYearTitle;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
	

	public List<SchoolPeriod> getSchoolPeriods() {
		return schoolPeriods;
	}

	public void setSchoolPeriods(List<SchoolPeriod> schoolPeriods) {
		this.schoolPeriods = schoolPeriods;
	}



	private int schoolYearId;
	private int schoolId;
	private String schoolYearTitle;
	private Date startDate;
	private Date endDate;
	private List<SchoolPeriod> schoolPeriods;
}

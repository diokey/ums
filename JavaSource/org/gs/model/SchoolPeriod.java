package org.gs.model;

import java.util.Date;

public class SchoolPeriod {

	public SchoolPeriod() {
		// TODO Auto-generated constructor stub
	}

	public int getSchoolPeriodId() {
		return schoolPeriodId;
	}
	public void setSchoolPeriodId(int schoolPeriodId) {
		this.schoolPeriodId = schoolPeriodId;
	}
	public int getSchoolYearId() {
		return schoolYearId;
	}
	public void setSchoolYearId(int schoolYearId) {
		this.schoolYearId = schoolYearId;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getPeriodDescription() {
		return periodDescription;
	}
	public void setPeriodDescription(String periodDescription) {
		this.periodDescription = periodDescription;
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
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}


	private int schoolPeriodId;
	private int schoolYearId;
	private String period;
	private String periodDescription;
	private Date startDate;
	private Date endDate;
	private Date createdOn;
	private int createdBy;
	private boolean deleted;
}

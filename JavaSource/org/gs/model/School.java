package org.gs.model;

import java.util.List;

public class School {

	public School() {
		// TODO Auto-generated constructor stub
	}

	public int getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<SchoolYear> getSchoolYears() {
		return schoolYears;
	}

	public void setSchoolYears(List<SchoolYear> schoolYears) {
		this.schoolYears = schoolYears;
	}
	
	public boolean addSchoolYear(SchoolYear newSy) {
		return this.schoolYears.add(newSy);
	}
	
	public boolean removeSchoolYear(SchoolYear sy) {
		if(this.schoolYears!=null && this.schoolYears!=null)
			return this.schoolYears.remove(sy);
		return false;
	}



	private int schoolId;
	private String title;
	private String adress;
	private String city;
	private String phone;
	private String principal;
	private String site;
	private String email;
	private List<SchoolYear> schoolYears;
}

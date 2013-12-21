package org.gs.model;

import java.util.Date;

public class Log {

	public Log() {
		// TODO Auto-generated constructor stub
	}

	public int getLogId() {
		return logId;
	}
	public void setLogId(int logId) {
		this.logId = logId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Date getLogDate() {
		return logDate;
	}
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	
	public String getLogSeverity() {
		return logSeverity;
	}

	public void setLogSeverity(String logSeverity) {
		this.logSeverity = logSeverity;
	}



	private int logId;
	private String description;
	private String logSeverity;
	private User user;
	private int userId;
	private Date logDate;
	private String userIp;
}

package org.gs.model;

public class User {

	public User() {
		// TODO Auto-generated constructor stub
	}

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Role getUserRole() {
		return userRole;
	}
	public void setUserRole(Role userRole) {
		this.userRole = userRole;
	}
	public String getResetHash() {
		return resetHash;
	}
	public void setResetHash(String resetHash) {
		this.resetHash = resetHash;
	}
	public boolean isBanned() {
		return banned;
	}
	public void setBanned(boolean banned) {
		this.banned = banned;
	}
	public String getLastIp() {
		return lastIp;
	}
	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}
	public String getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
	public int getResetBy() {
		return resetBy;
	}
	public void setResetBy(int resetBy) {
		this.resetBy = resetBy;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public String getBannedMessage() {
		return bannedMessage;
	}
	public void setBannedMessage(String bannedMessage) {
		this.bannedMessage = bannedMessage;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public int getPasswordIterations() {
		return passwordIterations;
	}
	public void setPasswordIterations(int passwordIterations) {
		this.passwordIterations = passwordIterations;
	}
	public boolean isForcePasswordReset() {
		return forcePasswordReset;
	}
	public void setForcePasswordReset(boolean forcePasswordReset) {
		this.forcePasswordReset = forcePasswordReset;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	public int getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}
	
	


	private int userId;
	private String username;
	private String userPassword;
	private String email;
	private int userRoleId;
	private Role userRole;
	private String resetHash;
	private boolean banned;
	private String lastIp;
	private String lastLogin;
	private int resetBy;
	private boolean deleted;
	private String bannedMessage;
	private String displayName;
	private String language;
	private boolean active;
	private int passwordIterations;
	private boolean forcePasswordReset;
}

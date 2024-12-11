package com.sub.potenfi.dto;

public class UserDto {
    private String user_id;
    private String user_name;
    private String email;
    private String user_cd;
    private int budget;
    private String audit_id;
    private String audit_dtm;
    
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUser_cd() {
		return user_cd;
	}
	public void setUser_cd(String user_cd) {
		this.user_cd = user_cd;
	}
	public int getBudget() {
		return budget;
	}
	public void setBudget(int budget) {
		this.budget = budget;
	}
	public String getAudit_id() {
		return audit_id;
	}
	public void setAudit_id(String audit_id) {
		this.audit_id = audit_id;
	}
	public String getAudit_dtm() {
		return audit_dtm;
	}
	public void setAudit_dtm(String audit_dtm) {
		this.audit_dtm = audit_dtm;
	}
    
}

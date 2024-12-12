package com.sub.potenfi.dto;

public class UserDTO {
    private String userId;
    private String userName;
    private String nickname;
    private String email;
    private String userCd;
    private int budget;
    
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserCd() {
		return userCd;
	}
	public void setUser_cd(String userCd) {
		this.userCd = userCd;
	}
	public int getBudget() {
		return budget;
	}
	public void setBudget(int budget) {
		this.budget = budget;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
    
}

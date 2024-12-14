package com.sub.potenfi.dto;

public class SubscriptionUpdateDTO {
    private String currentPlatformId; // 현재 플랫폼 ID
    private String platformId; // 새로운 플랫폼 ID
    private String planId; // 새로운 요금제 ID
    private String billingMonth; // 결제 월
    private String billingDay; // 결제 일
    private String isGroup; // 그룹 구독 여부
    private String isYearlyPay; // 연간 결제 여부
    private int actualCost; // 실제 결제 금액
    private int activeUsers; // 활성 사용자 수
    
	public String getCurrentPlatformId() {
		return currentPlatformId;
	}
	public void setCurrentPlatformId(String currentPlatformId) {
		this.currentPlatformId = currentPlatformId;
	}
	public String getPlatformId() {
		return platformId;
	}
	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getBillingMonth() {
		return billingMonth;
	}
	public void setBillingMonth(String billingMonth) {
		this.billingMonth = billingMonth;
	}
	public String getBillingDay() {
		return billingDay;
	}
	public void setBillingDay(String billingDay) {
		this.billingDay = billingDay;
	}
	public String isGroup() {
		return isGroup;
	}
	public void setGroup(String isGroup) {
		this.isGroup = isGroup;
	}
	public String isYearlyPay() {
		return isYearlyPay;
	}
	public void setYearlyPay(String isYearlyPay) {
		this.isYearlyPay = isYearlyPay;
	}
	public int getActualCost() {
		return actualCost;
	}
	public void setActualCost(int actualCost) {
		this.actualCost = actualCost;
	}
	public int getActiveUsers() {
		return activeUsers;
	}
	public void setActiveUsers(int activeUsers) {
		this.activeUsers = activeUsers;
	}
}

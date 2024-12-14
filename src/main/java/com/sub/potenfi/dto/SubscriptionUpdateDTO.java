package com.sub.potenfi.dto;

public class SubscriptionUpdateDTO {
    private String currentPlatformId; // 현재 플랫폼 ID
    private String platformId; // 새로운 플랫폼 ID
    private String planId; // 새로운 요금제 ID
    private int billingMonth; // 결제 월
    private int billingDay; // 결제 일
    private boolean isGroup; // 그룹 구독 여부
    private boolean isYearlyPay; // 연간 결제 여부
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
	public int getBillingMonth() {
		return billingMonth;
	}
	public void setBillingMonth(int billingMonth) {
		this.billingMonth = billingMonth;
	}
	public int getBillingDay() {
		return billingDay;
	}
	public void setBillingDay(int billingDay) {
		this.billingDay = billingDay;
	}
	public boolean isGroup() {
		return isGroup;
	}
	public void setGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}
	public boolean isYearlyPay() {
		return isYearlyPay;
	}
	public void setYearlyPay(boolean isYearlyPay) {
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

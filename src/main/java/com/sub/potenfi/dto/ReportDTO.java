package com.sub.potenfi.dto;

import java.util.List;

public class ReportDTO {
	private String userId; 
    private int billingMonth; // 월 정보
    private int actualCost;   // 기존 필드
    private String platformId; // 기존 필드
    private int budget; // 사용자 예산

    // Getter and Setter
    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    private int totalCost;   // 해당 월의 총 비용
    private List<String> platformIds; // 플랫폼 ID 목록

    // Getters and Setters
    public int getBillingMonth() {
        return billingMonth;
    }

    public void setBillingMonth(int billingMonth) {
        this.billingMonth = billingMonth;
    }

    public int getActualCost() {
        return actualCost;
    }

    public void setActualCost(int actualCost) {
        this.actualCost = actualCost;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public List<String> getPlatformIds() {
        return platformIds;
    }

    public void setPlatformIds(List<String> platformIds) {
        this.platformIds = platformIds;
    }

    @Override
    public String toString() {
        return "ReportDTO{" +
                "billingMonth=" + billingMonth +
                ", actualCost=" + actualCost +
                ", platformId='" + platformId + '\'' +
                ", totalCost=" + totalCost +
                ", platformIds=" + platformIds +
                '}';
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}

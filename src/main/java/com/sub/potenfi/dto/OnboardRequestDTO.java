package com.sub.potenfi.dto;

import java.util.List;

public class OnboardRequestDTO {
    private String code;
    private String userId;
    private int budget;
    private List<PlatformDTO> platforms;

    public static class PlatformDTO {
        private String platformId;
        private String planId;
        private String isGroup;
        private String isYearlyPay;
        private int billingMonth;
        private int billingDay;

        // Getters and Setters
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

        public String getIsGroup() {
            return isGroup;
        }

        public void setIsGroup(String isGroup) {
            this.isGroup = isGroup;
        }

        public String getIsYearlyPay() {
            return isYearlyPay;
        }

        public void setIsYearlyPay(String isYearlyPay) {
            this.isYearlyPay = isYearlyPay;
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
    }

    // Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public List<PlatformDTO> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<PlatformDTO> platforms) {
        this.platforms = platforms;
    }
}

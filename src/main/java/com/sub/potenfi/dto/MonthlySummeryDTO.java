package com.sub.potenfi.dto;

import java.util.List;

public class MonthlySummeryDTO {
    private String userName;            // 사용자 이름
    private int totalSubscriptionCost;  // 총 구독 금액
    private int totalSubscriptions;     // 총 구독 갯수
    private int remainingBudget;        // 잔여 예산

    private int subscriptionYear;     // 구독 년
    private int subscriptionMonth;    // 구독 월
    
    private List<UserSubscriptionInfoDTO> subscriptionDetails; // 구독 내역 목록

    private AnnualSubscriptionCostDTO annualSubscriptionCost; // 사용자 구독 정보 (별도의 클래스 사용)   
}

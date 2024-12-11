package com.sub.potenfi.dto;

public class AnnualSubscriptionCostDTO {
    private int year;                     // 구독 년도 (int)
    private int currentMonthCost;         // 당월 비용 (MonthlyCost 클래스 사용)
    private int lastMonthCost;            // 1개월 전 비용
    private int twoMonthsBeforeCost;      // 2개월 전 비용
    private int threeMonthsBeforeCost;    // 3개월 전 비용
    private int totalCost;                // 총 비용 합 (int)
    private int costChange;               // 증감 금액 (int, -값 포함 가능)
    private int costChangeAmount;         // 증감률 (double, -값 포함 가능)
}
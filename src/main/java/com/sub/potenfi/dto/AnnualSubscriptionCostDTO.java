package com.sub.potenfi.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class AnnualSubscriptionCostDTO {
    private int year;                     // 구독 년도 (int)/
    private int currentMonthCost;         // 당월 비용/
    private int lastMonthCost;            // 1개월 전 비용/
    private int twoMonthsBeforeCost;      // 2개월 전 비용/
    private int threeMonthsBeforeCost;    // 3개월 전 비용/
    private int totalCost;                // 총 비용 합 (int)/
    private int costChange;               // 증감 금액 (int, -값 포함)
    private int costChangeAmount;         // 증감률 (int, -값 포함)
    private int budgetOverflow;
    private List<Integer> monthlyCosts;
	/*
	 * public void setBudgetOverflow(int max) { // TODO Auto-generated method stub
	 * 
	 * } public void setMonthlyCosts(List<Integer> monthlyCosts) { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 */
}
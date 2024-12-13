package com.sub.potenfi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthlyCost {
    private int cost;                // 구독 비용
    private int costDifference;      // 증감 금액
    private double costChangeRate;   // 증감률
}

package com.sub.potenfi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class MonthlyCost {
    private int cost;                // 구독 비용
    private int costDifference;      // 증감 금액
    private double costChangeRate;   // 증감률
}

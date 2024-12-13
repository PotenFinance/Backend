package com.sub.potenfi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserSubscriptionInfoDTO {
    private String userId;              // 사용자 id
    private String platformId;          // 플랫폼 id
    private String planId;            // 요금제 Id
    private String isGroup;        // 개인구독/그룹구독 여부 Y/N (Y: 그룹구독, N: 개인구독)
    private String isYearlyPay;        // 요금제 연간 구독 여부 Y/N (Y: 매년 결제, N: 매월 결제)
    private String platformType;       // 플랫폼 종류
    private String billingMonth;       // 결제 발생월
    private String billingDay;       // 결제 발생일
    private int actualCost;       // 월 구독 금액(사용자가 지불하는 구독요금)
    private int activeUsers;       // 실제 구독 인원 수
}
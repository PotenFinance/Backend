package com.sub.potenfi.dto;

public class SubscriptionDetailDTO {
    private String platformName;       // 플랫폼 이름
    private int platformMonthlyCost;   // 플랫폼 월 구독 금액
    private int renewalDaysLeft;       // 플랫폼 갱신 잔여 일자
    private String subscriptionPlan;   // 플랫폼 구독 요금제명
    private boolean isGroupSubscription; // 개인구독/그룹구독 여부 (true: 그룹구독, false: 개인구독)
}
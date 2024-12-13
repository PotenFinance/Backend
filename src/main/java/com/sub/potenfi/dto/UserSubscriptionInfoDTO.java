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
    private String platformId;          // 플랫폼 id
    private String platformCode;       // 플랫폼 코드
    private String platformName;       // 플랫폼 이름
    private String platformType;       // 플랫폼 종류
    
    private int platformMonthlyCost;   // 플랫폼 월 구독 금액
    private int renewalDaysLeft;       // 플랫폼 갱신 잔여 일자
    private String subscriptionPlan;   // 플랫폼 구독 요금제명
    private boolean isGroupSubscription; // 개인구독/그룹구독 여부 (true: 그룹구독, false: 개인구독)
}
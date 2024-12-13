package com.sub.potenfi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatformPlanInfoDTO {
    private String planId;          // 요금제 Id
    private String platformId;      // 플랫폼 Id
    private String planName;        // 요금제 이름 
    private String planFee;         // 요금제 가격
    private String maxMembers;      // 최대 이용 가능 인원
    private String isYearlyPlan;    // 연간 요금제 여부 (Y: 연간구독, N: 월구독)
}

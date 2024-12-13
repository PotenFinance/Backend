package com.sub.potenfi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class PlatformPlanInfoDTO {
    private String planId;          // 요금제 Id
    private String platformId;      // 플랫폼 Id
    private String planName;        // 요금제 이름 
    private String planFee;         // 요금제 가격
    private String allowedUsers;    // 최대 이용 가능 인원
}

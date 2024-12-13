package com.sub.potenfi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class PlatformInfoDTO {
    private String platformId;      // 플랫폼 Id
    private String platformCode;    // 플랫폼 코드
    private String platformName;    // 플랫폼 이름
    private String platformType;    // 플랫폼 종류
}

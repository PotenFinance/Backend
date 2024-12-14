package com.sub.potenfi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sub.potenfi.dto.PlatformInfoDTO;
import com.sub.potenfi.dto.PlatformPlanInfoDTO;

@Mapper
public interface PlatformMapper {

    // 구독서비스 TOP5 조회
    List<PlatformInfoDTO> selectTop5Platforms();
    
    // 모든 플랫폼 목록 조회
    List<PlatformInfoDTO> selectAllplatformList();

    // 플랫폼 이름을 기준으로 플랫폼 목록 조회
    List<PlatformInfoDTO> selectPlatformByName(String platformName);

    // 특정 플랫폼에 해당하는 요금제 조회
    List<PlatformPlanInfoDTO> selectPricingPlansByPlatform(String platformId);
}

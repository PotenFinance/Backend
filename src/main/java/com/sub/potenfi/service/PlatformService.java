package com.sub.potenfi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sub.potenfi.dto.PlatformPlanInfoDTO;
import com.sub.potenfi.dto.UserSubscriptionInfoDTO;
import com.sub.potenfi.mapper.PlatformMapper;

@Service
public class PlatformService {
    
    @Autowired
    PlatformMapper platformMapper;

    // 플랫폼 정보 조회
    public UserSubscriptionInfoDTO getPlatformInfo() {
        // 플랫폼 정보 DB 조회
        // return platformMapper.selectPlatformInfo();
        return null;
    }

    // 특정 플랫폼에 해당하는 요금제 조회
    public List<PlatformPlanInfoDTO> getPricingPlansByPlatform(String platformId) {
        // 특정 플랫폼의 요금제 조회
        // return platformMapper.selectPricingPlansByPlatform(platformId);
        return null;
    }
}
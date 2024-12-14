package com.sub.potenfi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sub.potenfi.common.exception.NoContentException;
import com.sub.potenfi.dto.PlatformInfoDTO;
import com.sub.potenfi.dto.PlatformPlanInfoDTO;
import com.sub.potenfi.dto.UserSubscriptionInfoDTO;
import com.sub.potenfi.mapper.PlatformMapper;

@Service
public class PlatformService {
    
    @Autowired
    PlatformMapper platformMapper;

    // 구독서비스 TOP5 조회
    public List<PlatformInfoDTO> getTop5Platforms() {
        List<PlatformInfoDTO> top5Platforms = platformMapper.selectTop5Platforms();
        
        if(top5Platforms.isEmpty()){
            throw new NoContentException("No pricing plans found for platform ID");
        }
        return platformMapper.selectTop5Platforms();
    }

    // 모든 플랫폼 목록 조회
    public List<PlatformInfoDTO> getAllPlatformList() {
        List<PlatformInfoDTO> allPlatformList = platformMapper.selectAllplatformList();

        if(allPlatformList.isEmpty()){
            throw new NoContentException("No platform lists found");
        }

        return platformMapper.selectAllplatformList();
    }

    // 플랫폼 이름을 기준으로 플랫폼 목록 조회
    public List<PlatformInfoDTO> getPlatformListByName(String platformName) {
        List<PlatformInfoDTO> platformListByName = platformMapper.selectPlatformByName(platformName);

        if(platformListByName.isEmpty()){
            throw new NoContentException("No platforms found with the name: " + platformName );
        }

        return platformMapper.selectPlatformByName(platformName);
    };

    // 플랫폼 정보 조회
    public UserSubscriptionInfoDTO getPlatformInfo() {
        // 플랫폼 정보 DB 조회
        // return platformMapper.selectPlatformInfo();
        return null;
    }

    // 특정 플랫폼에 해당하는 요금제 조회
    public List<PlatformPlanInfoDTO> getPricingPlansByPlatform(String platformId) {
        // 특정 플랫폼의 요금제 조회
        return platformMapper.selectPricingPlansByPlatform(platformId);
    }
}
package com.sub.potenfi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sub.potenfi.dto.PlatformInfoDTO;
import com.sub.potenfi.dto.PlatformPlanInfoDTO;
import com.sub.potenfi.service.PlatformService;

@RestController
@RequestMapping("/platforms")
public class PlatformController {

    @Autowired
    private PlatformService platformService;
    
    // 구독서비스 TOP5 조회
    @GetMapping("/top-info")
    public ResponseEntity<List<PlatformInfoDTO>> getTop5PlatformList() {
        return ResponseEntity.ok(platformService.getTop5Platforms());
    }

    // 플랫폼 목록 조회
    @GetMapping("/search")
    public ResponseEntity<List<PlatformInfoDTO>> getPlatformInfo(@RequestParam("platformName") String platformName) {
        // 플랫폼 이름이 제공되지 않은 경우, 모든 플랫폼 정보를 반환하도록 처리
        if (platformName == null || platformName.isEmpty()) {
            return ResponseEntity.ok(platformService.getAllPlatformList());
        } else {
            // platformName이 제공된 경우, 해당 플랫폼 정보를 반환
            return ResponseEntity.ok(platformService.getPlatformListByName(platformName));
        }
    }

    // 플랫폼에 해당하는 요금제 조회
    @GetMapping("/search-plan")
    public ResponseEntity<List<PlatformPlanInfoDTO>> getPricingPlansByPlatform(@RequestParam("platformId") String platformId) {
        // 플랫폼에 해당하는 요금제 조회 로직 처리
        List<PlatformPlanInfoDTO> pricingPlans = platformService.getPricingPlansByPlatform(platformId);
        return ResponseEntity.ok(pricingPlans);
        // return ResponseEntity.ok(platformService.getPricingPlansByPlatform(platformId));
    }
    
}

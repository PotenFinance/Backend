package com.sub.potenfi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sub.potenfi.dto.PlatformInfoDTO;
import com.sub.potenfi.service.PlatformService;

@RestController
@RequestMapping("/platform")
public class PlatformController {

    @Autowired
    private PlatformService platformService;

    // 플랫폼 조회
    @GetMapping("/info")
    public ResponseEntity<List<PlatformInfoDTO>> getPlatformInfo() {
        // 플랫폼 정보 조회 로직 처리
        // return ResponseEntity.ok(platformService.getPlatformInfo());
        return null;
    }
    

    // 플랫폼에 해당하는 요금제 조회
    @GetMapping("/pricingPlans")
    public String getPricingPlansByPlatform(@RequestParam List<String> platformIdList) {
        // 플랫폼에 해당하는 요금제 조회 로직 처리
        // return ResponseEntity.ok(platformService.getPricingPlansByPlatform(platformId));
        return null;
    }
    
}

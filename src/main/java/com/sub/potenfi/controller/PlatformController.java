package com.sub.potenfi.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sub.potenfi.common.exception.NoContentException;
import com.sub.potenfi.dto.PlatformPlanInfoDTO;
import com.sub.potenfi.service.PlatformService;

@RestController
@RequestMapping("/api/platforms")
public class PlatformController {

    @Autowired
    private PlatformService platformService;
    
    // 구독서비스 TOP5 조회
    @GetMapping("/top-info")
    public ResponseEntity<?> getTop5PlatformList() {
        try {
            return ResponseEntity.ok(platformService.getTop5Platforms());
        } catch (NoContentException e) {    // 조회결과 미존재시 빈배열 반환
            return ResponseEntity.status(204).body(Collections.emptyList());
        }
        catch (Exception e) {      // 서버 오류
            return ResponseEntity.status(500).body(Map.of(
                "error", "Internal server error",
                "details", e.getMessage()
            ));
        }
    }

    // 플랫폼 목록 조회
    @GetMapping("/search")
    public ResponseEntity<?> getPlatformInfo(@RequestParam("platformName") String platformName) {
        try {
            // 플랫폼 이름이 제공되지 않은 경우, 모든 플랫폼 정보를 반환하도록 처리
            if (platformName == null || platformName.isEmpty()) {
                return ResponseEntity.ok(platformService.getAllPlatformList());
            } else {
                // platformName이 제공된 경우, 해당 플랫폼 정보를 반환
                return ResponseEntity.ok(platformService.getPlatformListByName(platformName));
            }
        } 
        catch (NoContentException e) {    // 조회결과 미존재시 빈배열 반환
            return ResponseEntity.status(204).body(Collections.emptyList());
        }
        catch (Exception e) {      // 서버 오류
            return ResponseEntity.status(500).body(Map.of(
                "error", "Internal server error",
                "details", e.getMessage()
            ));
        }
    }

    // 플랫폼에 해당하는 요금제 조회
    @GetMapping("/search-plan")
    public ResponseEntity<?> getPricingPlansByPlatform(@RequestParam("platformId") String platformId) {
        try {
            List<PlatformPlanInfoDTO> pricingPlans = platformService.getPricingPlansByPlatform(platformId);
            return ResponseEntity.ok(pricingPlans);
        }
        catch (NoContentException e) {    // 조회결과 미존재시
            return ResponseEntity.status(204).body(Collections.emptyList());
        }
        catch (Exception e) {      // 서버 오류
            return ResponseEntity.status(500).body(Map.of(
                "error", "Internal server error",
                "details", e.getMessage()
            ));
        }
    }
    
}

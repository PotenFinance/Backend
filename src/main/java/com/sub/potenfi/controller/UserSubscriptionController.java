package com.sub.potenfi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sub.potenfi.dto.UserSubscriptionInfoReqDTO;
import com.sub.potenfi.service.UserSubscriptionService;

@RestController
@RequestMapping("/user-subscription")
public class UserSubscriptionController {

    @Autowired
    private UserSubscriptionService userSubService;

    // 구독서비스 TOP5 조회
    @PostMapping("/test")
    public ResponseEntity<Map<String, Object>> getTop5PlatformList(@RequestBody UserSubscriptionInfoReqDTO userInfo) {
        userSubService.registUserSubscriptions(userInfo);

        // 응답 데이터 생성
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "User subscriptions registered successfully");
        response.put("data", "success"); // 요청받은 데이터를 그대로 반환하거나, DB에서 반환한 데이터를 담을 수 있음
        

        return ResponseEntity.ok(response);
    }

    
    
}

package com.sub.potenfi.controller;

import com.sub.potenfi.service.KakaoAuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class KakaoAuthController {

    @Autowired
    private KakaoAuthService kakaoAuthService;

    @Value("${kakao.redirect_uri}")
    private String redirectUri;

    @Value("${kakao.client_id}")
    private String clientId;

    @GetMapping("/loginPage")
    public ResponseEntity<Map<String, String>> loginPage() {
        return ResponseEntity.ok(Map.of(
            "clientId", clientId,
            "redirectUri", redirectUri
        ));
    }

    @GetMapping("/kakao/callback")
    public ResponseEntity<Map<String, Object>> kakaoCallback(@RequestParam String code) {
    	System.out.println("##########code : "+code);
        try {
            // 1. 액세스 토큰 가져오기
            String accessToken = kakaoAuthService.getAccessToken(code);

            // 2. 사용자 정보 가져오기
            Map<String, Object> userInfo = kakaoAuthService.getUserInfo(accessToken);

            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to process Kakao callback", "details", e.getMessage()));
        }
    }

}

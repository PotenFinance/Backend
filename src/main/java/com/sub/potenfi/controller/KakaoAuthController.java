package com.sub.potenfi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.sub.potenfi.dto.KakaoCallbackResponseDTO;
import com.sub.potenfi.dto.KakaoUserInfoDTO;
import com.sub.potenfi.dto.OnboardRequestDTO;
import com.sub.potenfi.dto.UserDTO;
import com.sub.potenfi.service.KakaoAuthService;
import com.sub.potenfi.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class KakaoAuthController {
	
	private static final Logger logger = LoggerFactory.getLogger(KakaoAuthController.class);

    @Autowired
    private KakaoAuthService kakaoAuthService;
    
    @Autowired
    private UserService userService;
    
 // /api/auth/kakao/callback 메서드 수정
    @GetMapping("/kakao/callback")
    public ResponseEntity<KakaoCallbackResponseDTO> kakaoCallback(@RequestParam String code) {
        logger.info("Received Kakao callback with code: {}", code);

        try {
            // Step 1: Fetch tokens
            Map<String, String> tokens = kakaoAuthService.getTokens(code);
            String accessToken = tokens.get("access_token");
            String refreshToken = tokens.get("refresh_token");

            // Step 2: Fetch user info
            KakaoUserInfoDTO userInfo = kakaoAuthService.getUserInfo(accessToken);

            if (userInfo == null || 
            	    userInfo.getId() == null || 
            	    userInfo.getId().isEmpty()) {
            	    logger.warn("UserInfo is null or missing essential fields.");
            	    throw new RuntimeException("Failed to fetch user info from Kakao.");
            	}

            // Step 3: Create DTO
            KakaoCallbackResponseDTO responseDTO = new KakaoCallbackResponseDTO(
                userInfo.getId(),
                userInfo.getConnectedAt(),
                userInfo.getProperties() != null ? userInfo.getProperties().getNickname() : null,
                userInfo.getKakaoAccount() != null ? userInfo.getKakaoAccount().getEmail() : null,
                accessToken,
                refreshToken
            );

            return ResponseEntity.ok(responseDTO);

        } catch (HttpClientErrorException e) {
            logger.error("Error while fetching tokens: {}", e.getMessage(), e);
            return ResponseEntity.status(400).build();
        } catch (Exception e) {
            logger.error("Unknown error: {}", e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }



    // /api/auth/kakao/onboard 메서드 수정
    @PostMapping("/kakao/onboard")
    public ResponseEntity<Map<String, Object>> onboardUser(@RequestParam String code) {
        try {
            //String code = request.getCode();
            Map<String, String> tokens = kakaoAuthService.getTokens(code);
            String accessToken = tokens.get("access_token");
            String refreshToken = tokens.get("refresh_token");

            KakaoUserInfoDTO userInfo = kakaoAuthService.getUserInfo(accessToken);

            return ResponseEntity.ok(Map.of(
                    "id", userInfo.getId(),
                    "connected_at", userInfo.getConnectedAt(),
                    "properties", Map.of(
                        "nickname", userInfo.getProperties() != null ? userInfo.getProperties().getNickname() : null
                    ),
                    "kakao_account", Map.of(
                        "profile_nickname_needs_agreement", false,
                        "profile", Map.of(
                            "nickname", userInfo.getProperties() != null ? userInfo.getProperties().getNickname() : null,
                            "is_default_nickname", false
                        ),
                        "has_email", userInfo.getKakaoAccount() != null && userInfo.getKakaoAccount().getEmail() != null,
                        "email_needs_agreement", false,
                        "is_email_valid", true,
                        "is_email_verified", true,
                        "email", userInfo.getKakaoAccount() != null ? userInfo.getKakaoAccount().getEmail() : null,
                        "access_token", accessToken,
                        "refresh_token", refreshToken
                )
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(Map.of(
                "error", "Onboarding failed",
                "details", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "error", "Internal server error",
                "details", e.getMessage()
            ));
        }
    }

    @PostMapping("/kakao/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String accessToken) {
        try {
        	Map<String, Object> result = kakaoAuthService.logout(accessToken);
	        if (result.isEmpty()) {
	            return ResponseEntity.status(400).body("Logout failed. Please check the token.");
	        }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "error", "Internal server error",
                "details", e.getMessage()
            ));
        }
    }

    @PostMapping("/kakao/unlink")
    public ResponseEntity<?> unlink(@RequestHeader("Authorization") String accessToken) {
        try {
            boolean success = kakaoAuthService.unlink(accessToken);
            if (!success) {
                return ResponseEntity.status(400).body("Unlink failed. Invalid token.");
            }
            
            // 사용자 데이터 삭제 로직 추가
            String userId = kakaoAuthService.getUserIdFromAccessToken(accessToken);
            userService.deleteUser(userId);
            
            return ResponseEntity.ok("Unlink successful.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "error", "Internal server error",
                "details", e.getMessage()
            ));
        }
    }

    @PostMapping("/kakao/refresh")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody Map<String, String> tokens) {
        try {
            String newAccessToken = kakaoAuthService.refreshAccessToken(tokens.get("refresh_token"));
            return ResponseEntity.ok(Map.of("access_token", newAccessToken));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
            		"error", "Failed to refresh access token",
            		"details", e.getMessage()
            ));
        }
    }
}

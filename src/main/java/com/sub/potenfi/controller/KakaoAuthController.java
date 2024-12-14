package com.sub.potenfi.controller;

import com.sub.potenfi.dto.OnboardRequestDTO;
import com.sub.potenfi.dto.UserDTO;
import com.sub.potenfi.service.KakaoAuthService;
import com.sub.potenfi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class KakaoAuthController {

    @Autowired
    private KakaoAuthService kakaoAuthService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/kakao/callback")
    public ResponseEntity<Map<String, Object>> kakaoCallback(@RequestParam String code) {
        try {
            // 액세스 토큰 및 리프레시 토큰 가져오기
            Map<String, String> tokens = kakaoAuthService.getTokens(code);
            String accessToken = tokens.get("access_token");

            // 사용자 정보 가져오기, DB 저장
            Map<String, Object> userInfo = kakaoAuthService.getUserInfo(accessToken);
            boolean isRegistered = kakaoAuthService.isUserRegistered(userInfo.get("id").toString());

            // 응답값 추가
            userInfo.put("isRegistered", isRegistered ? "Y" : "N");
            
            if (!isRegistered) {
                // 등록되지 않은 경우 토큰을 null로 설정
                tokens.put("access_token", null);
                tokens.put("refresh_token", null);
            }
            
            userInfo.putAll(tokens);

            return ResponseEntity.ok(userInfo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(Map.of(
                "error", "Authentication failed",
                "details", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "error", "Internal server error",
                "details", e.getMessage()
            ));
        }
    }

    @PostMapping("/kakao/onboard")
    public ResponseEntity<?> onboardUser(@RequestBody OnboardRequestDTO request) {
    	System.out.println("Request: " + request);
    	try {
            // 요청 데이터 파싱
            String code = request.getCode();
            String userId = request.getUserId();
            int budget = request.getBudget();
            List<OnboardRequestDTO.PlatformDTO> platforms = request.getPlatforms();

            // 카카오 API 호출하여 새로운 토큰 발급
            Map<String, String> tokens = kakaoAuthService.getTokens(code);
            String accessToken = tokens.get("access_token");
            String refreshToken = tokens.get("refresh_token");

            // user 테이블에 사용자 정보 저장
            UserDTO userDto = new UserDTO();
            userDto.setUserId(userId);
            userDto.setBudget(budget);
            userService.registerUser(userDto);

            // 응답 반환
            return ResponseEntity.ok(Map.of(
                "access_token", accessToken,
                "refresh_token", refreshToken,
                "message", "User onboarded and authenticated successfully"
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

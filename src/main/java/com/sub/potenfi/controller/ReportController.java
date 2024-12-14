package com.sub.potenfi.controller;

import com.sub.potenfi.dto.ReportDTO;
import com.sub.potenfi.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/subscriptions")
public class ReportController {

	@Autowired
    private ReportService reportService;
	
	private static final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";


    @GetMapping("/report")
    public ResponseEntity<Map<String, Object>> getMonthlyReport(
    		@RequestHeader("Authorization") String token) {

    	String userId = extractUserIdFromToken(token);
    	
        // 서비스 호출
        Map<String, Object> report = reportService.getMonthlyReport(userId);

        return ResponseEntity.ok(report);
    }

    private String extractUserIdFromToken(String token) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token); // Bearer 토큰 포함

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    KAKAO_USER_INFO_URL, requestEntity, Map.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                Map<String, Object> responseBody = response.getBody();
                if (responseBody != null && responseBody.containsKey("id")) {
                    return responseBody.get("id").toString(); // userId 추출
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to fetch user information: " + e.getMessage());
        }

        throw new IllegalArgumentException("Invalid token: Unable to fetch user information");
    }
}
package com.sub.potenfi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sub.potenfi.util.KakaoConstants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class KakaoAuthService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${kakao.client_id}")
    private String clientId;

    @Value("${kakao.redirect_uri}")
    private String redirectUri;

    public String getAccessToken(String code) {
        try {
            // 1. HTTP 요청 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // 2. 요청 바디 설정
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", clientId);  // REST API Key
            params.add("redirect_uri", redirectUri);  // Redirect URI
            params.add("code", code);  // Authorization Code

            // 3. HTTP 요청 엔티티 생성
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            // 4. 카카오 서버에 POST 요청
            ResponseEntity<String> response = restTemplate.exchange(
                KakaoConstants.TOKEN_URL, 
                HttpMethod.POST, 
                request, 
                String.class
            );

            // 5. 응답 로그 확인
            System.out.println("Kakao Token API Response: " + response.getBody());

            // 6. 응답 처리 (액세스 토큰 추출)
            return parseAccessToken(response.getBody());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get access token", e);
        }
    }


    private String parseAccessToken(String responseBody) {
        try {
            // JSON 문자열을 JsonNode 객체로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);

            // "access_token" 키의 값을 추출
            return rootNode.get("access_token").asText();
        } catch (Exception e) {
            // JSON 파싱 실패 시 예외 처리
            throw new RuntimeException("Failed to parse access token from response", e);
        }
    }

    public Map<String, Object> getUserInfo(String accessToken) {
        try {
            // 1. 사용자 정보 요청 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + accessToken);

            // 2. HTTP 요청 엔티티 생성
            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            // 3. 카카오 서버에 사용자 정보 요청
            ResponseEntity<Map> response = restTemplate.exchange(
            	KakaoConstants.USER_INFO_URL,
                HttpMethod.GET,
                requestEntity,
                Map.class
            );

            // 사용자 정보를 반환
            return response.getBody();
        } catch (Exception e) {
            // 사용자 정보 가져오기 실패 시 예외 처리
            throw new RuntimeException("Failed to get user info", e);
        }
    }
}

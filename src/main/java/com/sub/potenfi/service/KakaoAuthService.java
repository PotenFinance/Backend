package com.sub.potenfi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sub.potenfi.util.KakaoConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

@Service
public class KakaoAuthService {

    RestTemplate restTemplate = new RestTemplate();

    @Value("${kakao.client_id}")
    private String clientId;

    @Value("${kakao.redirect_uri}")
    private String redirectUri;

    public String getAccessToken(String code) {
        // 1. HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        
        String url = KakaoConstants.TOKEN_URL;
        
        // 2. 요청 바디 설정 (MultiValueMap 사용)
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        // 3. HTTP 요청 엔티티 생성
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        // 4. 카카오 서버에 POST 요청
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        String body = response.getBody();

        // 5. 응답 처리 (액세스 토큰 추출)
        // JSON 응답을 파싱하여 액세스 토큰 반환 (Jackson 사용 등)
        // 예시: {"access_token":"abc123", ...} 중 "access_token" 추출
        return parseAccessToken(response.getBody());
    }
    
    private String parseAccessToken(String responseBody) {
    	
    	final ObjectMapper objectMapper = new ObjectMapper();

        try {
            // JSON 문자열을 JsonNode 객체로 변환
            JsonNode rootNode = objectMapper.readTree(responseBody);

            // "access_token" 키의 값을 추출
            String accessToken = rootNode.get("access_token").asText();

            return accessToken; // 추출된 액세스 토큰 반환
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse access token from response", e);
        }
    }

	public Map<String, Object> getUserInfo(String accessToken) {
	    String url = "https://kapi.kakao.com/v2/user/me";
	    
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Authorization", "Bearer " + accessToken);
	    
	    HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
	    
	    ResponseEntity<Map> response = restTemplate.exchange(
	        url,
	        HttpMethod.GET,
	        requestEntity,
	        Map.class
	    );
	    System.out.println("Received user info response: " + response.getBody());

	    return response.getBody();
	}
}

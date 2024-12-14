package com.sub.potenfi.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sub.potenfi.controller.KakaoAuthController;
import com.sub.potenfi.dto.UserDTO;
import com.sub.potenfi.mapper.UserMapper;
import com.sub.potenfi.util.KakaoConstants;

@Service
public class KakaoAuthService {
	
	private static final Logger logger = LoggerFactory.getLogger(KakaoAuthController.class);

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${kakao.clientId}")
    private String clientId;

    @Value("${kakao.redirectUri}")
    private String redirectUri;

    @Autowired
    private UserMapper userDao;

    public Map<String, String> getTokens(String code) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", clientId); // 카카오 개발자 콘솔 값과 일치해야 함
            params.add("redirect_uri", redirectUri); // 카카오 개발자 콘솔에 등록된 URI
            params.add("code", code);
            // 로그로 요청 파라미터 출력
            logger.info("Requesting tokens with the following parameters:");
            logger.info("grant_type: {}", params.getFirst("grant_type"));
            logger.info("client_id: {}", params.getFirst("client_id"));
            logger.info("redirect_uri: {}", params.getFirst("redirect_uri"));
            logger.info("code: {}", params.getFirst("code"));
            System.out.println("Requesting tokens with code: " + code);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                KakaoConstants.tokenUrl, // https://kauth.kakao.com/oauth/token
                HttpMethod.POST,
                request,
                String.class
            );

            if (!response.getStatusCode().is2xxSuccessful()) {
                System.err.println("Token API call failed: " + response.getBody());
                throw new RuntimeException("Failed to fetch tokens: " + response.getStatusCode());
            }

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            String accessToken = rootNode.path("access_token").asText(null);
            String refreshToken = rootNode.path("refresh_token").asText(null);

            if (accessToken == null || refreshToken == null) {
                System.err.println("Invalid tokens in response: " + response.getBody());
                throw new RuntimeException("Failed to fetch valid tokens");
            }

            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token", accessToken);
            tokens.put("refresh_token", refreshToken);

            return tokens;

        } catch (Exception e) {
            System.err.println("Error while fetching tokens: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to get tokens", e);
        }
    }


    public String refreshAccessToken(String refreshToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "refresh_token");
            params.add("client_id", clientId);
            params.add("refresh_token", refreshToken);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                KakaoConstants.tokenUrl,
                HttpMethod.POST,
                request,
                String.class
            );

            return parseAccessToken(response.getBody());
        } catch (Exception e) {
            throw new RuntimeException("Failed to refresh access token", e);
        }
    }

    private String parseAccessToken(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            return rootNode.get("access_token").asText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse access token", e);
        }
    }

    public Map<String, Object> getUserInfo(String accessToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + accessToken);

            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                KakaoConstants.userInfoUrl,
                HttpMethod.GET,
                requestEntity,
                Map.class
            );

            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get user info", e);
        }
    }

    public void saveOrUpdateUser(Map<String, Object> userInfo) {
        String kakaoId = userInfo.get("id").toString();
        String email = userInfo.getOrDefault("email", "unknown@kakao.com").toString();
        String name = ((Map<String, Object>) userInfo.get("properties")).get("nickname").toString();

        UserDTO userDto = new UserDTO();
        userDto.setUserId(kakaoId);
        userDto.setEmail(email);
        userDto.setUserName(name);

        if (userDao.getUserById(kakaoId) == null) {
            userDao.registerUser(userDto);
        } else {
            userDao.updateUser(userDto);
        }
    }

    public Map<String, Object> logout(String accessToken) {
        String url = KakaoConstants.logoutUrl;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                Map.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new HashMap<>();
    }

    public boolean unlink(String accessToken) {
        String url = KakaoConstants.unlinkUrl;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                Map.class
            );

            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public String getUserIdFromAccessToken(String accessToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + accessToken);

            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                KakaoConstants.userInfoUrl,
                HttpMethod.GET,
                requestEntity,
                String.class
            );

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            return rootNode.get("id").asText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get user ID from access token", e);
        }
    }


	public boolean isUserRegistered(String userId) {
		
		return userDao.getUserById(userId) != null;
	}
}

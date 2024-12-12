package com.sub.potenfi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sub.potenfi.dto.UserDTO;
import com.sub.potenfi.mapper.UserMapper;
import com.sub.potenfi.util.KakaoConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class KakaoAuthService {

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
            params.add("client_id", clientId);
            params.add("redirect_uri", redirectUri);
            params.add("code", code);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                KakaoConstants.tokenUrl,
                HttpMethod.POST,
                request,
                String.class
            );

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            // 액세스 토큰과 리프레시 토큰 추출
            String accessToken = rootNode.get("access_token").asText();
            String refreshToken = rootNode.get("refresh_token").asText();

            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token", accessToken);
            tokens.put("refresh_token", refreshToken);

            return tokens;
        } catch (Exception e) {
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

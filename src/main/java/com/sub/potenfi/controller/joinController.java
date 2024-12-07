package com.sub.potenfi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class joinController {

    @Value("${kakao.client_id}")
    private String client_id;

    @Value("${kakao.redirect_uri}")
    private String redirect_uri;

//    @GetMapping("/loginPage")
//    public String loginPage(Model model) {
//        model.addAttribute("clientId", client_id);
//        model.addAttribute("redirectUri", redirect_uri);
//        return "login.html"; // Thymeleaf 템플릿 파일 이름
//    }
    
    @PostMapping("/oauth/kakao")
    public String kakaoCallback(@RequestParam("code") String code, Model model) {
        // 1. 인증 코드로 액세스 토큰 요청
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "YOUR_CLIENT_ID"); // 카카오 REST API 키
        params.add("redirect_uri", "http://localhost:8080/loginPage"); // 리다이렉트 URI
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        // 카카오 서버로 HTTP 요청
        ResponseEntity<String> response = restTemplate.postForEntity(
                "https://kauth.kakao.com/oauth/token",
                kakaoTokenRequest,
                String.class
        );

        // 2. 액세스 토큰 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        String accessToken = "";
        try {
            JsonNode responseJson = objectMapper.readTree(response.getBody());
            accessToken = responseJson.get("access_token").asText();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // 3. 액세스 토큰으로 사용자 정보 요청
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + accessToken);
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers2);

        ResponseEntity<String> profileResponse = restTemplate.postForEntity(
                "https://kapi.kakao.com/v2/user/me",
                kakaoProfileRequest,
                String.class
        );

        // 4. 사용자 정보 파싱
        String userEmail = "";
        try {
            JsonNode profileJson = objectMapper.readTree(profileResponse.getBody());
            userEmail = profileJson.get("kakao_account").get("email").asText();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        model.addAttribute("userEmail", userEmail);
        return "loginSuccess"; // 로그인 성공 시 이동할 페이지
    }

}

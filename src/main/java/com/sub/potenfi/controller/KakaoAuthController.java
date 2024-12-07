package com.sub.potenfi.controller;

import com.sub.potenfi.service.KakaoAuthService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class KakaoAuthController {

    @Autowired
    private KakaoAuthService kakaoAuthService;

    @Value("${kakao.redirect_uri}")
    private String redirectUri;

    @Value("${kakao.client_id}")
    private String clientId;

    @GetMapping("/loginPage")
    public String loginPage(Model model) {
        model.addAttribute("clientId", clientId);
        model.addAttribute("redirectUri", redirectUri);
        return "login.html";
    }

    @GetMapping("/kakao/callback")
    public String kakaoCallback(@RequestParam String code, Model model) {
    	// 1. 액세스 토큰 가져오기
    	String accessToken = kakaoAuthService.getAccessToken(code);
        // 2. 사용자 정보 가져오기
        Map<String, Object> userInfo = kakaoAuthService.getUserInfo(accessToken);
        
        // 3. 사용자 정보를 모델에 추가
        model.addAttribute("userInfo", userInfo);
        
        return "redirect:/loginSuccess"; // 리다이렉트
    }

    @GetMapping("/loginSuccess")
    public String loginSuccessPage(Model model) {
        return "success.html"; // templates/loginSuccess.html 매핑
    }
}

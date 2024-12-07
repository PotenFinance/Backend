package com.sub.potenfi.controller;

import com.sub.potenfi.service.KakaoAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        String accessToken = kakaoAuthService.getAccessToken(code);
        model.addAttribute("accessToken", accessToken);
        return "redirect:/success";
    }
}

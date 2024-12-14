package com.sub.potenfi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer  {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 모든 경로에 대해 CORS 허용
        registry.addMapping("/**")  // 모든 엔드포인트
        .allowedOrigins("http://www.potenfinance.site")  // 허용할 출처 (클라이언트의 URL)
        .allowedMethods("GET", "POST")  // 허용할 HTTP 메소드
        .allowedHeaders("*")  // 허용할 헤더
        .allowCredentials(true);  // 쿠키 및 인증 정보 허용
    }
    
    
}

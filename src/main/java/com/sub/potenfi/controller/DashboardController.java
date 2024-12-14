package com.sub.potenfi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sub.potenfi.dto.MonthlySummeryDTO;
import com.sub.potenfi.service.MonthlySummeryService;
import com.sub.potenfi.util.StringUtils;


@RestController
@RequestMapping("/subscriptions")
public class DashboardController {
    
    @Autowired
    private MonthlySummeryService monthlySummeryService;
    
    // 홈 화면 - 사용자는 이번달 총 구독 비용과 절약 가능 금액을 수치를 통해 한눈에 확인할 수 있다
    @GetMapping("/summary")
    public ResponseEntity<MonthlySummeryDTO> getMonthlySummary(@RequestParam("userId") String userId) {
        // platformIdList가 null 또는 빈 리스트인 경우 처리
        if (StringUtils.isNullOrEmpty(userId)) {
            throw new IllegalArgumentException("");
            // "Platform ID list cannot be empty or null"
        }
        try {
            // 유효한 userId가 있을 경우, 서비스를 통해 월간 요약 정보 가져오기
            System.out.println("================== ***** =======================");
            System.out.println("userId : " + userId);
            System.out.println("================== ***** =======================");
            MonthlySummeryDTO monthlySummary = monthlySummeryService.getMonthlySummary(userId);
            
            if (monthlySummary == null) {
                // 월간 요약 정보를 찾을 수 없는 경우
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body(null); // 404 Not Found
            }
            
            return ResponseEntity.ok(monthlySummary); // 200 OK 정상 응답
        } catch (IllegalArgumentException e) {
            System.out.println("================== ***** =======================");
            System.out.println(e.getMessage());
            System.out.println("================== ***** =======================");
            
            // 잘못된 userId나 다른 입력 오류 발생 시
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(null); // 400 Bad Request
        } catch (Exception e) {
            System.out.println("================== ***** =======================");
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.out.println("================== ***** =======================");
            // 예기치 못한 서버 오류 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(null); // 500 Internal Server Error
        }
    }
}

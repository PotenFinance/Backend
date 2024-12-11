package com.sub.potenfi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sub.potenfi.dto.MonthlySummeryDTO;
import com.sub.potenfi.service.MonthlySummeryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/subscriptions")
public class DashboardController {
    
    @Autowired
    private MonthlySummeryService monthlySummeryService;
    
    // 홈 화면 - 사용자는 이번달 총 구독 비용과 절약 가능 금액을 수치를 통해 한눈에 확인할 수 있다
    @GetMapping("/summary")
    public ResponseEntity<MonthlySummeryDTO> getMonthlySummary(@RequestParam String userId) {
        // return ResponseEntity.ok(monthlySummeryService.getMonthlySummary());
        return null;
    }
}

package com.sub.potenfi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.sub.potenfi.dto.MonthlySummeryDTO;
import com.sub.potenfi.mapper.MonthlySummeryMapper;

@Service
public class MonthlySummeryService {

    @Autowired
    MonthlySummeryMapper monthlySummeryMapper;
    
    // 홈 화면 - 사용자는 이번달 총 구독 비용과 절약 가능 금액을 수치를 통해 한눈에 확인할 수 있다
    public ResponseEntity<MonthlySummeryDTO> getMonthlySummary(@RequestParam String userId) {
        return null;
    }
}

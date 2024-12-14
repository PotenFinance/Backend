package com.sub.potenfi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sub.potenfi.common.exception.NoContentException;
import com.sub.potenfi.dto.MonthlySummeryDTO;
import com.sub.potenfi.service.MonthlySummeryService;


@RestController
@RequestMapping("/api/subscriptions")
public class DashboardController {
    
    @Autowired
    private MonthlySummeryService monthlySummeryService;
    
    // 홈 화면 - 사용자는 이번달 총 구독 비용과 절약 가능 금액을 수치를 통해 한눈에 확인할 수 있다
    @GetMapping("/summary")
    public ResponseEntity<?> getMonthlySummary(@RequestParam("userId") String userId) {
        try {
            // 유효한 userId가 있을 경우, 서비스를 통해 월간 요약 정보 가져오기
            MonthlySummeryDTO monthlySummary = monthlySummeryService.getMonthlySummary(userId);
            
            if (monthlySummary == null) {
                // 월간 요약 정보를 찾을 수 없는 경우
                return ResponseEntity.status(204)
                                     .body(Map.of(
                                        "error", "Bad Request",
                                        "details", "Monthly data not found."
                                    )); // 204 Not Found
            }
            
            return ResponseEntity.ok(monthlySummary); // 200 OK 정상 응답
        }
        catch (NoContentException e) {    // 조회결과 미존재시
            return ResponseEntity.status(204).body(Map.of(
                "error", "No results found.",
                "details", e.getMessage()
            ));
        }
        catch (IllegalArgumentException e) {
            // 잘못된 userId나 다른 입력 오류 발생 시
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "error", "Bad Request",
                "details", e.getMessage()
            )); // 400 Bad Request
        } catch (Exception e) {
            // 예기치 못한 서버 오류 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of(
                                    "error", "Internal Server Error",
                                    "details", e.getMessage()
                                )); // 500 Internal Server Error
        }
    }
}

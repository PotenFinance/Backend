package com.sub.potenfi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sub.potenfi.dto.ReportDTO;
import com.sub.potenfi.mapper.ReportMapper;

@Service
public class ReportService {

    @Autowired
    private ReportMapper reportMapper;

    public Map<String, Object> getMonthlyReport(String userId) {
        List<ReportDTO> reports = reportMapper.fetchMonthlyReport(userId);
        System.out.println("Fetched Reports: " + reports);

        // 필요하다면 여기서 reports 리스트를 가공
        return processReports(reports);
    }

    public Map<String, Object> processReports(List<ReportDTO> reports) {
        Map<String, Object> response = new HashMap<>();

        // 사용자 budget을 설정 (첫 번째 ReportDTO에서 budget 추출)
        if (!reports.isEmpty()) {
            response.put("budget", reports.get(0).getBudget());
        } else {
            response.put("budget", 0); // 데이터가 없는 경우 기본값 설정
        }

        // monthly_report 생성
        Map<Integer, Map<String, Object>> monthlyReport = reports.stream()
            .collect(Collectors.groupingBy(
                ReportDTO::getBillingMonth, // billingMonth 기준으로 그룹화
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    monthlyReports -> {
                        Map<String, Object> monthlyData = new HashMap<>();
                        monthlyData.put("total_cost", monthlyReports.stream()
                            .mapToInt(ReportDTO::getActualCost) // actualCost 합산
                            .sum()
                        );
                        monthlyData.put("platform_id", monthlyReports.stream()
                            .map(ReportDTO::getPlatformId)
                            .distinct()
                            .collect(Collectors.toList())
                        );
                        return monthlyData;
                    }
                )
            ));

        response.put("monthly_report", monthlyReport);

        // userId 추출 및 출력 (디버깅 또는 로깅용)
        List<String> userIds = reports.stream()
            .map(ReportDTO::getUserId)
            .distinct()
            .collect(Collectors.toList());

        System.out.println("Extracted userIds: " + userIds);

        return response;
    }

}

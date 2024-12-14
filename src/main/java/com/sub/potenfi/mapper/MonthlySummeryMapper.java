package com.sub.potenfi.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sub.potenfi.dto.AnnualSubscriptionCostDTO;
import com.sub.potenfi.dto.MonthlySummeryDTO;
import com.sub.potenfi.dto.UserSubscriptionInfoDTO;

@Mapper
public interface MonthlySummeryMapper {
    
    // 총 구독 금액 및 구독 개수
    MonthlySummeryDTO getTotalSubscriptionInfo(String userId);

    // 구독 내역 목록
    List<UserSubscriptionInfoDTO> getSubscriptionDetails(String userId);

    // 연간 구독 비용 정보
    AnnualSubscriptionCostDTO getAnnualSubscriptionCost(String userId);

    List<Integer> getMonthlyCosts(@Param("userId") String userId, @Param("year") int year);

	List<Map<String, Object>> getMonthlyCostsByMonth(@Param("userId") String userId, @Param("year") int year);
}

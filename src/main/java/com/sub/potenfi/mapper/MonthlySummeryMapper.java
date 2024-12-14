package com.sub.potenfi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

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
}

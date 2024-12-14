package com.sub.potenfi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sub.potenfi.dto.UserSubscriptionInfoDTO;

@Mapper
public interface UserSubscriptionMapper {
    // 사용자 구독 정보 등록
    void insertUserSubscriptions(List<UserSubscriptionInfoDTO> subscriptions);
    
    int updatePlatform(@Param("userId") String userId, @Param("currentPlatformId") String currentPlatformId, @Param("platformId") String platformId);

    int updatePlan(@Param("userId") String userId, @Param("platformId") String platformId, @Param("planId") String planId);

    int updateBillingDate(@Param("userId") String userId, @Param("platformId") String platformId,
                          @Param("billingMonth") String string, @Param("billingDay") String string2);

    int updateAdditionalFields(@Param("userId") String userId, @Param("platformId") String platformId,
                               @Param("isGroup") String string, @Param("isYearlyPay") String string2,
                               @Param("actualCost") int actualCost, @Param("activeUsers") int activeUsers);

}
package com.sub.potenfi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sub.potenfi.dto.UserSubscriptionInfoDTO;
import com.sub.potenfi.dto.UserSubscriptionInfoReqDTO;
import com.sub.potenfi.mapper.UserSubscriptionMapper;

@Service
public class UserSubscriptionService {
    
    @Autowired
    UserSubscriptionMapper userSubscriptionMapper;

    // 회원가입시 사용자 구독 정보 등록
    public void registUserSubscriptions(UserSubscriptionInfoReqDTO userSubscriptionInfoReqDTO) {
        // userId를 UserSubscriptionInfoDTO의 각 항목에 세팅
        List<UserSubscriptionInfoDTO> subscriptions = userSubscriptionInfoReqDTO.getPlatforms()
                .stream()
                .map(dto -> {
                    // userId -> UserSubscriptionInfoDTO 에 세팅
                    return UserSubscriptionInfoDTO.builder()
                            .userId(userSubscriptionInfoReqDTO.getUserId())
                            .platformId(dto.getPlatformId())
                            .planId(dto.getPlanId())
                            .isGroup(dto.getIsGroup())
                            .isYearlyPay(dto.getIsYearlyPay())
                            .billingMonth(dto.getBillingMonth())
                            .billingDay(dto.getBillingDay())
                            .actualCost(dto.getActualCost())
                            .activeUsers(dto.getActiveUsers())
                            .platformType(dto.getPlatformType())
                            .build();
                })
                .collect(Collectors.toList());

                userSubscriptionMapper.insertUserSubscriptions(subscriptions);
} 
}

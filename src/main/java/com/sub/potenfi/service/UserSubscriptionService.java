package com.sub.potenfi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sub.potenfi.dto.UserDTO;
import com.sub.potenfi.dto.UserSubscriptionInfoDTO;
import com.sub.potenfi.dto.UserSubscriptionInfoReqDTO;
import com.sub.potenfi.mapper.UserMapper;
import com.sub.potenfi.mapper.UserSubscriptionMapper;

@Service
public class UserSubscriptionService {

    @Autowired
	UserSubscriptionMapper userSubscriptionMapper;
    UserMapper userDao;

    // 회원가입시 사용자 구독 정보 등록
    public void registUserSubscriptions(UserSubscriptionInfoReqDTO userSubscriptionInfoReqDTO) {
        String userId = userSubscriptionInfoReqDTO.getUserId();
        int budget = userSubscriptionInfoReqDTO.getBudget();

        // 고객 조회
        UserDTO user = userDao.getUserById(userId);

        if (user == null) {
            throw new RuntimeException("User not found: " + userId);
        }
        
        // update 값 세팅
        user.setBudget(budget);

        // userId -> UserSubscriptionInfoDTO 에 세팅
        List<UserSubscriptionInfoDTO> subscriptions = userSubscriptionInfoReqDTO.getPlatforms()
                .stream()
                .map(dto -> {
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
        
        // 고객 정보 update
        userDao.updateUser(user);

        // 고객 구독 정보 update
        userSubscriptionMapper.insertUserSubscriptions(subscriptions);
    }

    
    public void updatePlatform(String userId, String currentPlatformId, String platformId) {
        userSubscriptionMapper.updatePlatform(userId, currentPlatformId, platformId);
    }

    public void updatePlan(String userId, String platformId, String planId) {
        userSubscriptionMapper.updatePlan(userId, platformId, planId);
    }

    public void updateBillingDate(String userId, String platformId, int billingMonth, int billingDay) {
        userSubscriptionMapper.updateBillingDate(userId, platformId, billingMonth, billingDay);
    }

    public void updateAdditionalFields(String userId, String platformId, boolean isGroup, boolean isYearlyPay, int actualCost, int activeUsers) {
        userSubscriptionMapper.updateAdditionalFields(userId, platformId, isGroup, isYearlyPay, actualCost, activeUsers);
    }
}

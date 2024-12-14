package com.sub.potenfi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sub.potenfi.dto.MonthlySummeryDTO;
import com.sub.potenfi.dto.UserDTO;
import com.sub.potenfi.dto.UserSubscriptionInfoDTO;
import com.sub.potenfi.mapper.MonthlySummeryMapper;
import com.sub.potenfi.mapper.UserMapper;

@Service
public class MonthlySummeryService {

    @Autowired
    MonthlySummeryMapper monthlySummeryMapper;
    
    @Autowired
    UserMapper userMapper;
    
    // 홈 화면 - 사용자는 이번달 총 구독 비용과 절약 가능 금액을 수치를 통해 한눈에 확인할 수 있다
    public MonthlySummeryDTO getMonthlySummary(String userId) {
        System.out.println("userId : " + userId);
        
        // return DTO
        MonthlySummeryDTO monthlySummeryDTO = new MonthlySummeryDTO();
        
        // 1. 사용자 정보 조회
        UserDTO user = userMapper.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found: " + userId);
        } else {
            System.out.println("Set user data");
            monthlySummeryDTO.setUserid(user.getUserId());
            monthlySummeryDTO.setUserName(user.getUserName());
        }
        System.out.println("1) getUserById - monthlySummeryDTO : " + monthlySummeryDTO);
        
        // 2. 총 구독 금액 및 구독 개수
        monthlySummeryDTO = monthlySummeryMapper.getTotalSubscriptionInfo(userId);
        System.out.println("2) getTotalSubscriptionInfo - monthlySummeryDTO : " + monthlySummeryDTO);

        // 3. 구독 내역 목록
        List<UserSubscriptionInfoDTO> UserSubscriptionListDTO = monthlySummeryMapper.getSubscriptionDetails(userId);
        monthlySummeryDTO.setSubscriptionDetails(UserSubscriptionListDTO);
        System.out.println("3) getSubscriptionDetails - monthlySummeryDTO : " + monthlySummeryDTO);


        // 4. annualSubscriptionCost (사용자 연간 구독 비용 정보)


        return monthlySummeryDTO;
    }
}

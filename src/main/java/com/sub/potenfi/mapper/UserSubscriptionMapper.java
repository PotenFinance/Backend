package com.sub.potenfi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sub.potenfi.dto.UserSubscriptionInfoDTO;

@Mapper
public interface UserSubscriptionMapper {

    // 사용자 구독 정보 등록
    void insertUserSubscriptions(List<UserSubscriptionInfoDTO> subscriptions);
}

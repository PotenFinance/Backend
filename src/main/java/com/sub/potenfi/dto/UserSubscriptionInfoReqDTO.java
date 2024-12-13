package com.sub.potenfi.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserSubscriptionInfoReqDTO {
    private String userId;        
    private String budget;        // 잔여 예산
    private List<UserSubscriptionInfoDTO> platforms;
    
}

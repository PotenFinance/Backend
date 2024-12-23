package com.sub.potenfi.service;

import java.math.BigDecimal;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sub.potenfi.common.exception.NoContentException;
import com.sub.potenfi.dto.AnnualSubscriptionCostDTO;
import com.sub.potenfi.dto.MonthlySummeryDTO;
import com.sub.potenfi.dto.UserDTO;
import com.sub.potenfi.dto.UserSubscriptionInfoDTO;
import com.sub.potenfi.mapper.MonthlySummeryMapper;
import com.sub.potenfi.mapper.UserMapper;
import com.sub.potenfi.util.StringUtils;

@Service
public class MonthlySummeryService {

    @Autowired
    MonthlySummeryMapper monthlySummeryMapper;

    @Autowired
    UserMapper userMapper;

    // 홈 화면 - 사용자는 이번달 총 구독 비용과 절약 가능 금액을 수치를 통해 한눈에 확인할 수 있다
    public MonthlySummeryDTO getMonthlySummary(String userId) throws Exception {
        try {

            // platformIdList가 null 또는 빈 리스트인 경우 처리
            if (StringUtils.isNullOrEmpty(userId)) {
                System.out.println("userId cannot be empty or null");
                throw new IllegalArgumentException("userId cannot be empty or null");
            }
            System.out.println("userId : " + userId);

            // return DTO
            MonthlySummeryDTO respnseDTO = new MonthlySummeryDTO();

            // 1. 사용자 정보 조회
            UserDTO user = userMapper.getUserById(userId);
            if (user == null) {
                throw new NoContentException("User not found: " + userId);
            } else {
                System.out.println("Set user data");
                respnseDTO.setUserid(user.getUserId());
                respnseDTO.setUserName(user.getUserName());
            }
            System.out.println("1) getUserById - respnseDTO : " + respnseDTO);

            // 2. 총 구독 금액 및 구독 개수
            MonthlySummeryDTO monthlySummeryDTO = new MonthlySummeryDTO();
            monthlySummeryDTO = monthlySummeryMapper.getTotalSubscriptionInfo(userId);
            respnseDTO.setUserBudget(monthlySummeryDTO.getUserBudget());
            respnseDTO.setTotalSubscriptionCost(monthlySummeryDTO.getTotalSubscriptionCost());
            respnseDTO.setTotalSubscriptions(monthlySummeryDTO.getTotalSubscriptions());
            respnseDTO.setRemainingBudget(monthlySummeryDTO.getRemainingBudget());
            respnseDTO.setSubscriptionYear(monthlySummeryDTO.getSubscriptionYear());
            respnseDTO.setSubscriptionMonth(monthlySummeryDTO.getSubscriptionMonth());
            System.out.println("2) getTotalSubscriptionInfo - respnseDTO : " + respnseDTO);

            // 3. 구독 내역 목록
            List<UserSubscriptionInfoDTO> UserSubscriptionListDTO = monthlySummeryMapper.getSubscriptionDetails(userId);
            respnseDTO.setSubscriptionDetails(UserSubscriptionListDTO);
            System.out.println("3) getSubscriptionDetails - respnseDTO : " + respnseDTO);

            // 4. annualSubscriptionCost (사용자 연간 구독 비용 정보)
            AnnualSubscriptionCostDTO annualCost = calculateAnnualSubscriptionCost(userId, user.getBudget());
            respnseDTO.setAnnualSubscriptionCost(annualCost);

            return respnseDTO;

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

	private AnnualSubscriptionCostDTO calculateAnnualSubscriptionCost(String userId, int budget) {
		int year = Year.now().getValue();
        // DB에서 연간 구독 비용 정보 가져오기
	    List<Map<String, Object>> monthlyCostsData = monthlySummeryMapper.getMonthlyCostsByMonth(userId, year);
	    Map<Integer, Integer> monthlyCosts = monthlyCostsData.stream()
	        .collect(Collectors.toMap(
	            data -> (Integer) data.get("subscriptionMonth"),
	            data -> ((BigDecimal) data.get("totalCost")).intValue()
	        ));

	    // 현재 월과 이전 3개월 비용 계산
	    int currentMonth = YearMonth.now().getMonthValue();
	    int currentMonthCost = monthlyCosts.getOrDefault(currentMonth, 0);
	    int lastMonthCost = monthlyCosts.getOrDefault(currentMonth - 1, 0);
	    int twoMonthsBeforeCost = monthlyCosts.getOrDefault(currentMonth - 2, 0);
	    int threeMonthsBeforeCost = monthlyCosts.getOrDefault(currentMonth - 3, 0);

	    // 연간 총 비용 계산
	    int annualCost = monthlyCosts.values().stream().mapToInt(Integer::intValue).sum();

	    // 초과 금액 계산
	    int budgetOverflow = annualCost - budget;

	    // AnnualSubscriptionCostDTO 생성
	    AnnualSubscriptionCostDTO annualCostDTO = new AnnualSubscriptionCostDTO();
	    annualCostDTO.setYear(Year.now().getValue());
	    annualCostDTO.setTotalCost(annualCost);
	    annualCostDTO.setBudgetOverflow(Math.max(budgetOverflow, 0));
	    annualCostDTO.setCurrentMonthCost(currentMonthCost);
	    annualCostDTO.setLastMonthCost(lastMonthCost);
	    annualCostDTO.setTwoMonthsBeforeCost(twoMonthsBeforeCost);
	    annualCostDTO.setThreeMonthsBeforeCost(threeMonthsBeforeCost);

	    return annualCostDTO;
	}
}

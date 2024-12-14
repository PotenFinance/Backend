package com.sub.potenfi.dto;

import java.util.List;

public class UpdateSubscriptionRequest {
    private String userId;
    private Integer budget; // Nullable to distinguish between budget and subscription updates
    private List<SubscriptionUpdateDTO> subscriptions;
    
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getBudget() {
		return budget;
	}
	public void setBudget(Integer budget) {
		this.budget = budget;
	}
	public List<SubscriptionUpdateDTO> getSubscriptions() {
		return subscriptions;
	}
	public void setSubscriptions(List<SubscriptionUpdateDTO> subscriptions) {
		this.subscriptions = subscriptions;
	}
}

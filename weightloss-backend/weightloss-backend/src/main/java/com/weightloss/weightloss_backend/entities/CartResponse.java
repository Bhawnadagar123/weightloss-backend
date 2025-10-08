package com.weightloss.weightloss_backend.entities;

import java.util.List;

public class CartResponse {

	private Long userId;
    
	private List<CartItemResponse> items;
    private Double grandTotal;
	public CartResponse(Long userId, List<CartItemResponse> items, Double grandTotal) {
		super();
		this.userId = userId;
		this.items = items;
		this.grandTotal = grandTotal;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public List<CartItemResponse> getItems() {
		return items;
	}
	public void setItems(List<CartItemResponse> items) {
		this.items = items;
	}
	public Double getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(Double grandTotal) {
		this.grandTotal = grandTotal;
	}
	public CartResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

    // getters & setters
}

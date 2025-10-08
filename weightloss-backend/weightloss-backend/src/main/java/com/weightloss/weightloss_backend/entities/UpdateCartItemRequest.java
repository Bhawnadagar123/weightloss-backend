package com.weightloss.weightloss_backend.entities;

public class UpdateCartItemRequest {
    private Long userId;
    
	private Long productId;
    private Integer quantity; // new qty (if 0 => remove)
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public UpdateCartItemRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UpdateCartItemRequest(Long userId, Long productId, Integer quantity) {
		super();
		this.userId = userId;
		this.productId = productId;
		this.quantity = quantity;
	}

    // getters & setters
}
package com.weightloss.weightloss_backend.entities;

public class OrderItemDto {

	private Long productId;
    private Integer quantity;
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
	public OrderItemDto(Long productId, Integer quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}
	public OrderItemDto() {
		super();
		// TODO Auto-generated constructor stub
	}
}

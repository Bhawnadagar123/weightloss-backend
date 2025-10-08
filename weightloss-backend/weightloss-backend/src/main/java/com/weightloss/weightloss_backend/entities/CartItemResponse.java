package com.weightloss.weightloss_backend.entities;

public class CartItemResponse {
    public CartItemResponse(Long productId, String productName, Integer quantity, Double unitPrice, Double totalPrice) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.totalPrice = totalPrice;
	}
	public CartItemResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	private Long productId;
    private String productName; // optional, fetched from Product
    private Integer quantity;
    private Double unitPrice;
    private Double totalPrice;
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

    // getters & setters
}
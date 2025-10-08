package com.weightloss.weightloss_backend.entities;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class OrderResponse {
    private Long id;
    private Long userId;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private String paymentMethod;
    private String paymentReference;
    private List<OrderItemResponse> items;
    private String shippingAddress;
    private Instant createdAt;
    // getters & setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getPaymentReference() {
		return paymentReference;
	}
	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}
	public List<OrderItemResponse> getItems() {
		return items;
	}
	public void setItems(List<OrderItemResponse> items) {
		this.items = items;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public Instant getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
	public OrderResponse(Long id, Long userId, BigDecimal totalAmount, OrderStatus status, String paymentMethod,
			String paymentReference, List<OrderItemResponse> items, String shippingAddress, Instant createdAt) {
		super();
		this.id = id;
		this.userId = userId;
		this.totalAmount = totalAmount;
		this.status = status;
		this.paymentMethod = paymentMethod;
		this.paymentReference = paymentReference;
		this.items = items;
		this.shippingAddress = shippingAddress;
		this.createdAt = createdAt;
	}
	public OrderResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
}


package com.weightloss.weightloss_backend.entities;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public class PlaceOrderRequest {
	
	  @NotNull
	    private Long userId;
	    @NotNull
	    private String paymentMethod; // "COD" or "ONLINE"
	    @NotNull
	    private String shippingAddress;

	    // Option 1: pass cart items client-side (validate again on server)
	    private List<OrderItemDto> items;

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public String getPaymentMethod() {
			return paymentMethod;
		}

		public void setPaymentMethod(String paymentMethod) {
			this.paymentMethod = paymentMethod;
		}

		public String getShippingAddress() {
			return shippingAddress;
		}

		public void setShippingAddress(String shippingAddress) {
			this.shippingAddress = shippingAddress;
		}

		public List<OrderItemDto> getItems() {
			return items;
		}

		public void setItems(List<OrderItemDto> items) {
			this.items = items;
		}

		public PlaceOrderRequest(@NotNull Long userId, @NotNull String paymentMethod, @NotNull String shippingAddress,
				List<OrderItemDto> items) {
			super();
			this.userId = userId;
			this.paymentMethod = paymentMethod;
			this.shippingAddress = shippingAddress;
			this.items = items;
		}

		public PlaceOrderRequest() {
			super();
			// TODO Auto-generated constructor stub
		}


}

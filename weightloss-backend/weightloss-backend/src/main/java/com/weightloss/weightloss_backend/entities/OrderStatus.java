package com.weightloss.weightloss_backend.entities;

public enum OrderStatus {
	
	PENDING_PAYMENT,    // placed but online payment pending
    PAYMENT_FAILED,
    PROCESSING,         // payment done, order being processed
    SHIPPED,
    DELIVERED,
    CANCELLED,
    REFUNDED

}

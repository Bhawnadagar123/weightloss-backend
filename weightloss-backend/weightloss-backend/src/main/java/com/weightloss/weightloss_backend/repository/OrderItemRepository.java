package com.weightloss.weightloss_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weightloss.weightloss_backend.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {}
package com.weightloss.weightloss_backend.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.weightloss.weightloss_backend.entities.OrderResponse;
import com.weightloss.weightloss_backend.entities.OrderStatus;
import com.weightloss.weightloss_backend.entities.PlaceOrderRequest;
import com.weightloss.weightloss_backend.entities.User;
import com.weightloss.weightloss_backend.repository.UserRepository;
import com.weightloss.weightloss_backend.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200") // allow Angular during dev
public class OrderController {

    private final OrderService svc;
    private final UserRepository userRepository;
    
    
    public OrderController(OrderService svc,  UserRepository userRepository) { 
    	this.svc = svc; 
    	this.userRepository = userRepository;
    	}

    // Place order (uses server-side cart)
    @PostMapping("/place")
    public ResponseEntity<OrderResponse> placeOrder(@Valid  @RequestBody PlaceOrderRequest req) {
        OrderResponse resp = svc.placeOrderFromCart(req.getUserId(), req.getPaymentMethod(), req.getShippingAddress());
        return ResponseEntity.ok(resp);
    }
    
    // Place order (requires authentication)
//    @PostMapping("/place")
//    public ResponseEntity<?> placeOrder(@AuthenticationPrincipal UserDetails userDetails,
//                                        @RequestBody PlaceOrderRequest request) {
//
//        if (userDetails == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                                 .body("Please login to place an order");
//        }
//
//        // Get logged-in user
//        User user = userRepository.findByEmail(userDetails.getUsername())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        // Call your existing service method
//        OrderResponse response = svc.placeOrderFromCart(
//                user.getId(),
//                request.getPaymentMethod(),
//                request.getShippingAddress()
//        );
//
//        return ResponseEntity.ok(response);
//    }

    // Get order by id
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long orderId) {
        return svc.getOrder(orderId)
                .map(order -> ResponseEntity.ok(svc.toOrderResponse(order))) // need to expose toOrderResponse or re-map
                .orElse(ResponseEntity.notFound().build());
    }

    // List orders for user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<?>> listUserOrders(@PathVariable Long userId) {
        List<?> orders = svc.getOrdersForUser(userId).stream().map(svc::toOrderResponse).toList();
        return ResponseEntity.ok(orders);
    }

    // Admin: update status
    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateStatus(@PathVariable Long orderId,
                                                      @RequestParam OrderStatus status) {
        OrderResponse updated = svc.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updated);
    }

    // Payment webhook (example stub) - payment gateway will call this
    @PostMapping("/payment/webhook")
    public ResponseEntity<Void> paymentWebhook(@RequestBody Map<String, Object> payload) {
        // parse payment id, order id, status -> update order accordingly
        // e.g., on success set order.status = PROCESSING and set paymentReference
        return ResponseEntity.ok().build();
    }
}

package com.weightloss.weightloss_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weightloss.weightloss_backend.entities.Cart;
import com.weightloss.weightloss_backend.entities.Order;
import com.weightloss.weightloss_backend.entities.OrderItem;
import com.weightloss.weightloss_backend.entities.OrderItemResponse;
import com.weightloss.weightloss_backend.entities.OrderResponse;
import com.weightloss.weightloss_backend.entities.OrderStatus;
import com.weightloss.weightloss_backend.entities.PlaceOrderRequest;
import com.weightloss.weightloss_backend.entities.Product;
import com.weightloss.weightloss_backend.repository.OrderRepository;
import com.weightloss.weightloss_backend.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService; // optional but recommended

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        CartService cartService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.cartService = cartService;
    }

    /**
     * Place order using server-side cart for the user (recommended).
     */
    @Transactional
    public OrderResponse placeOrderFromCart(Long userId, String paymentMethod, String shippingAddress) {
        // 1. Fetch cart (service returns CartResponse or Cart entity depending on your implementation)
    	Cart cart = cartService.getCartEntityByUserId(userId)
    	        .orElseThrow(() -> new IllegalArgumentException("Cart empty or not found"));

        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        // 2. Validate stock and compute totals
        Map<Long, Product> productsMap = productRepository.findAllById(
                cart.getItems().stream().map(ci -> ci.getProductId()).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(Product::getId, p -> p));

        Order order = new Order();
        order.setUserId(userId);
        order.setPaymentMethod(paymentMethod);
        order.setShippingAddress(shippingAddress);
        BigDecimal grandTotal = BigDecimal.ZERO;

        for (var ci : cart.getItems()) {
            Product p = productsMap.get(ci.getProductId());
            if (p == null) throw new IllegalArgumentException("Product not found: " + ci.getProductId());

            if (p.getStock() < ci.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for product: " + p.getName());
            }

            // create order item
            OrderItem oi = new OrderItem();
            oi.setProductId(p.getId());
            oi.setProductName(p.getName());
            oi.setQuantity(ci.getQuantity());
            oi.setUnitPrice(p.getPrice() == null ? BigDecimal.ZERO : BigDecimal.valueOf(p.getPrice()));
            oi.setTotalPrice(oi.getUnitPrice().multiply(BigDecimal.valueOf(oi.getQuantity())));
            order.addItem(oi);
            grandTotal = grandTotal.add(oi.getTotalPrice());

            // reduce stock immediately (simple approach)
            p.setStock(p.getStock() - ci.getQuantity());
            productRepository.save(p);
        }

        order.setTotalAmount(grandTotal);

        // set initial status depending on paymentMethod
        if ("ONLINE".equalsIgnoreCase(paymentMethod)) {
            order.setStatus(OrderStatus.PENDING_PAYMENT);
            // create payment session with gateway and return payment URL/info (not implemented here)
        } else if ("COD".equalsIgnoreCase(paymentMethod)) {
            order.setStatus(OrderStatus.PROCESSING);
        } else {
            order.setStatus(OrderStatus.PROCESSING);
        }

        Order saved = orderRepository.save(order);

        // clear cart on successful order placement (for COD or if payment flow managed)
        cartService.clearCart(userId);

        return toOrderResponse(saved);
    }

    /**
     * Place order from explicit items passed by client (re-validate price & stock).
     */
    @Transactional
    public OrderResponse placeOrderFromItems(PlaceOrderRequest req) {
        // similar logic: validate, reduce stock, save order
        // implementation omitted for brevity (follow same pattern as above)
        throw new UnsupportedOperationException("Not implemented in this snippet");
    }

    public Optional<Order> getOrder(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrdersForUser(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Transactional
    public OrderResponse updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setStatus(newStatus);
        // if cancelled: optionally restore stock (complex logic: depending on timing)
        order = orderRepository.save(order);
        return toOrderResponse(order);
    }

    public OrderResponse toOrderResponse(Order order) {
        OrderResponse r = new OrderResponse();
        r.setId(order.getId());
        r.setUserId(order.getUserId());
        r.setTotalAmount(order.getTotalAmount());
        r.setStatus(order.getStatus());
        r.setPaymentMethod(order.getPaymentMethod());
        r.setPaymentReference(order.getPaymentReference());
        r.setShippingAddress(order.getShippingAddress());
        r.setCreatedAt(order.getCreatedAt());
        r.setItems(order.getItems().stream().map(oi -> {
            OrderItemResponse ir = new OrderItemResponse();
            ir.setProductId(oi.getProductId());
            ir.setProductName(oi.getProductName());
            ir.setQuantity(oi.getQuantity());
            ir.setUnitPrice(oi.getUnitPrice());
            ir.setTotalPrice(oi.getTotalPrice());
            return ir;
        }).collect(Collectors.toList()));
        return r;
    }
}

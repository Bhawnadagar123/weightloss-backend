package com.weightloss.weightloss_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weightloss.weightloss_backend.entities.Cart;
import com.weightloss.weightloss_backend.entities.CartItem;
import com.weightloss.weightloss_backend.entities.CartItemResponse;
import com.weightloss.weightloss_backend.entities.CartResponse;
import com.weightloss.weightloss_backend.entities.Product;
import com.weightloss.weightloss_backend.repository.CartItemRepository;
import com.weightloss.weightloss_backend.repository.CartRepository;
import com.weightloss.weightloss_backend.repository.ProductRepository;

import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository; // assume exists

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public CartResponse getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElse(new Cart(userId));
        return toCartResponse(cart);
    }
    
    public Optional<Cart> getCartEntityByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Transactional
    public CartResponse addToCart(Long userId, Long productId, int qty) {
        if (qty <= 0) throw new IllegalArgumentException("Quantity must be > 0");

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Cart cart = cartRepository.findByUserId(userId).orElse(new Cart(userId));

        // check if item exists
        Optional<CartItem> existing = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst();

        if (existing.isPresent()) {
            CartItem item = existing.get();
            item.setQuantity(item.getQuantity() + qty);
            // optionally update price snapshot
            item.setPrice(product.getPrice());
        } else {
            CartItem item = new CartItem(productId, qty, product.getPrice());
            cart.addItem(item);
        }

        cart = cartRepository.save(cart);
        return toCartResponse(cart);
    }

    @Transactional
    public CartResponse updateCartItem(Long userId, Long productId, int newQty) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for user"));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));

        if (newQty <= 0) {
            cart.removeItem(item);
        } else {
            item.setQuantity(newQty);
        }

        cart = cartRepository.save(cart);
        return toCartResponse(cart);
    }

    @Transactional
    public CartResponse removeItem(Long userId, Long productId) {
        return updateCartItem(userId, productId, 0);
    }

    @Transactional
    public void clearCart(Long userId) {
        cartRepository.findByUserId(userId).ifPresent(cartRepository::delete);
    }

    // helper to convert to DTO
    private CartResponse toCartResponse(Cart cart) {
        List<CartItemResponse> items = cart.getItems().stream().map(ci -> {
            CartItemResponse cir = new CartItemResponse();
            cir.setProductId(ci.getProductId());
            // fetch product details (best to batch fetch, simplified here)
            productRepository.findById(ci.getProductId())
                    .ifPresent(p -> cir.setProductName(p.getName()));
            cir.setQuantity(ci.getQuantity());
            cir.setUnitPrice(ci.getPrice());
            cir.setTotalPrice(ci.getPrice() * ci.getQuantity());
            return cir;
        }).collect(Collectors.toList());

        double grandTotal = items.stream()
                .mapToDouble(CartItemResponse::getTotalPrice)
                .sum();

        CartResponse cr = new CartResponse();
        cr.setUserId(cart.getUserId());
        cr.setItems(items);
        cr.setGrandTotal(grandTotal);
        return cr;
    }
}


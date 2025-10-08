package com.weightloss.weightloss_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.weightloss.weightloss_backend.entities.AddToCartRequest;
import com.weightloss.weightloss_backend.entities.CartResponse;
import com.weightloss.weightloss_backend.entities.UpdateCartItemRequest;
import com.weightloss.weightloss_backend.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) { this.cartService = cartService; }

    // GET /api/cart/{userId}
    @GetMapping("/{userId}")
    public ResponseEntity<CartResponse> getCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    // POST /api/cart/add
    @PostMapping("/add")
    public ResponseEntity<CartResponse> addToCart(@RequestBody AddToCartRequest req) {
        CartResponse resp = cartService.addToCart(req.getUserId(), req.getProductId(), req.getQuantity());
        return ResponseEntity.ok(resp);
    }

    // PUT /api/cart/update
    @PutMapping("/update")
    public ResponseEntity<CartResponse> updateCartItem(@RequestBody UpdateCartItemRequest req) {
        CartResponse resp = cartService.updateCartItem(req.getUserId(), req.getProductId(), req.getQuantity());
        return ResponseEntity.ok(resp);
    }

    // DELETE /api/cart/item
    @DeleteMapping("/item")
    public ResponseEntity<CartResponse> removeItem(@RequestParam Long userId, @RequestParam Long productId) {
        CartResponse resp = cartService.removeItem(userId, productId);
        return ResponseEntity.ok(resp);
    }

    // DELETE /api/cart/{userId}
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}


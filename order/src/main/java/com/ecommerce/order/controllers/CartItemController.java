package com.ecommerce.order.controllers;

import com.ecommerce.order.dtos.CartItemRequest;
import com.ecommerce.order.models.CartItem;
import com.ecommerce.order.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartItemController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody CartItemRequest request) {


        if (!cartService.addToCart(userId, request)) {

            return ResponseEntity.badRequest().body("product not found or user not found or product out of stock");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(@RequestHeader("X-User-Id") Long userId, @PathVariable Long productId) {

        boolean deleted = cartService.deleteItemFromCart(userId, Long.valueOf(String.valueOf(productId)));
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();

    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCart(@RequestHeader("X-User-Id") Long userId) {

        return ResponseEntity.ok(cartService.getCart(userId));
    }
}

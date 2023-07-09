package com.javidev.ecommerce.controllers;

import com.javidev.ecommerce.entities.Cart;
import com.javidev.ecommerce.entities.CartDetail;
import com.javidev.ecommerce.entities.CartDetailOption;
import com.javidev.ecommerce.entities.Product;
import com.javidev.ecommerce.services.CartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<?> getCarts() {
        try {
            Long userId = Long.parseLong(SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal()
                    .toString());
            return new ResponseEntity<>(cartService.getCartsByUserId(userId), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getCartById(Long id) {
        return new ResponseEntity<>(cartService.getCartById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveCart(@RequestBody Cart cart) {
        try {
            return new ResponseEntity<>(cartService.saveCart(cart), cart.getId() == null ? HttpStatus.CREATED : HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/toogle-visibility")
    public ResponseEntity<?> changeVisibility(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(cartService.toogleVisibility(id), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<?> activate(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(cartService.activate(id), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivate(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(cartService.deactivate(id), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable Long id) {
        try {
            cartService.deleteCart(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

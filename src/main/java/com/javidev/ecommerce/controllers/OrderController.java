package com.javidev.ecommerce.controllers;

import com.javidev.ecommerce.entities.Order;
import com.javidev.ecommerce.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> saveOrder(@RequestBody Order order) {
        try{
            return new ResponseEntity<>(orderService.saveOrder(order), order.getId() == null ? HttpStatus.CREATED : HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/checkout/{id}")
    public ResponseEntity<?> checkout(@RequestBody Order order, @PathVariable Long id) {
        try{
            return new ResponseEntity<>(orderService.checkout(id, order), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //* Soft delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        try{
            return new ResponseEntity<>(orderService.deleteOrder(id), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

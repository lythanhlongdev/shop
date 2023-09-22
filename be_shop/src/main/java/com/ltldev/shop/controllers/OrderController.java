package com.ltldev.shop.controllers;

import com.ltldev.shop.dto.OrdersDTO;
import com.ltldev.shop.models.Order;
import com.ltldev.shop.services.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {


    private final IOrderService orderService;

    @PostMapping("")
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrdersDTO request, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errMessage = result.getAllErrors()
                        .stream()
                        .map(fieldError -> fieldError.getDefaultMessage())
                        .toList();
                return ResponseEntity.badRequest().body(errMessage);
            }
            orderService.creatOrder(request);
            return ResponseEntity.ok(orderService.creatOrder(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@Valid @PathVariable("id") Long orderId) {
        try {
            Order exitstingOrder = orderService.getByOrderId(orderId);
            return ResponseEntity.ok(exitstingOrder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllOrders(@Valid @PathVariable("userId") Long userId) {
        // change status
        try {
            List<Order> orders = orderService.getAllOrderByUserId(userId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@Valid @PathVariable("id") Long orderId, @Valid @RequestBody OrdersDTO request) {
        try {
            Order existing = orderService.updateOrder(orderId, request);
            return ResponseEntity.ok(existing);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrder(@Valid @PathVariable("orderId") Long orderId) {
        // change status
        try {
            orderService.deleteOrder(orderId);
            return ResponseEntity.ok("Delete successfully order with id: " + orderId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

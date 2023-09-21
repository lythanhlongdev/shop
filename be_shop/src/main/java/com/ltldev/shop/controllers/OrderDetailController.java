package com.ltldev.shop.controllers;
import com.ltldev.shop.dto.OrderDetailDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("${api.prefix}/order_details")
public class OrderDetailController {


    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(@RequestBody @Valid OrderDetailDTO req, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errMessage = result.getAllErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            return ResponseEntity.badRequest().body(errMessage);
        }
        return ResponseEntity.ok("insert Order detail successfully " + req);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getOderDetail(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(" Get oder detail successfully id = " + id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // get list order_detail in order
    @GetMapping("/order/{order_id}")
    public ResponseEntity<String> getOderDetails(@PathVariable("order_id") String id) {
        try {
            return ResponseEntity.ok(" Get order detail with order_id = " + id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrderDetail(@Valid @PathVariable("id") Long id,@Valid @RequestBody OrderDetailDTO request) {
        try {
            return ResponseEntity.ok(" Update oder detail successfully id = " + request);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderDetail(@PathVariable String id) {
        try {
            return ResponseEntity.ok(" Delete oder detail successfully id = " + id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

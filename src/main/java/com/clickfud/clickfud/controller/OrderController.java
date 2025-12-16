package com.clickfud.clickfud.controller;

import com.clickfud.clickfud.dto.OrderDTO;
import com.clickfud.clickfud.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders") //
@CrossOrigin // React access
public class OrderController {

    @Autowired
    private OrderService service;

    // Today's orders
    @GetMapping("/today")
    public List<OrderDTO> todayOrders() {
        return service.getTodayOrders();
    }

    // History orders
    @GetMapping("/history")
    public List<OrderDTO> history(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String date
    ) {
        return service.getHistoryOrders(status, date);
    }

    // Update order status (Modal)
    @PutMapping("/{orderId}/status")
    public void updateStatus(
            @PathVariable String orderId,
            @RequestParam String status
    ) {
        service.updateStatus(orderId, status);
    }
}

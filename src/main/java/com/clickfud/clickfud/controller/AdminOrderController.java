package com.clickfud.clickfud.controller;

import com.clickfud.clickfud.service.FirebaseOrderService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminOrderController {

    private final FirebaseOrderService orderService;

    public AdminOrderController(FirebaseOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/today")
    public List<Map<String, Object>> getTodayOrders() throws Exception {
        return orderService.getTodayOrders();
    }
}

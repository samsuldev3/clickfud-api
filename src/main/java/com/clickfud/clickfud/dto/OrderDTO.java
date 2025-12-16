package com.clickfud.clickfud.dto;

import lombok.Data;

@Data
public class OrderDTO {

    private String id;

    private String customerName;
    private String customerPhone;

    private String restaurantId;
    private String restaurantName;

    private String item;
    private double amount;

    private String status;

    private String createdAt;        // yyyy-MM-dd
    private long createdTimestamp;   // millis

    private String riderId;
}

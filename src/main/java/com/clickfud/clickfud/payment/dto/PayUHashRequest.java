package com.clickfud.clickfud.payment.dto;

import lombok.Data;

@Data
public class PayUHashRequest {
    private String txnId;
    private String amount;
    private String productInfo;
    private String name;
    private String email;
}

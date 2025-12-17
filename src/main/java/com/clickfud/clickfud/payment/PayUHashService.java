package com.clickfud.clickfud.payment;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payu")
public class PayUHashService {

    @Value("${payu.key}")
    private String key;

    @Value("${payu.salt}")
    private String salt;

    public String generateHash(
            String txnId,
            String amount,
            String productInfo,
            String name,
            String email
    ) {

        String hashString =
                key + "|" + txnId + "|" + amount + "|" +
                        productInfo + "|" + name + "|" + email +
                        "|||||||||||" + salt;

        return DigestUtils.sha512Hex(hashString);
    }

    public String getKey() {
        return key;
    }
}

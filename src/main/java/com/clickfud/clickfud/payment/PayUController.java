package com.clickfud.clickfud.payment;

import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payu")
public class PayUController {

    private final String KEY = System.getenv("Dtc3ua");
    private final String SALT = System.getenv("oaqg8pmlBHfxhZlAooQBwezk3XIlyj0h");

    @PostMapping("/hash")
    public Map<String, String> generateHash(@RequestBody Map<String, String> body) {

        String txnId = body.get("txnId");
        String amount = body.get("amount");
        String productInfo = body.get("productInfo");
        String firstname = body.get("name");
        String email = body.get("email");

        String hashString =
                KEY + "|" +
                        txnId + "|" +
                        amount + "|" +
                        productInfo + "|" +
                        firstname + "|" +
                        email +
                        "|||||||||||" +
                        SALT;

        String hash = sha512(hashString);

        Map<String, String> res = new HashMap<>();
        res.put("key", KEY);
        res.put("hash", hash);
        return res;
    }

    @PostMapping("/success")
    public String success(@RequestParam Map<String, String> params) {
        return "PAYMENT SUCCESS";
    }

    @PostMapping("/failure")
    public String failure(@RequestParam Map<String, String> params) {
        return "PAYMENT FAILED";
    }

    private String sha512(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

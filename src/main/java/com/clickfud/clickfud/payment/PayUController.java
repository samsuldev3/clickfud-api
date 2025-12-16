package com.clickfud.clickfud.payment;

import com.google.common.hash.Hashing;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;

@RestController
public class PayUController {


    @GetMapping("/payu/start")
    public void startPayU(HttpServletResponse response) throws Exception {

        String key = "Dtc3ua";
        String salt = "oaqg8pmlBHfxhZlAooQBwezk3XIlyj0h";

        String txnId = UUID.randomUUID().toString().replace("-", "");
        String amount = "250.00";
        String productInfo = "Food Order";
        String firstname = "Samsul";
        String email = "test@email.com";
        String phone = "9999999999";

        String surl = "https://yourdomain.com/payu/success";
        String furl = "https://yourdomain.com/payu/failure";

        String hashString = key + "|" + txnId + "|" + amount + "|" + productInfo + "|" +
                firstname + "|" + email + "|||||||||||" + salt;

        String hash = sha512(hashString);

        String html =
                "<html><body onload='document.forms[0].submit()'>" +
                        "<form action='https://secure.payu.in/_payment' method='post'>" +
                        hidden("key", key) +
                        hidden("txnid", txnId) +
                        hidden("amount", amount) +
                        hidden("productinfo", productInfo) +
                        hidden("firstname", firstname) +
                        hidden("email", email) +
                        hidden("phone", phone) +
                        hidden("surl", surl) +
                        hidden("furl", furl) +
                        hidden("hash", hash) +
                        "</form></body></html>";

        response.setContentType("text/html");
        response.getWriter().write(html);
    }

    private String hidden(String name, String value) {
        return "<input type='hidden' name='" + name + "' value='" + value + "' />";
    }
    private static String sha512(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] bytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}

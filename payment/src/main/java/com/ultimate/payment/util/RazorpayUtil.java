package com.ultimate.payment.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class RazorpayUtil {

    public static boolean verify(String payload, String signature, String secret) throws Exception {

        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(), "HmacSHA256"));

        byte[] hash = mac.doFinal(payload.getBytes());
        String generated = Base64.getEncoder().encodeToString(hash);

        return generated.equals(signature);
    }
}


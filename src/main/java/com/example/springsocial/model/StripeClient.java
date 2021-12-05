package com.example.springsocial.model;
import java.util.HashMap;
import java.util.Map;

public class StripeClient {

    private final long amount;
    private final String receiptEmail;
    private final String source;
    private final String currency;

    public StripeClient(long amount, String receiptEmail) {
        this.amount = amount;
        this.source = "tok_visa";
        this.currency = "INR";
        this.receiptEmail = receiptEmail;
    }

    public Map<String, Object> getCharge() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", this.amount);
        params.put("currency", this.currency);
        // source should obtained with Stripe.js
        params.put("source", this.source);
        params.put(
                "description",
                "My First Test Charge (created for API docs)"
        );
        params.put("receipt_email",this.receiptEmail);
        return params;
    }
}
package com.example.springsocial.controller;


import com.example.springsocial.model.StripeClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

@RestController
@RequestMapping("/api")
public class PaymentGatewayController {

    @PostMapping("/stripe/charge")
    public ResponseEntity<String> createCharge(@RequestBody StripeClient stripeCharge) {
        try {
            // creating the charge
            Stripe.apiKey = "sk_test_51K2RXcSFlRY3Q4axxb9vODd9NgNtcvNbbWwuyifuATgWBycwFP17ofuA00Fc92vPkwsNZg42AEwcapCp4C1HTssG00yoWd8ioP";
            Charge charge = Charge.create(stripeCharge.getCharge());
            System.out.println(charge);
            return new ResponseEntity<String>("Success", HttpStatus.CREATED);
        } catch (StripeException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Failure", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
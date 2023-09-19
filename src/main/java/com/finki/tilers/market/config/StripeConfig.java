package com.finki.tilers.market.config;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class StripeConfig {
    @Value("${stripe.apiKey}")
    private String apiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = apiKey;
    }
}
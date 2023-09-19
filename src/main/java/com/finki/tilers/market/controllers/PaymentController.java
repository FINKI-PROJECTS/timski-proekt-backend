package com.finki.tilers.market.controllers;

import com.finki.tilers.market.model.dto.PostSummaryDto;
import com.finki.tilers.market.model.entity.Post;
import com.finki.tilers.market.services.PostService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PostService postService;

    @GetMapping("/get-link")
    public Map<String, String> createAndConfirmPayment(@RequestParam(required = false) Long quantity, @RequestParam Long serviceId) throws StripeException { // ,

        PostSummaryDto post = postService.getPostById(serviceId);

        SessionCreateParams params = SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:3000/service/" + serviceId + "?success=true")
                    .setCancelUrl("http://localhost:3000/service/" + serviceId)
                    .addLineItem(SessionCreateParams.LineItem.builder()
                            .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                    .setCurrency("mkd")
                                    .setUnitAmount(Long.parseLong(post.getPrice()) * 100) // e.g., $20.00
                                    .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                            .setName(post.getName())
                                            .build())
                                    .build())
                            .setQuantity(quantity != null ? quantity : 1L)
                            .build())
                    .build();

            Session session = Session.create(params);
            Map<String, String> result = new HashMap<>();
            result.put("paymentURL", session.getUrl());
            return result;  // This is the URL you'll redirect your user to

    }

}

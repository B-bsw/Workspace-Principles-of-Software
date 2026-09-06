package com.example.lab08.strategy;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DiscountContext {
    private final Map<String, DiscountStrategy> strategies;

    public DiscountContext(Map<String, DiscountStrategy> strategies) {
        this.strategies = strategies;
    }

    public double calculateDiscount(String discountType, double price) {
        DiscountStrategy strategy = strategies.getOrDefault(
                discountType,
                strategies.get("NONE")
        );

        return strategy.calculateDiscount(price);
    }
}
